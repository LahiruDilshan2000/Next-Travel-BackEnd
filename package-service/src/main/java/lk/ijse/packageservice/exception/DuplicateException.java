package lk.ijse.packageservice.exception;

/**
 * @author Lahiru Dilshan
 * @created Sat 7:09 PM on 10/7/2023
 * @project nexttravel
 **/
public class DuplicateException extends RuntimeException{
    public DuplicateException(String error) {
        super(error);
    }
}
