package com.spotify.oauth2.api;

import com.spotify.oauth2.pojo.Playlist;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Route.API_TOKEN;
import static com.spotify.oauth2.api.SpecBuilder.getAccountRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class RestResource {

    public static Response post(Object requestObject, String token, String path) {
        return
                given(SpecBuilder.getRequestSpec()).
                        header("Authorization", "Bearer " + token).
                        body(requestObject).
                        when().post(path).
                        then().spec(SpecBuilder.getResponseSpec()).
                        extract().response();
    }

    public static Response get(String token, String path) {
        return
                given(SpecBuilder.getRequestSpec()).
                        header("Authorization", "Bearer " + token).
                        when().get(path).
                        then().spec(SpecBuilder.getResponseSpec()).
                        extract().response();
    }

    public static Response put(Object requestObject, String token, String path) {
        return
                given(SpecBuilder.getRequestSpec()).
                        header("Authorization", "Bearer " + token).
                        body(requestObject).
                        when().put(path).
                        then().spec(SpecBuilder.getResponseSpec()).
                        extract().response();
    }

    public static Response postAccount(HashMap<String, String> formParams) {

        return given(getAccountRequestSpec()).
                formParams(formParams).
                when().
                post(API_TOKEN).
                then().
                spec(getResponseSpec()).
                extract().response();

    }
}
