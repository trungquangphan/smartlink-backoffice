package ch.smartlink.backoffice.common.web.response;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1547401644057895028L;
    private boolean isSuccess = true;
    private String messageInfo;
    private T body;

    public BaseResponse(T groupDtos) {
        this.body = groupDtos;
    }

    public BaseResponse() {

    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}
