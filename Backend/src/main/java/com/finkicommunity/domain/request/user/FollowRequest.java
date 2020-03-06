package com.finkicommunity.domain.request.user;

import javax.validation.constraints.NotNull;

public class FollowRequest {
    @NotNull
    public String usernameFollowing;
    @NotNull
    public String usernameFollowed;
    @NotNull
    public Boolean isFollow;
}
