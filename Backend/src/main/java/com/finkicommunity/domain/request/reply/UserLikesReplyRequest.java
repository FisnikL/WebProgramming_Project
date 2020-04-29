package com.finkicommunity.domain.request.reply;

import javax.validation.constraints.NotNull;

public class UserLikesReplyRequest {
    @NotNull
    public String username;
    @NotNull
    public Long replyId;
    @NotNull
    public Boolean isLike;  // If true -> like, if false -> no like
}
