package com.wublog.domain;

import lombok.Data;

@Data
public class UploadResult<T> {

    private boolean result;

    private String message;
    private T data;

    public UploadResult() {
    }

    public UploadResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public T data(T data) {
        return data;
    }
}
