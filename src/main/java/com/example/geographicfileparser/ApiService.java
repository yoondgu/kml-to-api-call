package com.example.geographicfileparser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class ApiService {

    private static final String boundary = "-----------------";
    private static final String CRLF = "\r\n";
    private static final String token = "token";
    final String devBaseUrl = "https://mapbefine.kro.kr/api/pins";
    final String prodBaseUrl = "https://mapbefine.com/api/pins";

    public void insert(PinCreateRequest pinCreateRequest) {
        HttpURLConnection con = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(devBaseUrl);

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", token);
            con.setRequestProperty("Content-Type", "multipart/form-data;");

            con.setDefaultUseCaches(false);
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeBytes("Multiparts: " + boundary + CRLF);
            dos.writeBytes("Content-Disposition: form-data; charset name = request; filename = file; Content-Type: application/json;" + CRLF);
            HashMap<Object, Object> pin = new HashMap<>();
            pin.put("request", pinCreateRequest);
            dos.writeBytes(objectMapper.writeValueAsString(pinCreateRequest) + CRLF);
            dos.writeBytes(boundary + CRLF);
            dos.flush();
            dos.close();

            int responseCode = con.getResponseCode();
            System.out.println("--------------------------------------------");
            System.out.println("responseCode = " + responseCode);
            System.out.println("con.getResponseMessage() = " + con.getResponseMessage());
        } catch (Exception e) {
            System.out.println("연결 오류 : " + e);
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
    }

}
