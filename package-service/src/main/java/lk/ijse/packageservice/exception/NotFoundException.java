package lk.ijse.packageservice.exception;

/**
 * @author Lahiru Dilshan
 * @created Sat 7:14 PM on 10/7/2023
 * @project nexttravel
 **/
public class NotFoundException extends RuntimeException{
    public NotFoundException(String error) {
        super(error);
    }
}
