package com.chippo.linkup.global.exception;

import static com.chippo.linkup.global.exception.ExceptionCode.EXCEED_IMAGE_CAPACITY;
import static com.chippo.linkup.global.exception.ExceptionCode.INTERNAL_SEVER_ERROR;
import static com.chippo.linkup.global.exception.ExceptionCode.INVALID_REQUEST;


import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.Objects;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        log.warn(e.getMessage(), e);

        final String errMessage = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(INVALID_REQUEST.getCode(), errMessage));
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<ExceptionResponse> handleSizeLimitExceededException(final SizeLimitExceededException e) {
        log.warn(e.getMessage(), e);

        final String message = EXCEED_IMAGE_CAPACITY.getMessage()
                + " 입력된 이미지 용량은 " + e.getActualSize() + " byte 입니다. "
                + "(제한 용량: " + e.getPermittedSize() + " byte)";
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(EXCEED_IMAGE_CAPACITY.getCode(), message));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ExceptionResponse> handleAuthException(final AuthException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(AdminException.class)
    public ResponseEntity<ExceptionResponse> handleAdminException(final AdminException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(final BadRequestException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(final Exception e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.internalServerError()
                .body(new ExceptionResponse(INTERNAL_SEVER_ERROR.getCode(), INTERNAL_SEVER_ERROR.getMessage()));
    }
}