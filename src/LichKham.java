import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/*
 LichKham: thêm trạng thái (0=chưa khám, 1=đã khám)
*/
public class LichKham {
    private String id;
    private String idBenhNhan;
    private String idBacSi;
    private LocalDateTime ngayGio;
    private String ghiChu;
    private int trangThai;          // 0: chưa khám, 1: đã khám

    public static final DateTimeFormatter DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public LichKham(String id, String idBenhNhan, String idBacSi, String ngayGioStr, String ghiChu, int trangThai) {
        this.id = id;
        this.idBenhNhan = idBenhNhan;
        this.idBacSi = idBacSi;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
        try {
            this.ngayGio = (ngayGioStr == null || ngayGioStr.isBlank()) ? null
                    : LocalDateTime.parse(ngayGioStr.trim(), DATETIME_FORMAT);
        } catch (DateTimeParseException ex) {
            this.ngayGio = null;
        }
    }

    public LichKham(String id, String idBenhNhan, String idBacSi, LocalDateTime ngayGio, String ghiChu, int trangThai) {
        this.id = id;
        this.idBenhNhan = idBenhNhan;
        this.idBacSi = idBacSi;
        this.ngayGio = ngayGio;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public String getId() { return id; }
    public String getIdBenhNhan() { return idBenhNhan; }
    public String getIdBacSi() { return idBacSi; }
    public String getNgayGio() {
        return (ngayGio == null) ? "" : ngayGio.format(DATETIME_FORMAT);
    }
    public LocalDateTime getNgayGioDate() { return ngayGio; }
    public String getGhiChu() { return ghiChu; }
    public int getTrangThai() { return trangThai; }
    public String getTrangThaiStr() {
        return (trangThai == 0) ? "Chua kham" : "Da kham";
    }

    @Override
    public String toString() {
        return id + " | BN:" + idBenhNhan + " | BS:" + idBacSi + " | " + getNgayGio() + 
               " | " + getTrangThaiStr() + " | " + ghiChu;
    }
}
