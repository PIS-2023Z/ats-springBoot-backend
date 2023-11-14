package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HelloWorldTest {

    @Test
    void hello() {
        String message = "I love PIK";
        assertTrue(message.length() != 0);
        assertEquals(10, message.length());
        assertFalse(message.equals("I love SOI"));
    }
}
