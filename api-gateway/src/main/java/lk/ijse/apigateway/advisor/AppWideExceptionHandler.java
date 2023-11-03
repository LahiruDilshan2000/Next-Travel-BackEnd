package lk.ijse.apigateway.advisor;

import lk.ijse.apigateway.util.ResponseUtil;
import org.springframework.http.HttpStatus;
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
}
