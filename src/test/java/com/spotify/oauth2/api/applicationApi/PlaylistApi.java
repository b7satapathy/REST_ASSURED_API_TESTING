package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.api.TokenManager;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;

public class PlaylistApi {

    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(requestPlaylist, TokenManager.getAccessToken(), USERS + ConfigLoader.getInstance().getUserId() + PLAYLISTS);

    }
    public static Response post(Playlist requestPlaylist, String accessToken) {
        return RestResource.post(requestPlaylist, accessToken, USERS + ConfigLoader.getInstance().getUserId() + PLAYLISTS);

    }

    public static Response get(String playlistID) {
        String path = PLAYLISTS + "/" + playlistID;
        return RestResource.get(TokenManager.getAccessToken(), path);
    }

    public static Response put(Playlist requestPlaylist, String playlistID) {
        String path = PLAYLISTS + "/" + playlistID;
        return RestResource.put(requestPlaylist, TokenManager.getAccessToken(), path);
    }
}
