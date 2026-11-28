package library.model;

import java.util.Comparator;

/**
 * Lớp chỉ để chứa các record tiêu chí tìm kiếm, 
 * kết quả và kiểu sắp xếp (constructor private).
 * BookSort, BookCriteria, BookView, BookLoanCount.
 */
public final class Criteria {

    private Criteria() {
    }


    /** Một dòng kết quả tìm sách (FR-08): kèm tổng số bản, số bản còn rảnh, số lượt mượn. */
    public record BookView(String id, String title, String author, String category,
                           int year, int totalCopies, int available, int loanCount) {
    }

    /** Kết quả thống kê TK-01: số lượt mượn của một đầu sách. */
    public record BookLoanCount(String bookId, String title, long loans) {
    }

    /**
     * Kiểu sắp xếp khi tìm sách.
     *  - label      : hiện trong menu
     *  - sql        : đoạn ORDER BY cho bản JDBC (lấy từ enum, KHÔNG lấy từ người dùng nhập)
     *  - comparator : dùng cho bản file / bộ nhớ
     */
    public enum BookSort {
        TITLE("Tên sách", "b.title ASC",
                Comparator.comparing(BookView::title, String.CASE_INSENSITIVE_ORDER)),
        AUTHOR("Tác giả", "b.author ASC, b.title ASC",
                Comparator.comparing(BookView::author, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(BookView::title, String.CASE_INSENSITIVE_ORDER)),
        YEAR_DESC("Năm XB mới nhất", "b.publish_year DESC, b.title ASC",
                Comparator.comparingInt(BookView::year).reversed()
                        .thenComparing(BookView::title, String.CASE_INSENSITIVE_ORDER)),
        LOANS_DESC("Lượt mượn nhiều nhất", "loan_count DESC, b.title ASC",
                Comparator.comparingInt(BookView::loanCount).reversed()
                        .thenComparing(BookView::title, String.CASE_INSENSITIVE_ORDER));

        private final String label;
        private final String sql;
        private final Comparator<BookView> comparator;

        BookSort(String label, String sql, Comparator<BookView> comparator) {
            this.label = label;
            this.sql = sql;
            this.comparator = comparator;
        }

        public String getLabel() {
            return label;
        }

        public String getSql() {
            return sql;
        }

        public Comparator<BookView> getComparator() {
            return comparator;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    /**
     * Tiêu chí tìm sách (FR-08). Thành phần nào null (hoặc false) thì bỏ qua tiêu chí đó.
     * Năm xuất bản tìm theo khoảng [yearFrom, yearTo].
     */
    public record BookCriteria(String title, String author, String category,
                               Integer yearFrom, Integer yearTo,
                               boolean availableOnly, BookSort sort) {

        //Chuẩn hóa: chuỗi rỗng -> null; sort null -> theo tên sách. 
        public BookCriteria {
            title = blankToNull(title);
            author = blankToNull(author);
            category = blankToNull(category);
            if (sort == null) {
                sort = BookSort.TITLE;
            }
        }

        //Không lọc gì, sắp xếp theo tên.
        public static BookCriteria none() {
            return new BookCriteria(null, null, null, null, null, false, BookSort.TITLE);
        }

        private static String blankToNull(String s) {
            return (s == null || s.isBlank()) ? null : s.trim();
        }
    }


    // (C thêm LoanCriteria, LoanSummary ở đây)
    // (D thêm FinePolicy, OverdueItem ở đây)
}
