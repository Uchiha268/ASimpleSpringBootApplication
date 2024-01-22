package com.Library.TestingDemo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;

import static org.hamcrest.Matchers.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WireMockTest {


    private static WireMockServer wireMockServer;



    @BeforeAll
    static void startWireMock(){
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();

    }


    @AfterAll
    static void stopWireMock(){
        wireMockServer.stop();
    }

    @Test
    void testWireMockWorking(){
        System.out.println(wireMockServer.baseUrl());
        Assertions.assertTrue(wireMockServer.isRunning());
    }

    @Test
    void testWireMock() {
        wireMockServer.stubFor(
                WireMock.get("/authors")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody("{\"key\" : \"Value\"}")
                        )
        );
        RestAssured.baseURI = wireMockServer.baseUrl();

        RestAssured
                .given()
                .header("Content-Type", "application/json")
                .when()
                .get("/authors")
                .then()
                .statusCode(200)
                .body("key", is("Value"));
    }


}
