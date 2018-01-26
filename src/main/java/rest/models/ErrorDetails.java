package rest.models;

import java.util.Date;
import java.util.List;

public class ErrorDetails {

    private Date timestamp;
    private String errorCode;
    private List<String> errorMessages;

    public ErrorDetails(Date timestamp, String message, String error) {
        super();
        this.timestamp = timestamp;
        this.errorCode = message;
    }

    public ErrorDetails(Date timestamp, String message, List<String> errors) {
        super();
        this.timestamp = timestamp;
        this.errorCode = message;
        this.errorMessages = errors;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return the errorMessages
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

}
