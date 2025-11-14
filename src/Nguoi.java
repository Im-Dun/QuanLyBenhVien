import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Nguoi {
    protected String id;
    protected String ten;
    protected LocalDate ngaySinh;
    protected String soDienThoai;

    // Định dạng ngày dùng chung
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Constructor nhận chuỗi ngày (định dạng dd-MM-yyyy)
    public Nguoi(String id, String ten, String ngaySinhStr, String soDienThoai) {
        this.id = id;
        this.ten = ten;
        this.soDienThoai = soDienThoai;
        try {
            this.ngaySinh = (ngaySinhStr == null || ngaySinhStr.isBlank()) ? null
                    : LocalDate.parse(ngaySinhStr.trim(), DATE_FORMAT);
        } catch (DateTimeParseException ex) {
            // Nếu không parse được, để null (các lớp quản lý có thể thông báo)
            this.ngaySinh = null;
        }
    }

    // Trả về chuỗi ngày theo định dạng dd-MM-yyyy (hoặc rỗng nếu null)
    public String getNgaySinh() {
        return (ngaySinh == null) ? "" : ngaySinh.format(DATE_FORMAT);
    }

    // Trả về đối tượng LocalDate khi cần thao tác
    public LocalDate getNgaySinhDate() {
        return ngaySinh;
    }

    public String getId() { return id; }
    public String getTen() { return ten; }
    public String getSoDienThoai() { return soDienThoai; }

    @Override
    public String toString() {
        return id + " | " + ten + " | " + getNgaySinh() + " | " + soDienThoai;
    }
}
