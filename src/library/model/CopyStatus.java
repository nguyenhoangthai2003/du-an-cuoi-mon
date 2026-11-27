package library.model;
// trạng thái bản sao 
public enum CopyStatus {
    AVAILABLE("Sẵn sàng"),
    BORROWED("Đang mượn"),
    DAMAGED("Hư hỏng"),
    LOST("Mất");

    private final String label;

    CopyStatus(String label) {
        this.label = label;
    }

    public String getLable(){
        return label;
    }

    @Override 
    public String toString(){
        return label;
    }

}
