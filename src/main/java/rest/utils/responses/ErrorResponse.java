package rest.utils.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponse extends CustomResponse {

    private String error;
    private HttpStatus status;
    private Object data;

    public ErrorResponse() {
        super(null);
    }

    public ErrorResponse(Object data) {
        this.data = data;
        this.error = null;
    }

    public ResponseEntity<CustomResponse> send(HttpStatus status, String error) {
        this.status = status;
        this.error = error;
        return new ResponseEntity<CustomResponse>(this, status);
    }

    public String getError() {
        return error;
    }
}
