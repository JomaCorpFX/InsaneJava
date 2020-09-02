package com.insaneio.insane.net;

/**
 * Created by Joma Espinoza Bone on 28/07/2016.
 */
public class HttpResponse {

    private String data;
    private int code;
    private String responseMessage;


    public HttpResponse(String data, int code, String responseMessage) {
        this.data = data;
        this.code = code;
        this.responseMessage = responseMessage;
    }



    public int getCode() {
        return code;
    }


    public void setCode(int code) {
        this.code = code;
    }


    public String getData() {
        return data;
    }


    public void setData(String data) {
        this.data = data;
    }


    public String getResponseMessage() {
        return responseMessage;
    }


    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

}
