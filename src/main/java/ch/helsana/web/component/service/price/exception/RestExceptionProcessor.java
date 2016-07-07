package ch.helsana.web.component.service.price.exception;

import ch.helsana.web.component.service.price.exception.vo.ErrorInfo;
import ch.helsana.web.component.service.price.exception.vo.ErrorInfoForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@ControllerAdvice
public class RestExceptionProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionProcessor.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfoForm handleMethodArgumentNotValid(HttpServletRequest req, MethodArgumentNotValidException ex) {
        String errorMessage = ex.getMessage();
        String errorURL = req.getRequestURL().toString();
        ErrorInfoForm errorInfo = new ErrorInfoForm(errorURL, errorMessage);
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        errorInfo.getFieldErrors().addAll(populateFieldErrors(fieldErrors));
        //LOGGER.error(LOGGING_STORE, errorMessage, ex); // TODO: 07.07.2016 LoggingStore
        LOGGER.error(errorMessage, ex);
        return errorInfo;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "ClientException: 001.")
    public ErrorInfoForm handleMethodArgumentNotValid(HttpServletRequest req, ValidationException ex) throws IOException {
        String errorMessage = "Exception Message: \'" + ex.getMessage() + "\'.";
        String errorURL = req.getRequestURL().toString();
        ErrorInfoForm errorInfo = new ErrorInfoForm(errorURL, errorMessage);
        //LOGGER.error(LOGGING_STORE, errorMessage, ex); // TODO: 07.07.2016 LoggingStore
        LOGGER.error(errorMessage, ex);
        return errorInfo;
    }

    /**
     * TODO Could not read JSON: Can not construct instance of.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "There was an error processing the request body.")
    public ErrorInfo handleMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException ex) {
        return makeErrorInfo(req, ex);
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "BusinessException: 001.")
    public ErrorInfo errorHandling(HttpServletRequest req, SystemException ex) throws IOException {
        return makeErrorInfo(req, ex);
    }

    @ExceptionHandler({SystemException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "SystemException: 001.")
    public ErrorInfo errorHandling(HttpServletRequest req, Exception ex) throws IOException {
        return makeErrorInfo(req, ex);
    }

    @ExceptionHandler({FSLException.class})
    public ErrorInfo errorHandling(HttpServletRequest req, HttpServletResponse res, FSLException ex) throws IOException {
        return makeErrorInfo(req, ex);
    }

    private ErrorInfo makeErrorInfo(HttpServletRequest req, Exception ex) {
        String errorMessage =" Exception Message: \'" + ex.getMessage() + "\'.";
        //LOGGER.error(LOGGING_STORE, errorMessage, ex); // TODO: 07.07.2016 LoggingStore
        LOGGER.error(errorMessage, ex);
        return new ErrorInfo(req.getRequestURL().toString(), errorMessage);
    }

    /**
     * Method populates {@link List} of {@link ErrorInfoForm.FieldErrorVO} objects. Each list item contains
     * localized error message and name of a form field which caused the exception.
     *
     * @param fieldErrorList - {@link List} of {@link FieldError} items
     * @return {@link List} of {@link ErrorInfoForm.FieldErrorVO} items
     */
    public List<ErrorInfoForm.FieldErrorVO> populateFieldErrors(List<FieldError> fieldErrorList) {
        List<ErrorInfoForm.FieldErrorVO> fieldErrors = new ArrayList<ErrorInfoForm.FieldErrorVO>();
        StringBuilder errorMessage = new StringBuilder("");

        for (FieldError fieldError : fieldErrorList) {

            errorMessage.append(fieldError.getCode()).append('.');
            errorMessage.append(fieldError.getObjectName()).append('.');
            errorMessage.append(fieldError.getField());

            fieldErrors.add(new ErrorInfoForm.FieldErrorVO(fieldError.getField(), errorMessage.toString()));
            errorMessage.delete(0, errorMessage.capacity());
        }
        return fieldErrors;
    }


}