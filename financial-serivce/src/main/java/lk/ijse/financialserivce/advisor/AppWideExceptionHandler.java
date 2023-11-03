package lk.ijse.financialserivce.advisor;

import lk.ijse.financialserivce.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Lahiru Dilshan
 * @created Sat 7:16 PM on 10/7/2023
 * @project nexttravel
 **/
@RestControllerAdvice
@CrossOrigin
public class AppWideExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({Exception.class})
    public ResponseUtil exceptionHandler(Exception e){
        return ResponseUtil
                .builder()
                .code(500)
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseUtil handleInvalidArgument(MethodArgumentNotValidException e){

        StringBuilder message = new StringBuilder();

        for (FieldError error: e.getFieldErrors()){
            message.append(error.getDefaultMessage()).append("\n");
        }
        return ResponseUtil
                .builder()
                .code(500)
                .message(message.toString())
                .build();
    }
}
