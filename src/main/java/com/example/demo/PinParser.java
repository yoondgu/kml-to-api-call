package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PinParser {

    private static final String NAME = "Name: ";
    private static final String DESCRIPTION = "Description: ";
    private static final String ADDRESS = "Address: ";
    private static final String LEGAL_DONG_CODE = "LegalDongCode: ";
    private static final String COORDINATES = "Coordinates: ";

    public static List<PinCreateRequest> parseFile(String path) {
        File file = new File(path);
        System.out.println(file.getName());

        List<PinCreateRequest> pins = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String name = null;
            String description = null;
            String address = null;
            String legalDongCode = null;
            Double latitude = null;
            Double longitude = null;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(NAME)) {
                    name = line.substring(NAME.length());
                } else if (line.startsWith(DESCRIPTION)) {
                    String raw = line.substring(DESCRIPTION.length());
                    description = raw.replaceAll("<br>", "\n")
                            .replaceAll("/", "\n- ")
                            .replaceAll("����확인 및 좌표 추가", "");
                } else if (line.startsWith(ADDRESS)) {
                    address = line.substring(ADDRESS.length());
                } else if (line.startsWith(LEGAL_DONG_CODE)) {
                    legalDongCode = line.substring(LEGAL_DONG_CODE.length());
                } else if (line.startsWith(COORDINATES)) {
                    String[] coordinates = line.substring(COORDINATES.length()).split(",");
                    latitude = Double.parseDouble(coordinates[1]);
                    longitude = Double.parseDouble(coordinates[0]);
                } else if (line.equals("---")) {
                    if (isNotNull(name, description, address, legalDongCode, latitude, longitude)) {
                        pins.add(new PinCreateRequest(551730L, name, description, address, legalDongCode, latitude, longitude));
                        name = null;
                        description = null;
                        address = null;
                        legalDongCode = null;
                        latitude = null;
                        longitude = null;
                    }
                }
            }
            if (isNotNull(name, description, address, legalDongCode, latitude, longitude)) {
                pins.add(new PinCreateRequest(551730L, name, description, address, legalDongCode, latitude, longitude));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pins;
    }

    private static boolean isNotNull(
            String name,
            String description,
            String address,
            String legalDongCode,
            Double latitude,
            Double longitude
    ) {
        return name != null & description != null & address != null & legalDongCode != null && latitude != null & longitude != null;
    }
}
