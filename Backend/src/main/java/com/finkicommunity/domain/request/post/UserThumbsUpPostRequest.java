package com.finkicommunity.domain.request.post;

import javax.validation.constraints.NotNull;

public class UserThumbsUpPostRequest {
    @NotNull
    public String username;
    @NotNull
    public Long postId;
//    @NotNull
//    public Boolean isThumbUp;  // If true -> thumbUp, if false -> exiting thumb up
}
