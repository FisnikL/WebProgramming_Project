package com.finkicommunity.domain.response;

import java.util.List;

public class PostDetailsResponse {
    public long id;
    public String title;
    public String content;
    public long created;
    public String groupCode;
    public String groupName;
    public String userName;
    public int numOfLikes;
    public int numOfReplies;
    // public List<User> usernamesLiked;
    public List<ReplyResponse> replies;
}
