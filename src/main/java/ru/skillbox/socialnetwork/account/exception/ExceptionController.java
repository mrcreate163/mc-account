package ru.skillbox.socialnetwork.account.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    /**
     * Кастомные исключения для constraint'ов из БД
     */
    private static final Map<String, String> CONSTRAINT_MESSAGES = Map.of(
            "account_email_key", "Такой email уже существует!",
            "account_phone_key", "Такой номер телефона уже существует!"
    );

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessageResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        String exceptionMessage = ex.getMessage();

        if (exceptionMessage != null) {
            for (Map.Entry<String, String> entry: CONSTRAINT_MESSAGES.entrySet()) {
                if (exceptionMessage.contains(entry.getKey())) {
                    exceptionMessage = entry.getValue();
                    break;
                }
            }
            return ResponseEntity.badRequest().body(new ErrorMessageResponse(exceptionMessage));
        }

        // Для обработки других исключений
        throw ex;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
