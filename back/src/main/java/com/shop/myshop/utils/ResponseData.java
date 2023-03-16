package com.shop.myshop.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
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
    private final Map<String, Object> data = new HashMap<>();

    public ResponseData add(String key, Object value) {
        if (key == null) {
            throw new NullPointerException("Key is null");
        }
        data.put(key, value);
        return this;
    }

    public static ResponseData fromResult(RequestResultEnum result) {
        return new ResponseData(result.getCode(), result.getStatus(), result.getMessage());
    }

    public static ResponseData fromException(Exception e) {
        return new ResponseData(
                RequestResultEnum.INTERNAL_SERVER_ERROR.getCode(),
                RequestResultEnum.INTERNAL_SERVER_ERROR.getStatus(),
                e.getMessage(),
                Collections.emptyMap()
        );
    }

    public ResponseData(int code, int status, String message, Map<String, Object> data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data.putAll(data);
    }
}
