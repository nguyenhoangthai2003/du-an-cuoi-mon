package library.model;
// trạng thái phiếu mượn
public enum LoanStatus {
    BORROWING("Đang mượn"),
    OVERDUE("Quá hạn"),
    RETURNED("Đã trả");

    private final String label;

    LoanStatus(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

    @Override 
    public String toString(){
        return label;
    }
    


}
