package com.finkicommunity.domain.request.post;

import javax.validation.constraints.NotNull;

public class UserThumbsDownPostRequest {
    @NotNull
    public String username;
    @NotNull
    public Long postId;
    @NotNull
    public Boolean isThumbDown;  // If true -> thumbDown, if false -> exiting thumb down
}
