package com.finkicommunity.domain.request.group;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddGroupModeratorRequest {
    @NotNull
    @NotEmpty
    public String groupCode;
    @NotNull
    @NotEmpty
    public String username;
}
