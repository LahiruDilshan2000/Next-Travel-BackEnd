package lk.ijse.packageservice.exception;

/**
 * @author Lahiru Dilshan
 * @created Sat 6:17 PM on 10/28/2023
 * @project identity-server
 **/
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String error){
        super(error);
    }
}
