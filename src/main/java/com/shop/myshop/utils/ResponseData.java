package com.shop.myshop.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData implements Serializable {

    private int code;
    private int status;
    private String message;

    private Map<String, Object> data = new HashMap<>();

    public ResponseData(RequestResultEnum result) {
        this.code = result.getCode();
        this.status = result.getStatus();
        this.message = result.getMessage();
    }

}
