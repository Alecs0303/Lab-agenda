package com.example.demo.Util;

import org.springframework.http.HttpStatus;

public class Util {
    public static HttpStatus statusResponse(String mensaje){
        HttpStatus httpStatus;
        switch (mensaje){
            case "could not execute statement":
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            default:
                httpStatus = HttpStatus.OK;
        }
        return httpStatus;
    }
}
