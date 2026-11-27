package library.model;

import java.io.Serializable;
import java.util.regex.Pattern;

// độc giả: 
public class Reader implements Identifiable<String>, Serializable {
    private static final long serialVersionUID = 1L;

    // mã độc giả (gồm DG kèm 3 số) - SĐT (10 số, bắt đầu từ 0)
    private static final Pattern ID_PATTERN = Pattern.compile("DG\\d{3}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("0\\d{9}");

    private String id;
    private String name;
    private String phone;
    private ReaderType type;
    private boolean active;

    public Reader(String id, String name, String phone, ReaderType type, boolean active) {
        setId(id);
        setName(name);
        setPhone(phone);
        setType(type);
        setActive(active);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || !ID_PATTERN.matcher(id.trim()).matches()) {
            throw new IllegalArgumentException("Mã độc giả phải có dạng DG001!");
        }
        this.id = id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên độc giả không được để trống!");
        }
        this.name = name.trim();
    }

    public String getPhone() {
        return phone;
    }

    // không bắt buộc (nếu có thì phải đủ 10 số)
    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            this.phone = null;
            return;
        }
        if (!PHONE_PATTERN.matcher(phone.trim()).matches()) {
            throw new IllegalArgumentException("Số điện thoại phải gồm 10 số, bắt đầu bằng 0!");
        }
        this.phone = phone.trim();
    }

    public ReaderType getType() {
        return type;
    }

    public void setType(ReaderType type) {
        if (type == null) {
            throw new IllegalArgumentException("Chưa chọn loại độc giả!");
        }
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + (phone == null ? "(chưa có SĐT)" : phone)
                + " | " + type.getLabel() + " | " + (active ? "Còn hiệu lực" : "Đã khóa");
    }
}
