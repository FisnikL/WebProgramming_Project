package com.finkicommunity.domain.request.post;

import javax.validation.constraints.NotNull;

public class UserLikesPostRequest {
    @NotNull
    public String username;
    @NotNull
    public Long postId;
    @NotNull
    public Boolean isLike;  // If true -> like, if false -> no like
}
