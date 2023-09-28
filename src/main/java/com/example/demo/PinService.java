package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PinService {

    public List<PinCreateRequest> parsePins(String fileName) {
        return PinParser.parseFile("/Users/doyoung/projects/datainsert/src/main/resources/static/" + fileName);
    }

}
