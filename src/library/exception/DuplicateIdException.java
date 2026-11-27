package library.exception;
// trùng mã
public class DuplicateIdException extends Exception{
    public DuplicateIdException(String what, Object id){
        super("Mã " + id + " của " + what + " đã tồn tại!");
    }
}
