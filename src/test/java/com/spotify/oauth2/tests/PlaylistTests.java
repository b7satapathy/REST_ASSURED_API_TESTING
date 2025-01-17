package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.InnerError;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {


    private String playlist_id;

    public Playlist playlistBuilder(String name, String description, boolean isPublic) {
        return new Playlist().
                setName(name).
                setDescription(description).
                setPublic(isPublic);
    }

    public InnerError innerErrorBuilder(int statusCode, String message) {
        return new InnerError().
                setStatus(statusCode).
                setMessage(message);
    }

    public void assertNameDescriptionAndPublic(Playlist responsePlaylist, Playlist requestPlaylist) {
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    };

    public void assertStatusCode(int actualStatusCode, int expectedStatusCode) {
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    };

    public void assertErrorStatusAndMessage(InnerError expectedError, InnerError actualError) {
        assertThat(expectedError.getStatus(), equalTo(actualError.getStatus()));
        assertThat(expectedError.getMessage(), equalTo(actualError.getMessage()));
    };




    @Test
    public void shouldBeAbleToCreatePlaylist() {

        Playlist requestPlaylist = playlistBuilder("New Playlist 13", "New playlist description 13", false);
        Response response = PlaylistApi.post(requestPlaylist);

        assertStatusCode(response.statusCode(), 201);

        Playlist responsePlaylist = response.as(Playlist.class);
        assertNameDescriptionAndPublic(responsePlaylist, requestPlaylist);

        playlist_id = responsePlaylist.getId();

    }

    @Test(dependsOnMethods = {"shouldBeAbleToCreatePlaylist"})
    public void shouldBeAbleToGetAPlaylist() {

        Playlist requestPlaylist = playlistBuilder("New Playlist 13", "New playlist description 13", true);
        Response response = PlaylistApi.get(playlist_id);

        assertStatusCode(response.statusCode(), 200);

        Playlist responsePlaylist = response.as(Playlist.class);
        assertNameDescriptionAndPublic(responsePlaylist, requestPlaylist);

    }

    @Test(dependsOnMethods = {"shouldBeAbleToGetAPlaylist"})
    public void shouldBeAbleToUpdateThePlaylist() {

        Playlist requestPlaylist = playlistBuilder("Updated Playlist Name 13", "Updated playlist description 13", false);

        Response response = PlaylistApi.put(requestPlaylist, playlist_id);
        assertStatusCode(response.statusCode(), 200);

    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithoutName() {

        Playlist requestPlaylist = playlistBuilder("", "New playlist description 13", false);
        InnerError innerError = innerErrorBuilder(400, "Missing required field: name");

        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), 400);

        Error responseError = response.as(Error.class);
        assertErrorStatusAndMessage(innerError, responseError.getError());

    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithInvalidToken() {

        String invalid_token = "invalid_token";

        Playlist requestPlaylist = playlistBuilder("New Playlist 13", "New playlist description 13", false);

        InnerError innerError = innerErrorBuilder(401, "Invalid access token");

        Response response = PlaylistApi.post(requestPlaylist, invalid_token);
        assertStatusCode(response.statusCode(), 401);

        Error responseError = response.as(Error.class);
        assertErrorStatusAndMessage(innerError, responseError.getError());

    }
}