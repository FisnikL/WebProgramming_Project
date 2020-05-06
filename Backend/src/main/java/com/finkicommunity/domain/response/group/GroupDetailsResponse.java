package com.finkicommunity.domain.response.group;

import java.util.Set;

public class GroupDetailsResponse {
    public Long id;
    public String code;
    public String name;
    public String description;
    public Set<GroupModeratorResponse> moderators;
}
