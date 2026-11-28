package library.model;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Bản sao sách - một cuốn sách vật lý của một đầu sách.
 * Mã bản sao có dạng S001-01 (mã đầu sách + "-" + số thứ tự).
**/
public class BookCopy implements Identifiable<String>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Pattern COPY_ID = Pattern.compile("S\\d{3}-\\d{2,}");
    private static final Pattern BOOK_ID = Pattern.compile("S\\d{3}");

    private final String id;
    private final String bookId;
    private CopyStatus status;

    public BookCopy(String id, String bookId, CopyStatus status) {
        if (id == null || !COPY_ID.matcher(id.trim()).matches()) {
            throw new IllegalArgumentException("Mã bản sao phải dạng S001-01");
        }
        if (bookId == null || !BOOK_ID.matcher(bookId.trim()).matches()) {
            throw new IllegalArgumentException("Mã đầu sách phải dạng S001");
        }
        this.id = id.trim();
        this.bookId = bookId.trim();
        setStatus(status);
    }

    // Ghép mã bản sao từ mã đầu sách và số thứ tự: ("S001", 4) -> "S001-04". 
    public static String makeId(String bookId, int number) {
        return String.format("%s-%02d", bookId, number);
    }

    // Lấy số thứ tự từ mã bản sao: "S001-04" -> 4. Mã sai dạng thì trả 0. 
    public static int numberOf(String copyId) {
        int dash = copyId.lastIndexOf('-');
        if (dash < 0) {
            return 0;
        }
        try {
            return Integer.parseInt(copyId.substring(dash + 1));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public CopyStatus getStatus() {
        return status;
    }

    public void setStatus(CopyStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Thiếu trạng thái bản sao");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return id + " - " + status;
    }
}

