package library.model;
// tình trạng trả sách
public enum ReturnCondition {
    NORMAL("Bình thường"),
    DAMAGED("Hư hỏng"),
    LOST("Mất");

    private final String label;

    ReturnCondition(String label) { this.label = label; }

    public String getLabel() { return label; }

    @Override
    public String toString() { return label; }
}
