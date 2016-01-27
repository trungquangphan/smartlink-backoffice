package ch.smartlink.backoffice.common.dto;

import java.io.Serializable;

public class LabelValueBean implements Serializable {
    private static final long serialVersionUID = 4249404231383308819L;
    private String code;
    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
