package library.model;

import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class Book implements Identifiable<String>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Pattern ID_PATTERN = Pattern.compile("S\\d{3}");
    private static final int MIN_YEAR = 1900;

    private String id;
    private String title;
    private String author;
    private String category;
    private int publishYear;
    private double price;
    private final List<BookCopy> copies = new ArrayList<>();

    public Book(String id, String title, String author, String category,
                int publishYear, double price) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setCategory(category);
        setPublishYear(publishYear);
        setPrice(price);
    }

    @Override
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public double getPrice() {
        return price;
    }

    //Danh sách bản sao chỉ đọc. Muốn thêm thì gọi addCopy().
    public List<BookCopy> getCopies() {
        return Collections.unmodifiableList(copies);
    }

    public void setId(String id) {
        if (id == null || !ID_PATTERN.matcher(id.trim()).matches()) {
            throw new IllegalArgumentException("Mã đầu sách phải dạng S001");
        }
        this.id = id.trim();
    }

    public void setTitle(String title) {
        this.title = requireText(title, "Tên sách không được rỗng");
    }

    public void setAuthor(String author) {
        this.author = requireText(author, "Tác giả không được rỗng");
    }

    public void setCategory(String category) {
        this.category = requireText(category, "Thể loại không được rỗng");
    }

   public void setPublishYear(int publishYear) {
        int currentYear = Year.now().getValue();
        if (publishYear < MIN_YEAR || publishYear > currentYear) {
            throw new IllegalArgumentException(
                    "Năm xuất bản phải từ " + MIN_YEAR + " đến " + currentYear);
        }
        this.publishYear = publishYear;
    }
    public void setPrice(double price) {
        // viết kiểu "!(price > 0)" để NaN cũng bị từ chối
        if (!(price > 0)) {
            throw new IllegalArgumentException("Giá sách phải > 0");
        }
        this.price = price;
    }

    //Thêm một bản sao vào đầu sách (phải đúng mã đầu sách, không trùng mã). 
    public void addCopy(BookCopy copy) {
        if (copy == null) {
            throw new IllegalArgumentException("Thiếu bản sao");
        }
        if (!copy.getBookId().equals(id)) {
            throw new IllegalArgumentException(
                    "Bản sao " + copy.getId() + " không thuộc đầu sách " + id);
        }
        for (BookCopy c : copies) {
            if (c.getId().equals(copy.getId())) {
                throw new IllegalArgumentException("Trùng mã bản sao " + copy.getId());
            }
        }
        copies.add(copy);
    }

    //Mã bản sao kế tiếp, nối tiếp số lớn nhất đã có: S001-01, S001-02... 
    public String nextCopyId() {
        int max = 0;
        for (BookCopy c : copies) {
            max = Math.max(max, BookCopy.numberOf(c.getId()));
        }
        return BookCopy.makeId(id, max + 1);
    }

    // Số bản sao đang ở trạng thái Sẵn sàng. 
    public long availableCount() {
        return copies.stream()
                .filter(c -> c.getStatus() == CopyStatus.AVAILABLE)
                .count();
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    @Override
    public String toString() {
        return String.format(Locale.forLanguageTag("vi-VN"),
                "%s | %s | %s | %s | %d | %,.0fđ | %d/%d bản rảnh",
                id, title, author, category, publishYear, price,
                availableCount(), copies.size());
    }
}

