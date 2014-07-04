package ar.com.insonet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED, reason="Request method 'GET' not supported") //405
public class RequestMethodNotSupportedException extends Exception {
		 
    private static final long serialVersionUID = 1L;

	public RequestMethodNotSupportedException(){
        super("RequestMethodNotSupportedException");
    }
}
