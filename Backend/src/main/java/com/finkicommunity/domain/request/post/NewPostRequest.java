package com.finkicommunity.domain.request.post;

import org.neo4j.ogm.annotation.Required;

import javax.validation.constraints.NotBlank;

public class NewPostRequest {
    @NotBlank
    public String title;
    @NotBlank
    public String content;
    @NotBlank
    public String groupCode;
    @NotBlank
    public String username;
}
