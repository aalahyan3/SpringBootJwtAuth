package com.test.payload;

public class ApiResponse<T> {
    private int status;
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(int s, boolean success, String m, T d) {
        this.status = s;
        this.success = success;
        this.message = m;
        this.data = d;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setStatus(int s) {
        this.status = s;
    }

    public void setMessage(String m) {
        this.message = m;
    }

    public void setData(T d) {
        this.data = d;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
