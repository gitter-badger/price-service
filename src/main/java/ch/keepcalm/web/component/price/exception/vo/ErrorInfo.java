package ch.keepcalm.web.component.price.exception.vo;

/**
 * This class is for any kind of error which need to inform a client about error cause.
 */
public class ErrorInfo {
    private String url;
    private String message;

    public ErrorInfo(String url, String message) {
        this.url = url;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}