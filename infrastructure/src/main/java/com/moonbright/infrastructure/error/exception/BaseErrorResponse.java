package com.moonbright.infrastructure.error.exception;

import com.moonbright.infrastructure.error.ErrorCodeAndDescription;
import jakarta.ws.rs.core.Response;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public record BaseErrorResponse(String message, String errorCode) {

    public static BaseErrorResponse fromErrorCode(ErrorCodeAndDescription errorCodeAndDescription){
        return  new BaseErrorResponse(errorCodeAndDescription.getErrorCode(),errorCodeAndDescription.getErrorCode());
    }
    public static BaseErrorResponse fromException(Throwable throwable){
        return new BaseErrorResponse(throwable.getLocalizedMessage(), Response.Status.INTERNAL_SERVER_ERROR.name());
    }
    public static BaseErrorResponse fromMessageWithLocale(ErrorCodeAndDescription clientUserExistsErrorCode,Locale locale, Object...messageVariables) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("LabelResource", locale);
        return new BaseErrorResponse(MessageFormat.format(resourceBundle.getString(clientUserExistsErrorCode.getErrorCode()),messageVariables),clientUserExistsErrorCode.getErrorCode());
    }

    public static BaseErrorResponse fromMessage(ErrorCodeAndDescription clientUserExistsErrorCode,Object...messageVariables) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("LabelResource");
        return new BaseErrorResponse(MessageFormat.format(resourceBundle.getString(clientUserExistsErrorCode.getErrorCode()),messageVariables),clientUserExistsErrorCode.getErrorCode());
    }
}
