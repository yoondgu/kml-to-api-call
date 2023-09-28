package com.example.geographicfileparser;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro Coelho
 */
public class Example {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ApiService apiService = new ApiService();

        System.out.println("\n==============BEGIN MAIN==============\n");

        File dir =  new File("Examples/");

        if (dir.listFiles().length > 0) {

            for (File file : dir.listFiles()) {

                if (    // Dumb check for KMZ/KML files. Parser class does it better.
                        file.getName().toLowerCase().endsWith(".kmz")
                        ||
                        file.getName().toLowerCase().endsWith(".kml")
                        ) {

                    try {

                        Parser parser = new Parser(file);
                        List<PinCreateRequest> pins = parser.parse();
                        apiService.insert(pins.get(0));

                    } catch (Exception ex) {
                        Logger.getLogger(Example.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    System.out.println("Ignored file: " + file.getName());
                }

            }

        } else {
            System.out.println("Empty Examples folder! Drop a KMZ or KML file there to test this library.");
        }

        System.out.println("\n==============END MAIN==============\n");
        
    }
    
}
