package com.finkicommunity.domain.request.reply;

import javax.validation.constraints.NotNull;

public class NewReplyRequest {
    @NotNull
    public long postId;
    @NotNull
    public String content;
    @NotNull
    public String username;
}
