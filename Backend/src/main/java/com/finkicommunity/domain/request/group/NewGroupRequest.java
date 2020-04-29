package com.finkicommunity.domain.request.group;

import javax.validation.constraints.NotBlank;

public class NewGroupRequest {
    @NotBlank
    public String code;
    @NotBlank
    public String name;
    @NotBlank
    public String description;
}
