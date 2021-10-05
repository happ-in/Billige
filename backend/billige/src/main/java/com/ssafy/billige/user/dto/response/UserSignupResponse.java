package com.ssafy.billige.user.dto.response;

import io.swagger.annotations.ApiModelProperty;

public class UserSignupResponse {
    @ApiModelProperty(value = "status", position = 1)
    public boolean status;
    @ApiModelProperty(value = "data", position = 2)
    public String data;
    @ApiModelProperty(value = "object", position = 3)
    public Object object;
}