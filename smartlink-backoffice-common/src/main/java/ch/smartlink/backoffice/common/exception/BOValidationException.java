package ch.smartlink.backoffice.common.exception;

public class BOValidationException extends RuntimeException {
    private static final long serialVersionUID = -967711504076257695L;

    private String messageCode;
    private Object[] parameters;

    public BOValidationException(String messageCode) {
        this.messageCode = messageCode;
    }

    public BOValidationException(String messageCode, Object... parameters) {
        this.messageCode = messageCode;
        this.parameters = parameters;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

}
