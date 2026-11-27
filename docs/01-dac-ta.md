Đặc tả yêu cầu – Đề tài 2: Thư viện mượn – trả

> Bản chép từ file đặc tả `12_11_2026` của giảng viên. Cột **Phụ trách** ở mục 2 đã điền theo bảng phân công nhóm (A–D, ghi tên thật vào `README.md`). Ô "năm xuất bản" ở mục 9.2 (`Book`) đã bổ sung "năm hiện tại" theo BR-08 vì bản gốc bị cắt chữ.

**Dành cho sinh viên.** Mục 1–7 cho biết phần mềm phải làm được gì; mục 8 cho biết làm theo thứ tự nào, dùng kiến thức slide nào.

Mô hình dữ liệu chi tiết, script SQL, đoạn code gợi ý: đề 02-Thu-vien-muon-tra.md. Khung project chung: 00-Huong-dan-chung.md.

Nhóm chép file này vào docs/01-dac-ta.md của repo nhóm, điền cột "Phụ trách" và cập nhật khi thay đổi.

**Mục 9 – Thiết kế lớp** liệt kê từng lớp cần viết: thuộc tính (kiểu, ý nghĩa, kiểm tra) và phương thức (tham số, ngoại lệ, việc làm).

| **Thông tin** |  |
| --- | --- |
| Độ khó | ★★ · nhóm 4–5 người · 18 buổi |
| Database | library_loan |
| Project mẫu để đối chiếu | 02-Thu-vien-muon-tra/ |
| Tái sử dụng | Được dùng lại phần đầu sách / bản sao của Assignment cá nhân; nhóm tập trung vào độc giả và mượn – trả |

# 1. Tổng quan

## 1.1 Mục tiêu

Phần mềm console cho thư viện trường: quản lý đầu sách và từng bản sao, độc giả (sinh viên, giảng viên), lập phiếu mượn nhiều cuốn, trả từng cuốn hoặc cả phiếu, tự tính tiền phạt trễ hạn / hỏng / mất, tự đánh dấu phiếu quá hạn. Giai đoạn 1 lưu file, giai đoạn 2 lưu SQL Server.

## 1.2 Phạm vi

| **Trong phạm vi** | **Ngoài phạm vi** |
| --- | --- |
| Đầu sách, bản sao, độc giả, phiếu mượn nhiều bản sao | Đặt trước sách (điểm cộng) |
| Mượn, trả, gia hạn, phạt, đánh dấu quá hạn | Thu tiền phạt qua cổng thanh toán |
| Tìm sách, tra cứu phiếu, thống kê, xuất HTML/CSV | Gửi email thật (chỉ in nội dung ra console) |
| Chuyển dữ liệu file → CSDL | Đăng nhập có mật khẩu |

## 1.3 Người dùng

| **Vai trò** | **Được làm** |
| --- | --- |
| Thủ thư | Mọi chức năng: quản lý sách, độc giả, mượn – trả, thống kê |
| Độc giả | Không dùng trực tiếp phần mềm; là đối tượng được quản lý |

## 1.4 Thuật ngữ

| **Thuật ngữ** | **Nghĩa** |
| --- | --- |
| Đầu sách | Một tựa sách (tên, tác giả, năm, giá) |
| Bản sao | Một cuốn sách vật lý của đầu sách, mã dạng S001-01 |
| Phiếu mượn | Một lần mượn của một độc giả, gồm nhiều bản sao, có hạn trả chung |
| Quá hạn | Phiếu chưa trả hết mà đã qua ngày hạn trả |
| Loại độc giả | Sinh viên: tối đa 3 cuốn, mượn 14 ngày · Giảng viên: tối đa 5 cuốn, mượn 30 ngày |

# 2. Yêu cầu chức năng

| **Mã** | **Chức năng** | **GĐ1** | **GĐ2** | **Slide** | **Phụ trách** |
| --- | --- | --- | --- | --- | --- |
| FR-01 | Thêm / sửa / xóa / xem đầu sách | ✔ | ✔ | 1, 2, 4 | A |
| FR-02 | Thêm nhiều bản sao cho một đầu sách một lần; xem, đổi trạng thái bản sao | ✔ | ✔ Batch | 2, 7 | A |
| FR-03 | Thêm / sửa / xem độc giả; khóa / mở khóa độc giả | ✔ | ✔ | 1, 2 | B |
| FR-04 | **Mượn sách: một phiếu nhiều bản sao** | ✔ | ✔ Transaction | 2, 4, 7 | C |
| FR-05 | **Trả sách: từng cuốn hoặc cả phiếu, ghi tình trạng, tính phạt, in biên lai** | ✔ | ✔ Transaction | 4, 7, 8 | D |
| FR-06 | Gia hạn phiếu (một lần, +7 ngày) | ✔ | ✔ | 4 | C |
| FR-07 | Tự đánh dấu phiếu quá hạn mỗi khi khởi động | ✔ | ✔ | 3, 7 | C |
| FR-08 | Tìm sách nhiều tiêu chí + sắp xếp + phân trang, hiện số bản còn rảnh | ✔ | ✔ JOIN | 3, 6 | A |
| FR-09 | Tra cứu phiếu mượn (độc giả, trạng thái, khoảng ngày) + phân trang | ✔ | ✔ JOIN | 3, 6 | C |
| FR-10 | Thống kê (mục 6) | ✔ | ✔ | 3, 8 | D (chủ trì) |
| FR-11 | Xuất danh sách quá hạn ra HTML hoặc CSV | ✔ | ✔ | 5, 8 | D |
| FR-12 | Lưu toàn bộ dữ liệu, mở lại còn nguyên | ✔ | ✔ | 5, 6 | B |
| FR-13 | Chọn nguồn dữ liệu File / SQL Server khi khởi động | ✔ | ✔ | 1, 4 | B |
| FR-14 | Chuyển dữ liệu từ file vào CSDL |  | ✔ | 5, 6, 7 | B |

## 2.1 Chi tiết các chức năng trọng tâm

### FR-02 Thêm nhiều bản sao

Nhập mã đầu sách và số lượng N → hệ thống sinh N bản sao nối tiếp số thứ tự đã có (S001-04, S001-05…), trạng thái "Có sẵn". GĐ2 ghi bằng một lệnh batch.

### FR-04 Mượn sách

- **Đầu vào:** mã độc giả, danh sách mã bản sao.

- **Luồng chính:**

- Kiểm tra độc giả: tồn tại, không bị khóa, không có phiếu quá hạn (BR-02, BR-03).

- Kiểm tra số cuốn đang mượn + số cuốn mượn mới ≤ giới hạn theo loại độc giả (BR-01).

- Kiểm tra từng bản sao đang "Có sẵn" (BR-04).

- Tạo phiếu: ngày mượn = hôm nay, hạn trả = hôm nay + số ngày mượn theo loại độc giả; các bản sao chuyển sang "Đang mượn".

- In mã phiếu và hạn trả.

- **Nghiệm thu:** một bản sao không hợp lệ thì **không** bản sao nào đổi trạng thái và không có phiếu (TC-02).

### FR-05 Trả sách

- **Đầu vào:** mã phiếu; chọn trả cả phiếu hoặc chọn từng bản sao; với mỗi bản sao chọn tình trạng Bình thường / Hỏng / Mất.

- **Luồng chính:**

- Với mỗi bản sao: ghi ngày trả, tình trạng, tiền phạt (BR-06).

- Trạng thái bản sao: Bình thường → "Có sẵn"; Hỏng → "Hỏng"; Mất → "Mất".

- Nếu mọi bản sao của phiếu đã trả → phiếu chuyển "Đã trả".

- In **biên lai** (Text Block): tên độc giả, từng cuốn, số ngày trễ, tiền phạt từng cuốn, tổng phạt.

- **Luồng lỗi:** bản sao không thuộc phiếu → NotFoundException; bản sao đã trả → BusinessException.

### FR-06 Gia hạn

Chỉ phiếu "Đang mượn", chưa quá hạn, chưa gia hạn lần nào: hạn trả +7 ngày, đánh dấu đã gia hạn. Sai điều kiện → BR-05.

### FR-07 Đánh dấu quá hạn

Mỗi lần khởi động, mọi phiếu "Đang mượn" có hạn trả trước hôm nay chuyển sang "Quá hạn"; in số phiếu vừa chuyển.

### FR-08 Tìm sách

- **Tiêu chí:** tên, tác giả, thể loại, năm xuất bản từ – đến, "chỉ sách còn bản rảnh".

- **Mỗi dòng:** mã, tên, tác giả, thể loại, năm, tổng số bản, số bản còn rảnh.

- **Sắp xếp:** tên, tác giả, năm, số lượt mượn. Phân trang có "Trang x/y".

### FR-14 Chuyển dữ liệu file → CSDL

Thứ tự: đầu sách → bản sao → độc giả → phiếu mượn (kèm chi tiết). Bỏ qua bản ghi trùng mã; in số bản ghi đã chuyển / bỏ qua.

# 3. Quy tắc nghiệp vụ

| **Mã** | **Quy tắc** | **Khi vi phạm** | **Áp dụng** |
| --- | --- | --- | --- |
| BR-01 | Số cuốn đang mượn + mượn mới ≤ giới hạn của loại độc giả (SV 3, GV 5) | BusinessException – "Sinh viên chỉ được mượn tối đa 3 cuốn, đang mượn 2" | FR-04 |
| BR-02 | Độc giả có phiếu quá hạn không được mượn mới | BusinessException | FR-04 |
| BR-03 | Độc giả bị khóa không được mượn | BusinessException | FR-04 |
| BR-04 | Chỉ mượn được bản sao "Có sẵn" | BusinessException – "Bản sao S001-02 đang được mượn" | FR-04 |
| BR-05 | Chỉ gia hạn 1 lần, khi phiếu chưa quá hạn | BusinessException | FR-06 |
| BR-06 | Tiền phạt mỗi cuốn = 5.000đ × số ngày trễ (không âm) + 30% giá sách nếu hỏng hoặc + 100% giá sách nếu mất | — | FR-05 |
| BR-07 | Hạn trả = ngày mượn + số ngày mượn của loại độc giả; hạn trả phải sau ngày mượn | — | FR-04 |
| BR-08 | Mã đầu sách, bản sao, độc giả là duy nhất; năm xuất bản 1900 – năm hiện tại; giá > 0 | DuplicateIdException / báo lỗi khi nhập | FR-01..03 |
| BR-09 | Không xóa đầu sách đang có bản sao được mượn | BusinessException | FR-01 |

Ví dụ tính phạt: sách giá 120.000đ, trả trễ 4 ngày, bình thường → 20.000đ; trả trễ 4 ngày và báo hỏng → 20.000 + 36.000 = 56.000đ; báo mất, không trễ → 120.000đ.

# 4. Yêu cầu dữ liệu

| **Thực thể** | **Thông tin cần lưu** | **Kiểm tra** |
| --- | --- | --- |
| Đầu sách | mã, tên, tác giả, thể loại, năm xuất bản, giá, danh sách bản sao | BR-08 |
| Bản sao | mã (S001-01), mã đầu sách, trạng thái |  |
| Độc giả | mã, tên, SĐT, loại độc giả, đang hoạt động / bị khóa |  |
| Phiếu mượn | mã, mã độc giả, ngày mượn, hạn trả, đã gia hạn chưa, trạng thái, danh sách chi tiết | BR-07 |
| Chi tiết phiếu | mã bản sao, ngày trả (trống = chưa trả), tình trạng khi trả, tiền phạt |  |

Enum: trạng thái bản sao (Có sẵn / Đang mượn / Hỏng / Mất), **loại độc giả có thuộc tính số cuốn tối đa và số ngày mượn**, trạng thái phiếu (Đang mượn / Quá hạn / Đã trả), tình trạng khi trả (Bình thường / Hỏng / Mất).

Dữ liệu mẫu tối thiểu: 15 đầu sách, ≥ 30 bản sao, 10 độc giả (có cả 2 loại), 15 phiếu mượn trong đó có phiếu quá hạn và phiếu đã trả có phạt. Script SQL: đề mục 3.4.

# 5. Yêu cầu phi chức năng

| **Mã** | **Yêu cầu** |
| --- | --- |
| NFR-01 | Không bao giờ dừng vì lỗi; mọi lỗi báo bằng tiếng Việt rồi quay lại menu |
| NFR-02 | Chia tầng ui → service → interface repository; SQL chỉ nằm trong repository/jdbc |
| NFR-03 | 100% PreparedStatement; cột sắp xếp lấy từ enum |
| NFR-04 | Đổi File / SQL Server lúc khởi động, không sửa code; tài khoản DB trong db.properties |
| NFR-05 | Số ngày trễ tính bằng java.time (ChronoUnit.DAYS.between), không tự cộng trừ ngày tay |
| NFR-06 | Tiền có dấu phân cách hàng nghìn; ngày dạng dd/MM/yyyy |
| NFR-07 | Đặt tên theo Quy-tac-dat-ten.md; mỗi thành viên commit phần mình làm |

# 6. Thống kê và báo cáo

Viết bằng Stream, kết quả dạng record. Làm **ít nhất 5**.

| **Mã** | **Thống kê** |
| --- | --- |
| TK-01 | Top 5 đầu sách được mượn nhiều nhất |
| TK-02 | Độc giả đang quá hạn kèm số ngày trễ (dùng cho FR-11) |
| TK-03 | Tổng tiền phạt theo tháng |
| TK-04 | Số bản sao theo từng trạng thái |
| TK-05 | Thể loại được mượn nhiều nhất |
| TK-06 | Số lượt mượn trung bình: sinh viên so với giảng viên |
| TK-07 | Đầu sách cần mua thêm: không còn bản rảnh nhưng có ≥ 3 lượt mượn trong tháng |

FR-11: danh sách quá hạn gồm mã độc giả, tên, SĐT, mã phiếu, hạn trả, số ngày trễ, tiền phạt tạm tính. HTML viết bằng Text Block; CSV mở được bằng Excel (UTF-8).

# 7. Kịch bản nghiệm thu

| **Mã** | **Thao tác** | **Kết quả mong đợi** | **Liên quan** |
| --- | --- | --- | --- |
| TC-01 | Sinh viên mượn 3 cuốn, rồi mượn thêm cuốn thứ 4 | Lần 1 thành công; lần 2 báo vượt giới hạn | FR-04, BR-01 |
| TC-02 | Mượn 2 bản sao, trong đó 1 bản đang được mượn | Báo lỗi; **bản còn lại vẫn ****"****Có sẵn****"**, không có phiếu mới | FR-04, BR-04 |
| TC-03 | Sửa hạn trả một phiếu về quá khứ (GĐ2 sửa bằng SSMS), khởi động lại | Phiếu thành "Quá hạn"; độc giả đó không mượn được | FR-07, BR-02 |
| TC-04 | Trả trễ 4 ngày, bình thường | Phạt 20.000đ, bản sao "Có sẵn" | FR-05, BR-06 |
| TC-05 | Trả báo mất | Phạt bằng giá sách (cộng tiền trễ nếu có), bản sao "Mất" | FR-05 |
| TC-06 | Trả lần lượt đủ các cuốn của phiếu | Phiếu chuyển "Đã trả", biên lai in đúng | FR-05 |
| TC-07 | Gia hạn 2 lần; gia hạn phiếu quá hạn | Cả hai báo lỗi | FR-06, BR-05 |
| TC-08 | Độc giả bị khóa mượn sách | Báo lỗi | BR-03 |
| TC-09 | Tìm sách "Java", chỉ sách còn bản rảnh, trang 2 | Đúng kết quả, có cột số bản rảnh | FR-08 |
| TC-10 | Thêm 5 bản sao cho S001 đang có 3 bản | Sinh S001-04 … S001-08 | FR-02 |
| TC-11 | Nhập chữ ở ô số lượng; nhập mã không tồn tại | Báo lỗi, hỏi lại, không thoát | NFR-01 |
| TC-12 | Tắt mở lại chương trình; chuyển dữ liệu file → DB hai lần | Dữ liệu còn nguyên; lần chuyển thứ hai bỏ qua bản ghi trùng | FR-12, FR-14 |

# 8. Các bước thực hiện theo 8 slide

Mỗi bước ứng với một slide. Cột "Xem lại" trỏ tới Demo Slide (mở Demo Slide/demo-slide.html để xem trực quan).

## Bước 1 – Buổi 1–2 · Slide 1: OOP

**Kiến thức:** class, constructor, đóng gói và kiểm tra trong setter, interface, đa hình.

**Việc cần làm**

- Phân công theo thực thể; viết docs/01-dac-ta.md; vẽ class diagram Book, BookCopy, Reader, Loan, LoanDetail (thuộc tính, phương thức theo mục 9.2).

- Viết package model: field private, constructor gọi setter, setter ném IllegalArgumentException khi sai BR-08.

- Book chứa List<BookCopy>; Loan chứa List<LoanDetail>; tham chiếu khác lưu bằng mã (Loan.readerId).

- Interface Identifiable<ID> cho các thực thể.

- Nếu dùng lại Assignment: chép Document/Copy, đổi tên cho khớp đề, kiểm tra lại validate.

**Bàn giao:** đặc tả, class diagram, model biên dịch được.

**Tự kiểm tra**

☐  Năm xuất bản 1800 hoặc năm sau bị từ chối

☐  Loan có method trả về số cuốn chưa trả

**Xem lại:** Demo Slide slide1/demo1, slide1/demo2.

## Bước 2 – Buổi 3–4 · Slide 2: Enum, Collection, Map

**Kiến thức:** enum có thuộc tính, values(), switch trên enum; List, Set, Map.

**Việc cần làm**

- 4 enum có nhãn tiếng Việt. ReaderType có thêm maxBooks và loanDays (STUDENT("Sinh viên", 3, 14), LECTURER("Giảng viên", 5, 30)); service đọc giới hạn từ enum, **không** viết số 3, 5, 14, 30 trong service.

- Repository bộ nhớ bằng Map<String, T>; CRUD FR-01, 02, 03.

- FR-02 sinh mã bản sao nối tiếp.

- Menu console + lớp Input an toàn.

**Bàn giao:** CRUD sách, bản sao, độc giả trên bộ nhớ.

**Tự kiểm tra**

☐  Đổi giới hạn sinh viên thành 4 chỉ cần sửa enum

☐  TC-10 đạt

**Xem lại:** Demo Slide slide2/demo1, slide2/demo2 (enum có thuộc tính và quy tắc), slide2/demo4 (Map).

## Bước 3 – Buổi 5–6 · Slide 3: Lambda & Stream

**Kiến thức:** filter, map, sorted, skip/limit, groupingBy, counting, averagingInt, flatMap; lớp EnumMap.

**Việc cần làm**

- FR-08 trên bộ nhớ, tính "số bản còn rảnh" bằng Stream trên danh sách bản sao.

- record Page<T> và phân trang bằng skip/limit.

- FR-07 bản bộ nhớ: filter(status == BORROWING && dueDate.isBefore(today)).forEach(...).

- ReportService ≥ 5 thống kê mục 6 (TK-04 dùng groupingBy(..., () -> new EnumMap<>(CopyStatus.class), counting())).

**Bàn giao:** tìm sách + phân trang, ≥ 5 thống kê.

**Tự kiểm tra**

☐  TC-09 đạt trên bộ nhớ

☐  TK-01 dùng flatMap từ phiếu sang chi tiết

**Xem lại:** Demo Slide slide3/vidu/FilterExample, slide3/demo.

## Bước 4 – Buổi 7–8 · Slide 4: Exception & Generics

**Kiến thức:** custom exception checked / unchecked, throws, GenericRepository<T, ID>, Optional.

**Việc cần làm**

- 4 exception chung (DuplicateIdException, NotFoundException, BusinessException, DataAccessException).

- GenericRepository<T, ID>; các repository bộ nhớ cài interface này.

- LoanService: FR-04 (kiểm tra BR-01..04 **trước**, hợp lệ hết mới đổi dữ liệu), FR-05 (tính phạt BR-06, nên gom vào record FinePolicy(perDay, damagedRate, lostRate)), FR-06.

- Menu bắt mọi exception, không crash.

**Bàn giao:** mượn, trả, gia hạn chạy trên bộ nhớ.

**Tự kiểm tra**

☐  TC-01, 02, 04, 05, 06, 07, 08, 11 đạt trên bộ nhớ

☐  Không có catch rỗng

**Xem lại:** Demo Slide slide4/demo5, slide4/demo3 (custom exception).

## Bước 5 – Buổi 9–10 · Slide 5: I/O → **Mốc 1**

**Kiến thức:** Serializable, Object Stream, try-with-resources, ghi file văn bản UTF-8.

**Việc cần làm**

- model cài Serializable; FileRepository<T, ID> lưu Map vào data/*.dat.

- Xử lý file không có / rỗng / hỏng (đổi tên .bak, cảnh báo).

- FR-07 chạy khi khởi động trên dữ liệu đọc từ file.

- FR-11 bản CSV bằng BufferedWriter.

**Bàn giao (Mốc 1):** demo giai đoạn 1 theo 00-Huong-dan-chung mục 5.

**Tự kiểm tra**

☐  TC-12 phần "tắt mở lại" đạt

☐  File CSV mở bằng Excel không lỗi font

**Xem lại:** Demo Slide slide5/demo4, slide5/demo2.

## Bước 6 – Buổi 11–12 · Slide 6: JDBC

**Kiến thức:** Connection, PreparedStatement, ResultSet, ánh xạ dòng → đối tượng, cột NULL.

**Việc cần làm**

- ERD; sql/01_schema.sql, sql/02_sample_data.sql.

- DBConnect đọc db.properties; phần đọc của các repository JDBC.

- FR-08 bằng LEFT JOIN book_copy + GROUP BY + HAVING (đề mục 6), phân trang OFFSET … FETCH + COUNT(*).

- FR-09 JOIN loan – reader.

- Đọc cột return_date có thể NULL (dùng rs.getDate(...) rồi kiểm tra null).

**Bàn giao:** chế độ SQL Server tìm sách, tra cứu phiếu.

**Tự kiểm tra**

☐  Script chạy trên máy sạch không lỗi

☐  Không có SQL ghép chuỗi giá trị người dùng nhập

**Xem lại:** Demo Slide slide6/demo (tab "Chạy từng bước"), slide6/vidu/TungBuocJdbc.

## Bước 7 – Buổi 13–14 · Slide 7: JDBC nâng cao

**Kiến thức:** executeUpdate, transaction, batch, UPDATE có điều kiện, CallableStatement.

**Việc cần làm**

- FR-04 transaction: INSERT loan → batch INSERT loan_detail → từng bản sao UPDATE book_copy SET status='BORROWED' WHERE id=? AND status='AVAILABLE' (0 dòng ⇒ rollback).

- FR-05 transaction: cập nhật chi tiết có điều kiện return_date IS NULL, cập nhật bản sao, đóng phiếu bằng NOT EXISTS (đề mục 5.2).

- FR-02 batch thêm bản sao; FR-07 bằng một câu UPDATE có điều kiện.

- Phần ghi còn lại của repository JDBC.

- Điểm cộng: stored procedure sp_overdue_readers.

**Bàn giao:** mọi nghiệp vụ chạy trên SQL Server, demo được rollback.

**Tự kiểm tra**

☐  TC-02, 03, 06 đạt ở chế độ SQL Server, xác nhận bằng SSMS

☐  Các lệnh của một lần mượn dùng cùng một Connection

**Xem lại:** Demo Slide slide7/demo1, slide7/vidu/TransactionDemo, slide7/vidu/BatchDemo.

## Bước 8 – Buổi 15 · Slide 8: Record, Annotation, Text Block

**Việc cần làm**

- Đổi kết quả thống kê, FinePolicy sang record.

- Biên lai FR-05 và báo cáo HTML FR-11 bằng Text Block + formatted().

- SQL dài (tìm sách, đóng phiếu) viết bằng Text Block.

- Annotation @Author cho service, đọc bằng Reflection.

- Điểm cộng: "email nhắc trả sách" in ra console bằng Text Block.

**Tự kiểm tra**

☐  Biên lai in đúng tiếng Việt, số tiền có dấu phân cách

☐  Không còn SQL nhiều dòng nối bằng +

**Xem lại:** Demo Slide slide8/vidu/TextBlockDemo, RecordDemo, AnnotationDemo.

## Bước 9 – Buổi 16–18: Tích hợp, kiểm thử chéo, bảo vệ

- FR-14 chuyển file → DB (TC-12).

- Kiểm thử chéo bằng toàn bộ mục 7, ghi biên bản lỗi và cách sửa.

- Hoàn thiện README.md, docs/, AI_LOG.md; chạy checklist 00-Huong-dan-chung mục 7.

- Mỗi thành viên chuẩn bị giải thích phần mình làm và các câu ở đề mục 12.

# 9. Thiết kế lớp

Mục này liệt kê **các lớp cần viết, mỗi lớp có thuộc tính nào, phương thức nào**. Thiết kế lấy đúng theo project mẫu 02-Thu-vien-muon-tra/, nên nhóm có thể đối chiếu code khi bí. Nhóm được đổi tên, gộp hoặc tách lớp, nhưng phải giữ đủ trách nhiệm và giữ cách chia tầng dưới đây.

Quy ước chung cho mọi lớp:

- Thuộc tính luôn private. Lớp thực thể có getter cho mọi thuộc tính; setter kiểm tra dữ liệu, sai thì ném IllegalArgumentException với thông báo tiếng Việt (cột "Ràng buộc").

- Constructor có tham số **gọi lại setter** để không viết kiểm tra hai lần.

- Lớp thực thể cài Identifiable<ID> (có getId()) và Serializable (để ghi file bằng Object Stream ở Bước 5).

- Enum có label tiếng Việt, getLabel() và toString() trả về label để in menu.

- Tầng trên chỉ gọi tầng dưới qua **interface**: ui → service → repository (interface) ← repository.file / repository.jdbc. service và ui không chứa SQL, không đọc ghi file.

- Cột "Bước" trong tài liệu này: thực thể và enum viết ở Bước 1–2; tìm kiếm, thống kê ở Bước 3; exception, repository ở Bước 4; bản file ở Bước 5; bản JDBC ở Bước 6–7; record, @Author ở Bước 8.

## 9.1 Sơ đồ gói và lớp

```
app/
  Main
  model/            Book, BookCopy, CopyStatus, Criteria, Identifiable,
                    Loan, LoanDetail, LoanStatus, Page, Reader, ReaderType,
                    ReturnCondition
  exception/        BusinessException, DataAccessException,
                    DuplicateIdException, NotFoundException
  repository/       GenericRepository, Repositories
  repository/file/  FileRepositories, FileRepository
  repository/jdbc/  DBConnect, JdbcRepositories, JdbcRepository, SqlFilter
  service/          BookService, LoanService, MigrationService,
                    ReportService, SampleData
  ui/               ConsoleUI, Input
  annotation/       Author
```

Quan hệ giữa các thực thể:

- Book ◆ BookCopy qua copies (1–N, dòng con không tồn tại độc lập, lưu cùng Book)

- BookCopy → Book qua bookId (N–1, lưu mã như khóa ngoại)

- Loan → Reader qua readerId (N–1, lưu mã như khóa ngoại)

- Loan ◆ LoanDetail qua details (1–N, dòng con không tồn tại độc lập, lưu cùng Loan)

- LoanDetail → BookCopy qua copyId (N–1, lưu mã như khóa ngoại)

## 9.2 Lớp thực thể (package model) – Bước 1, 2

### Book – Đầu sách

Đầu sách – thực thể gốc chứa List bản sao. Cài đặt: Identifiable<String>, Serializable.

| **Thuộc tính** | **Kiểu** | **Ý nghĩa** | **Kiểm tra (thông báo lỗi khi sai) / ghi chú** |
| --- | --- | --- | --- |
| id | String | Mã đầu sách | Mã đầu sách phải dạng S001 |
| title | String | Tên sách | Tên sách không được rỗng |
| author | String | Tác giả | Tác giả không được rỗng |
| category | String | Thể loại | Thể loại không được rỗng |
| publishYear | int | Năm xuất bản | Năm xuất bản phải từ 1900 đến năm hiện tại |
| price | double | Giá sách (dùng tính phạt) | Giá sách phải > 0 |
| copies | List<BookCopy> | Danh sách bản sao (1–N) | Khởi tạo rỗng, thêm qua phương thức của lớp |

| **Phương thức** | **Mô tả** |
| --- | --- |
| Book(String id, String title, String author, String category, int publishYear, double price) | Constructor đầy đủ tham số; gọi setter nên dùng chung phần kiểm tra |
| getId() : String | Trả mã (cài đặt Identifiable) |
| get…() / is…() | Getter cho mọi thuộc tính |
| set…(…) | Setter có kiểm tra (sai → IllegalArgumentException): setId, setTitle, setAuthor, setCategory, setPublishYear, setPrice |
| nextCopyId() : String | Mã bản sao kế tiếp: S001-01, S001-02… |
| availableCount() : long | Số bản sao đang ở trạng thái Sẵn sàng |
| toString() : String | Chuỗi một dòng để in danh sách |

### BookCopy – Bản sao sách

Bản sao vật lý của một đầu sách. Cài đặt: Serializable.

| **Thuộc tính** | **Kiểu** | **Ý nghĩa** | **Kiểm tra (thông báo lỗi khi sai) / ghi chú** |
| --- | --- | --- | --- |
| id | String | Mã bản sao sách | final: gán một lần trong constructor (kiểm tra ở constructor) |
| bookId | String | Mã đầu sách | final: gán một lần trong constructor (kiểm tra ở constructor) |
| status | CopyStatus | Trạng thái | Thiếu trạng thái bản sao |

| **Phương thức** | **Mô tả** |
| --- | --- |
| BookCopy(String id, String bookId, CopyStatus status) | Constructor; kiểm tra: Mã bản sao phải dạng S001-01 |
| getId() : String | Trả mã (cài đặt Identifiable) |
| get…() / is…() | Getter cho mọi thuộc tính |
| set…(…) | Setter có kiểm tra (sai → IllegalArgumentException): setStatus |
| toString() : String | Chuỗi một dòng để in danh sách |

### Loan – Phiếu mượn

Phiếu mượn – thực thể gốc chứa List chi tiết. Cài đặt: Identifiable<String>, Serializable.

| **Thuộc tính** | **Kiểu** | **Ý nghĩa** | **Kiểm tra (thông báo lỗi khi sai) / ghi chú** |
| --- | --- | --- | --- |
| id | String | Mã phiếu mượn | Không có setter; chỉ đổi qua phương thức nghiệp vụ của lớp |
| readerId | String | Mã độc giả | Không có setter; chỉ đổi qua phương thức nghiệp vụ của lớp |
| borrowDate | LocalDate | Ngày mượn | Không có setter; chỉ đổi qua phương thức nghiệp vụ của lớp |
| dueDate | LocalDate | Hạn trả | Hạn trả không hợp lệ |
| extended | boolean | Đã gia hạn chưa (chỉ được 1 lần) | — |
| status | LoanStatus | Trạng thái | Thiếu trạng thái phiếu |
| details | List<LoanDetail> | Danh sách dòng chi tiết (quan hệ 1–N, lưu cùng đối tượng cha) | Khởi tạo rỗng, thêm qua phương thức của lớp |

| **Phương thức** | **Mô tả** |
| --- | --- |
| Loan(String id, String readerId, LocalDate borrowDate, LocalDate dueDate, boolean extended, LoanStatus status) | Constructor; kiểm tra: Mã phiếu rỗng; Thiếu độc giả; Hạn trả phải sau ngày mượn |
| getId() : String | Trả mã (cài đặt Identifiable) |
| get…() / is…() | Getter cho mọi thuộc tính |
| set…(…) | Setter có kiểm tra (sai → IllegalArgumentException): setDueDate, setExtended, setStatus |
| notReturnedCount() : long | Số cuốn chưa trả trong phiếu |
| totalFine() : double | Tổng tiền phạt của phiếu |
| detail(String copyId) : LoanDetail | Tìm dòng chi tiết theo mã bản sao (null nếu không có) |

### LoanDetail – Dòng phiếu mượn

Một bản sao trong phiếu mượn. returnDate = null nghĩa là chưa trả. Cài đặt: Serializable.

| **Thuộc tính** | **Kiểu** | **Ý nghĩa** | **Kiểm tra (thông báo lỗi khi sai) / ghi chú** |
| --- | --- | --- | --- |
| copyId | String | Mã bản sao | final: gán một lần trong constructor (kiểm tra ở constructor) |
| returnDate | LocalDate | Ngày trả (null = chưa trả) | Không có setter; chỉ đổi qua phương thức nghiệp vụ của lớp |
| condition | ReturnCondition | Tình trạng khi trả | Không có setter; chỉ đổi qua phương thức nghiệp vụ của lớp |
| fine | double | Tiền phạt của cuốn này | Không có setter; chỉ đổi qua phương thức nghiệp vụ của lớp |

| **Phương thức** | **Mô tả** |
| --- | --- |
| LoanDetail(String copyId, LocalDate returnDate, ReturnCondition condition, double fine) | Constructor; kiểm tra: Thiếu mã bản sao; Tiền phạt không âm |
| get…() / is…() | Getter cho mọi thuộc tính |
| markReturned(LocalDate date, ReturnCondition condition, double fine) | Ghi nhận trả sách. |

### Reader – Độc giả

Cài đặt: Identifiable<String>, Serializable.

| **Thuộc tính** | **Kiểu** | **Ý nghĩa** | **Kiểm tra (thông báo lỗi khi sai) / ghi chú** |
| --- | --- | --- | --- |
| id | String | Mã độc giả | Mã độc giả phải dạng DG001 |
| name | String | Tên | Tên độc giả không được rỗng |
| phone | String | Số điện thoại | Số điện thoại phải gồm 10 số, bắt đầu bằng 0 |
| type | ReaderType | Loại độc giả | Chưa chọn loại độc giả |
| active | boolean | Thẻ còn hiệu lực | — |

| **Phương thức** | **Mô tả** |
| --- | --- |
| Reader(String id, String name, String phone, ReaderType type, boolean active) | Constructor đầy đủ tham số; gọi setter nên dùng chung phần kiểm tra |
| getId() : String | Trả mã (cài đặt Identifiable) |
| get…() / is…() | Getter cho mọi thuộc tính |
| set…(…) | Setter có kiểm tra (sai → IllegalArgumentException): setId, setName, setPhone, setType, setActive |
| setPhone(String phone) | Không bắt buộc; nếu có phải 10 số bắt đầu bằng 0. |
| toString() : String | Chuỗi một dòng để in danh sách |

## 9.3 Enum (package model) – Bước 2

| **Enum** | **Ý nghĩa** | **Hằng (nhãn, tham số)** | **Thuộc tính / phương thức thêm** |
| --- | --- | --- | --- |
| CopyStatus | Trạng thái bản sao | AVAILABLE ("Sẵn sàng"); BORROWED ("Đang mượn"); DAMAGED ("Hư hỏng"); LOST ("Mất") | — |
| LoanStatus | Trạng thái phiếu mượn | BORROWING ("Đang mượn"); OVERDUE ("Quá hạn"); RETURNED ("Đã trả") | — |
| ReaderType | Loại độc giả – quy tắc nghiệp vụ gắn liền từng loại (khác hằng số ở chỗ này). | STUDENT ("Sinh viên", 3, 14); LECTURER ("Giảng viên", 5, 30) | Thuộc tính: maxBooks, loanDays getMaxBooks() : int – Số sách được mượn cùng lúc tối đa getLoanDays() : int – Số ngày được mượn |
| ReturnCondition | Tình trạng sách khi trả. | NORMAL ("Bình thường"); DAMAGED ("Hư hỏng"); LOST ("Mất") | — |

## 9.4 Record tiêu chí tìm kiếm, kết quả và kiểu sắp xếp – Bước 3, 8

Project mẫu gom các record và enum sắp xếp vào lớp Criteria (lớp chỉ để chứa, constructor private). Record là bất biến, có sẵn constructor, getter dạng ten(), equals, toString.

| **Kiểu** | **Nằm trong** | **Thành phần** | **Mô tả** |
| --- | --- | --- | --- |
| BookCriteria | Criteria | String title, String author, String category, Integer year, boolean availableOnly, BookSort sort | Tiêu chí tìm kiếm; thành phần nào null (hoặc false) thì bỏ qua tiêu chí đó |
| BookView | Criteria | String id, String title, String author, String category, int year, int totalCopies, int available, int loanCount | Một dòng kết quả tìm sách: kèm số bản sao, số bản rảnh, số lượt mượn. |
| LoanCriteria | Criteria | String readerId, LoanStatus status, LocalDate from, LocalDate to | Tiêu chí tìm kiếm; thành phần nào null (hoặc false) thì bỏ qua tiêu chí đó |
| LoanSummary | Criteria | String id, String readerName, LocalDate borrowDate, LocalDate dueDate, LoanStatus status, int books, int notReturned, double fine | DTO kết quả thống kê / báo cáo |
| FinePolicy | Criteria | double perDay, double damagedRate, double lostRate | Chính sách phạt: trễ perDay đ/ngày; hỏng thêm damagedRate × giá; mất lostRate × giá. fine(LocalDate dueDate, LocalDate returnDate, ReturnCondition cond, double bookPrice) : double – Tính tiền phạt một cuốn: trễ hạn + hỏng / mất theo giá sách |
| BookLoanCount | Criteria | String bookId, String title, long loans | DTO kết quả thống kê / báo cáo |
| OverdueItem | Criteria | String loanId, String readerId, String readerName, LocalDate dueDate, long daysLate, long books | DTO kết quả thống kê / báo cáo |
| Page | model | List<T> items, int page, int size, long totalItems | Một trang kết quả. page đếm từ 1. totalPages() : int – Tổng số trang = ⌈totalItems / size⌉ of(List<T> all, int page, int size) : Page<T> – Cắt một danh sách đã lọc + sắp xếp thành trang (dùng cho bản file). |
| ReturnLine | LoanService | String copyId, String title, long daysLate, ReturnCondition condition, double fine | Kết quả trả 1 cuốn – dùng để in biên lai. |
| BookSort | Criteria | TITLE ("Tên sách"); AUTHOR ("Tác giả"); YEAR_DESC ("Năm XB mới nhất"); LOANS_DESC ("Lượt mượn nhiều nhất") | Kiểu sắp xếp: mỗi hằng có label (hiện menu), sql (đoạn ORDER BY cho bản JDBC), comparator (cho bản file) |

## 9.5 Ngoại lệ (package exception) – Bước 4

| **Lớp** | **Kế thừa** | **Dùng khi** | **Phương thức thêm** |
| --- | --- | --- | --- |
| BusinessException | Exception | Vi phạm quy tắc nghiệp vụ (hết hàng, trùng lịch, vượt sĩ số…). | Constructor BusinessException(String message) |
| DataAccessException | RuntimeException | Unchecked – bọc IOException / SQLException để tầng service, ui không phụ thuộc java.io / java.sql. | Constructor DataAccessException(String message, Throwable cause) |
| DuplicateIdException | Exception | Trùng mã khi thêm mới. | Constructor DuplicateIdException(String what, Object id) |
| NotFoundException | Exception | Không tìm thấy theo mã. | Constructor NotFoundException(String what, Object id) |

Exception checked (extends Exception) bắt buộc service khai báo throws, ConsoleUI bắt và in thông báo. DataAccessException là unchecked, bọc IOException / SQLException để tầng trên không phụ thuộc java.io / java.sql.

## 9.6 Repository (package repository) – Bước 4, 5, 6, 7

**Interface chung** Identifiable<ID>: getId() : ID.

**Interface chung** GenericRepository<T, ID> – CRUD dùng cho mọi thực thể:

| **Phương thức** | **Mô tả** |
| --- | --- |
| findAll() : List<T> | Lấy tất cả bản ghi |
| findById(ID id) : Optional<T> | Tìm theo mã; trả Optional rỗng nếu không có |
| existsById(ID id) : boolean | Mã đã tồn tại chưa |
| insert(T entity) | Thêm mới |
| update(T entity) : boolean | Cập nhật; trả false nếu mã không tồn tại |
| deleteById(ID id) : boolean | Xóa theo mã; trả false nếu mã không tồn tại |

**Interface riêng từng thực thể** (project mẫu gom trong lớp Repositories), kế thừa GenericRepository và thêm phương thức đặc thù:

| **Interface** | **Phương thức thêm** | **Ngoại lệ** | **Mô tả** |
| --- | --- | --- | --- |
| ReaderRepository GenericRepository<Reader, String> | — |  | Chỉ dùng CRUD chung |
| BookRepository GenericRepository<Book, String> | search(BookCriteria criteria, int page, int size) : Page<BookView> |  | Tìm đầu sách theo nhiều tiêu chí + sắp xếp + phân trang (trang đếm từ 1) |
|  | addCopies(String bookId, int n) : List<String> | NotFoundException | Thêm n bản sao AVAILABLE cùng lúc (batch). Trả về: mã các bản sao mới |
|  | findCopy(String copyId) : Optional<BookCopy> |  | Tìm bản sao theo mã (Optional) |
|  | findAllCopies() : List<BookCopy> |  | Mọi bản sao của mọi đầu sách |
| LoanRepository GenericRepository<Loan, String> | create(Loan loan) | BusinessException | Lưu phiếu + chuyển bản sao sang BORROWED – được hết hoặc không gì cả. |
|  | returnCopy(String loanId, String copyId, LocalDate date, ReturnCondition cond, double fine) | BusinessException, NotFoundException | Ghi nhận trả 1 bản sao + đổi trạng thái bản sao + đóng phiếu nếu đã trả đủ. |
|  | markOverdue(LocalDate today) : int |  | BORROWING có hạn trả < today → OVERDUE. Trả về: số phiếu bị đánh dấu |
|  | search(LoanCriteria criteria, int page, int size) : Page<LoanSummary> |  | Tìm phiếu mượn theo nhiều tiêu chí + sắp xếp + phân trang (trang đếm từ 1) |
|  | existsByReader(String readerId) : boolean |  | Có phiếu mượn nào tham chiếu tới độc giả này không (kiểm tra trước khi xóa) |
|  | existsByBook(String bookId) : boolean |  | Có phiếu mượn nào tham chiếu tới đầu sách này không (kiểm tra trước khi xóa) |
|  | nextId(LocalDate date) : String |  | Mã kế tiếp dạng PM20261025-001. |

**Hai bản cài đặt** – service chỉ biết interface nên đổi File ↔ SQL Server không phải sửa service:

| **Lớp** | **Bước** | **Vai trò** |
| --- | --- | --- |
| FileRepository<T, ID> | 5 | Cài đặt Giai đoạn 1 dùng chung: mỗi thực thể 1 file .dat, trong bộ nhớ là Map<ID, T>. File không tồn tại / rỗng → bắt đầu rỗng. File hỏng → đổi tên thành .bak, bắt đầu rỗng, ghi cảnh báo. Thuộc tính: file, Map<ID, T> data. Phương thức: CRUD của GenericRepository + save() ghi cả Map xuống file. |
| FileReaderRepository, FileBookRepository, FileLoanRepository | 5 | Lớp con của FileRepository, cài các phương thức riêng bằng Stream (lọc, sắp xếp, Page.of). |
| DBConnect | 6 | Kết nối SQL Server. Cấu hình đọc từ file db.properties ở thư mục gốc project (url, user, password); có thể ghi đè bằng -Ddb.url=.. -Ddb.user=.. -Ddb.password=... Phương thức: getConnection() : Connection. |
| JdbcRepository<T, ID> | 6 | Phần JDBC dùng chung cho bảng có khóa chính 1 cột: findAll, findById, existsById, deleteById. Lớp con chỉ viết mapRow, insert, update và các truy vấn riêng. Phương thức trừu tượng mapRow(ResultSet) : T; tiện ích query, count, execute, bind. |
| SqlFilter | 6 | Ghép điều kiện WHERE động cho tìm kiếm nhiều tiêu chí – giá trị luôn đi qua dấu ?, không nối chuỗi. Ví dụ: f.add("p.price >= ?", minPrice).add("p.name LIKE ?", "%" + kw + "%"); |
| JdbcReaderRepository, JdbcBookRepository, JdbcLoanRepository | 6, 7 | Lớp con của JdbcRepository, mỗi lớp một bảng; mọi câu SQL dùng PreparedStatement. |

Các phương thức JDBC quan trọng (Bước 6–7) trong project mẫu:

- JdbcBookRepository.search – LEFT JOIN bản sao + GROUP BY để có số bản / số bản rảnh; HAVING khi chỉ lấy sách còn bản rảnh.

- JdbcBookRepository.addCopies – Thêm n bản sao bằng batch.

- JdbcLoanRepository.create – TRANSACTION mượn sách: INSERT phiếu → INSERT chi tiết (batch) → UPDATE bản sao có điều kiện.

- JdbcLoanRepository.returnCopy – TRANSACTION trả sách: ghi chi tiết → đổi trạng thái bản sao → đóng phiếu nếu trả đủ.

- JdbcLoanRepository.markOverdue – UPDATE có điều kiện – chạy mỗi lần khởi động.

- JdbcLoanRepository.search – JOIN reader + loan_detail, lọc, phân trang.

## 9.7 Service (package service) – Bước 3, 4 trở đi

Service chứa nghiệp vụ: kiểm tra quy tắc (BR), gọi repository, ném exception. Mỗi service gắn @Author ghi người phụ trách (Bước 8).

### BookService – Sách, bản sao, độc giả

Đầu sách, bản sao, độc giả. Nhận qua constructor: BookRepository, ReaderRepository, LoanRepository.

| **Phương thức** | **Ngoại lệ** | **Mô tả** |
| --- | --- | --- |
| getBook(String id) : Book | NotFoundException | Tìm đầu sách theo mã; không có → NotFoundException |
| addBook(Book b, int copies) | DuplicateIdException, NotFoundException | Thêm đầu sách mới (kiểm tra hợp lệ); trùng mã → DuplicateIdException; mã tham chiếu không tồn tại → NotFoundException |
| updateBook(Book b) | NotFoundException | Sửa thông tin đầu sách; không có → NotFoundException |
| deleteBook(String id) | NotFoundException, BusinessException | Xóa đầu sách; đã có dữ liệu liên quan → BusinessException |
| addCopies(String bookId, int n) : List<String> | NotFoundException, BusinessException | Thêm n bản sao (1–50) cho một đầu sách; trả về danh sách mã bản sao mới |
| search(BookCriteria c, int page, int size) : Page<BookView> |  | Tìm bản ghi theo nhiều tiêu chí + sắp xếp + phân trang (trang đếm từ 1) |
| allReaders() : List<Reader> |  | Danh sách tất cả độc giả |
| getReader(String id) : Reader | NotFoundException | Tìm độc giả theo mã; không có → NotFoundException |
| addReader(Reader r) | DuplicateIdException | Thêm độc giả mới (kiểm tra hợp lệ); trùng mã → DuplicateIdException |
| updateReader(Reader r) | NotFoundException | Sửa thông tin độc giả; không có → NotFoundException |
| deleteReader(String id) | NotFoundException, BusinessException | Xóa độc giả; đã có dữ liệu liên quan → BusinessException |

### LoanService – Mượn – trả

Mượn, trả, gia hạn, đánh dấu quá hạn. Nhận qua constructor: LoanRepository, ReaderRepository, BookRepository.

| **Phương thức** | **Ngoại lệ** | **Mô tả** |
| --- | --- | --- |
| markOverdue() : int |  | Đánh dấu Quá hạn mọi phiếu đang mượn đã qua hạn trả; trả về số phiếu |
| borrowingCount(String readerId) : long |  | Số bản sao độc giả đang giữ (chưa trả). |
| borrow(String readerId, List<String> copyIds) : Loan | NotFoundException, BusinessException | Kiểm tra độc giả TRƯỚC khi mở transaction; repository chặn bản sao không sẵn sàng. |
| returnBooks(String loanId, List<String> copyIds, ReturnCondition condition, LocalDate date) : List<ReturnLine> | NotFoundException, BusinessException | Trả 1 hoặc nhiều cuốn trong phiếu (copyIds rỗng = trả cả phiếu, mọi cuốn tình trạng NORMAL). |
| extend(String loanId) : Loan | NotFoundException, BusinessException | Gia hạn 1 lần +7 ngày, chỉ khi chưa quá hạn. |
| getLoan(String id) : Loan | NotFoundException | Tìm phiếu mượn theo mã; không có → NotFoundException |
| search(LoanCriteria c, int page, int size) : Page<LoanSummary> |  | Tìm phiếu mượn theo nhiều tiêu chí + sắp xếp + phân trang (trang đếm từ 1) |
| receipt(String loanId, List<ReturnLine> lines) : String | NotFoundException | Biên lai trả sách – Text Block. |

### MigrationService – Chuyển dữ liệu file → CSDL

Chuyển dữ liệu Giai đoạn 1 (file) sang Giai đoạn 2 (SQL Server). Chuyển theo thứ tự khóa ngoại (bảng cha trước), mã đã có trong DB thì bỏ qua.

| **Phương thức** | **Ngoại lệ** | **Mô tả** |
| --- | --- | --- |
| copy(String name, GenericRepository<T, ID> from, GenericRepository<T, ID> to) : int |  | Gọi lần lượt cho từng cặp repository: file → jdbc. Trả về số dòng đã chuyển. |
| report() : List<String> |  | Các dòng tổng kết sau khi chuyển: mỗi bảng chuyển bao nhiêu dòng, lỗi gì |

### ReportService – Thống kê, báo cáo

7 thống kê bằng Stream + xuất danh sách quá hạn ra HTML và CSV. Nhận qua constructor: LoanRepository, BookRepository, ReaderRepository.

| **Phương thức** | **Ngoại lệ** | **Mô tả** |
| --- | --- | --- |
| topBooks(int n) : List<BookLoanCount> |  | 1. Top n đầu sách được mượn nhiều nhất: flatMap chi tiết → copyId → bookId. |
| overdue() : List<OverdueItem> |  | 2. Phiếu quá hạn + số ngày trễ. |
| fineByMonth() : Map<YearMonth, Double> |  | 3. Tổng tiền phạt theo tháng. |
| copiesByStatus() : Map<CopyStatus, Long> |  | 4. Số bản sao theo trạng thái – EnumMap giữ đúng thứ tự khai báo. |
| loansByCategory() : List<Map.Entry<String, Long>> |  | 5. Lượt mượn theo thể loại (giảm dần). |
| averageLoansByType() : Map<ReaderType, Double> |  | 6. Số lượt mượn trung bình mỗi độc giả, theo loại độc giả. |
| needMoreCopies() : List<String> |  | 7. Đầu sách cần mua thêm: hết bản rảnh nhưng có ≥ 3 lượt mượn trong tháng này. |
| exportOverdue(String htmlPath, String csvPath) : List<Path> | IOException | Xuất danh sách quá hạn: HTML (Text Block) + CSV. |

SampleData – Dữ liệu mẫu cho chế độ File – cùng kịch bản với sql/02_sample_data.sql. Ngày tính tương đối theo hôm nay → lúc nào chạy cũng có phiếu đang mượn, quá hạn, đã trả. Phương thức seed(…) (static) chỉ chạy khi file dữ liệu còn rỗng.

## 9.8 Giao diện, khởi động, annotation – Bước 1 trở đi

| **Lớp** | **Thuộc tính** | **Phương thức** | **Ghi chú** |
| --- | --- | --- | --- |
| Main | — | main(String[] args) | Chọn File (Giai đoạn 1) hoặc SQL Server (Giai đoạn 2); tham số "file" / "db" để bỏ qua câu hỏi. |
| ConsoleUI | bookService, loanService, reports, migration | start(); các menu con private: bookMenu(), readerMenu(), loanMenu(), reportMenu(); pager(…) hiện từng trang; printAuthors() đọc @Author bằng Reflection | Menu console – chỉ gọi service. Mỗi thao tác chạy trong run(Action) để bắt mọi exception. |
| Input | DATE (dd/MM/yyyy), Scanner dùng chung | line(…) – Đọc một dòng (đã trim, có thể rỗng) required(…) – Bắt buộc nhập; rỗng thì hỏi lại optional(…) – Enter để bỏ trống → null. integer(…) – Số nguyên trong khoảng [min, max]; sai thì hỏi lại optionalInt(…) – Enter để bỏ trống → null. decimal(…) – Số thực ≥ min; sai thì hỏi lại optionalDecimal(…) – Enter để bỏ trống → null. date(…) – Ngày dạng dd/MM/yyyy; sai thì hỏi lại optionalDate(…) – Enter để bỏ trống → null. choose(…) – Chọn 1 hằng enum theo số thứ tự. Enum nên có toString() trả nhãn tiếng Việt. chooseOptional(…) – Như choose nhưng cho phép 0 = bỏ qua → null. confirm(…) – Hỏi có / không (y/n) | Nhập liệu an toàn: luôn đọc cả dòng, nhập sai thì hỏi lại – chương trình không bao giờ crash vì nhập. Mọi phương thức là static. |
| @Author | name(), task() | — | Annotation @Retention(RUNTIME), @Target(TYPE) gắn lên service; đọc lúc chạy bằng Reflection. |

MOB1024 – Đặc tả yêu cầu dự án nhóm   ·   Trang /
