package ch.helsana.web.component.service.price.exception.vo;

import java.util.ArrayList;
import java.util.List;

public class ErrorInfoForm extends ErrorInfo {

    //used for form fields
    private List<FieldErrorVO> fieldErrors = new ArrayList<FieldErrorVO>();

    public ErrorInfoForm(String url, String message) {
        super(url, message);
    }

    public ErrorInfoForm(List<FieldErrorVO> fieldErrors, String url, String message) {
        this(url, message);
        this.fieldErrors = fieldErrors;
    }

    public List<FieldErrorVO> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldErrorVO> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public static class FieldErrorVO {
        private String fieldName;
        private String fieldError;

        public FieldErrorVO(String fieldName, String fieldError) {
            this.fieldName = fieldName;
            this.fieldError = fieldError;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldError() {
            return fieldError;
        }

        public void setFieldError(String fieldError) {
            this.fieldError = fieldError;
        }
    }
}