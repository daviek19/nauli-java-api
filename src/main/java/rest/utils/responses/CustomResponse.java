package rest.utils.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponse {

    private HttpStatus status;
    private Object data;

    public CustomResponse() {
        this(null);
    }

    public CustomResponse(Object data) {
        this.data = data;
    }

    public ResponseEntity<CustomResponse> send(HttpStatus status) {
        this.status = status;
        return new ResponseEntity<CustomResponse>(this, status);
    }

    public Object getData() {
        return data;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
