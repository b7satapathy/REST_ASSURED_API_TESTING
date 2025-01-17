package com.spotify.oauth2.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InnerError {
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    @JsonProperty("status")
    public InnerError setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public InnerError setMessage(String message) {
        this.message = message;
        return this;
    }

}
