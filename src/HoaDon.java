import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/*
 HoaDon: lưu thông tin hóa đơn khám chữa bệnh
 - Liên kết với bệnh nhân, bác sĩ, lịch khám
 - Lưu danh sách dịch vụ (tên -> giá)
 - Tính tổng tiền
*/
public class HoaDon {
    private String id;
    private String idBenhNhan;
    private String idBacSi;
    private String idLichKham;
    private LocalDate ngayLapHD;
    private Map<String, Double> dichVu;      // tên dịch vụ -> giá
    private String chanDoan;
    private double tongTien;

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public HoaDon(String id, String idBenhNhan, String idBacSi, String idLichKham, String ngayLapStr, String chanDoan) {
        this.id = id;
        this.idBenhNhan = idBenhNhan;
        this.idBacSi = idBacSi;
        this.idLichKham = idLichKham;
        this.chanDoan = chanDoan;
        this.dichVu = new HashMap<>();
        this.tongTien = 0;
        try {
            this.ngayLapHD = (ngayLapStr == null || ngayLapStr.isBlank()) ? LocalDate.now()
                    : LocalDate.parse(ngayLapStr.trim(), DATE_FORMAT);
        } catch (Exception ex) {
            this.ngayLapHD = LocalDate.now();
        }
    }

    // Thêm dịch vụ (tên + giá)
    public void themDichVu(String tenDichVu, double gia) {
        dichVu.put(tenDichVu, gia);
        tinhTongTien();
    }

    // Tính tổng tiền (generic logic)
    private void tinhTongTien() {
        tongTien = dichVu.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public String getId() { return id; }
    public String getIdBenhNhan() { return idBenhNhan; }
    public String getIdBacSi() { return idBacSi; }
    public String getIdLichKham() { return idLichKham; }
    public String getNgayLapHD() { return ngayLapHD.format(DATE_FORMAT); }
    public String getChanDoan() { return chanDoan; }
    public Map<String, Double> getDichVu() { return dichVu; }
    public double getTongTien() { return tongTien; }

    @Override
    public String toString() {
        return "HoaDon{" + "id='" + id + "', BN='" + idBenhNhan + "', BS='" + idBacSi +
               "', Ngay='" + getNgayLapHD() + "', ChanDoan='" + chanDoan + "', TongTien=" + tongTien + "}";
    }
}
