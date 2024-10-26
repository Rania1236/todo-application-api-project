package com.qacart.todo.base;

import io.restassured.http.ContentType;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {
    public static String getEnv(){
        String env = System.getProperty("env","PRODUCTION");
        String baseURL;
        switch (env) {
            case "PRODUCTION":
                baseURL = "https://qacart-todo.herokuapp.com";
            break;
            case "LOCAL":
                baseURL = "https://localhost:8000";
            break;
            default:
                throw new RuntimeException("Environment is not supported");
        }
        return baseURL;
    }
    public static RequestSpecification gerRequestSpec(){
        return given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .log().all();
    }
}
