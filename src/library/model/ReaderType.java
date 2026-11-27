package library.model;
// loại độc giả
public enum ReaderType {
    STUDENT("Sinh viên",3,14),
    LECTURER("Giảng viên",5,30);

    private final String label;
    private final int maxBooks;   
    private final int loanDays;

    ReaderType(String label, int maxBooks, int loanDays){
        this.label = label;
        this.maxBooks = maxBooks;
        this.loanDays = loanDays;
    }
    public String getLabel() { return label; }

    public int getMaxBooks() { return maxBooks; }

    public int getLoanDays() { return loanDays; }

    @Override
    public String toString() { return label; }
    


}
