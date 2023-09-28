package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PinService {

    public List<PinCreateRequest> parsePins(long topicId, String fileName) {
        return PinParser.parseTxt(topicId, "/Users/doyoung/projects/datainsert/src/main/resources/static/" + fileName);
    }

}
