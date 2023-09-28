package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class PinServiceTest {

    @Test
    void parseFile() {
        PinService pinService = new PinService();

        List<PinCreateRequest> pinCreateRequests = pinService.parsePins("타코야키.txt");

        assertThat(pinCreateRequests).isNotEmpty();
        System.out.println(pinCreateRequests);
    }
}
