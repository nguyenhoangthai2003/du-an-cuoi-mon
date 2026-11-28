package library.model;

import java.util.List;

//Một trang kết quả. page đếm từ 1.
 
public record Page<T>(List<T> items, int page, int size, long totalItems) {

    //Tổng số trang = làm tròn lên (totalItems / size). 
    public int totalPages() {
        if (size <= 0) {
            return 0;
        }
        return (int) ((totalItems + size - 1) / size);
    }

    //Cắt một danh sách đã lọc + sắp xếp thành một trang (dùng cho bản file / bộ nhớ).
    public static <T> Page<T> of(List<T> all, int page, int size) {
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 10;
        }
        List<T> items = all.stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();
        return new Page<>(items, page, size, all.size());
    }
}
