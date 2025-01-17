package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class TokenManager {

    private static String access_token;
    private static Instant expiry_time;

    public static String getAccessToken() {

        try{
            if(access_token == null || Instant.now().isAfter(expiry_time)) {
                System.out.println("Renewing access token...");

                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            } else {
                System.out.println("Using existing access token");
            }

        }catch(Exception e) {
            throw new RuntimeException("Failed to get access token", e);
        }

        return access_token;

    }


    private static Response renewToken() {
        HashMap<String, String> formParamters = new HashMap<String, String>();
        formParamters.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParamters.put("client_id", ConfigLoader.getInstance().getClientId());
        formParamters.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        formParamters.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());

        Response response = RestResource.postAccount(formParamters);

        if(response.statusCode() != 200) {
            throw new RuntimeException("Renew Token Failed");
        }

        return response;


    }
}
