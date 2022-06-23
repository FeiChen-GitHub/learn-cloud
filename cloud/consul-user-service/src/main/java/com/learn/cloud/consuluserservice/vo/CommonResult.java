package com.learn.cloud.consuluserservice.vo;

public class CommonResult<T> {
    private String msg;
    private Integer code;
    private T data;

    public CommonResult() {
    }

    public CommonResult(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public CommonResult(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
