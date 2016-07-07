package ch.helsana.web.component.service.price.exception;

import org.springframework.http.HttpStatus;

/**
 * Backend / Umsysteme / Database
 *  Created by marcelwidmer
 */
public class SystemException extends RestException {

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
