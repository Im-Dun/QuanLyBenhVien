import java.util.ArrayList;

/*
  QuanLyLichKham: load/save số dùng định dạng dd-MM-yyyy HH:mm,
  kiểm tra xung đột bằng LocalDateTime (chính xác hơn so sánh chuỗi).
*/
public class QuanLyLichKham implements IQuanLy {
    private ArrayList<LichKham> ds = new ArrayList<>();
    private final String file = "data/lichkham.txt";

    public void load() {
        ds = DuLieuTrongBoNho.docFile(file, line -> {
            // format: id,idBenhNhan,idBacSi,ngaygio(dd-MM-yyyy HH:mm),ghichu,trangThai
            String[] p = line.split(",", 6);
            int tt = (p.length>5) ? Integer.parseInt(p[5].trim()) : 0;
            return new LichKham(
                p[0].trim(),
                p.length>1 ? p[1].trim() : "",
                p.length>2 ? p[2].trim() : "",
                p.length>3 ? p[3].trim() : "",
                p.length>4 ? p[4].trim() : "",
                tt
            );
        });
    }

    public void save() {
        DuLieuTrongBoNho.ghiFile(file, ds.stream().map(l ->
            String.join(",", l.getId(), l.getIdBenhNhan(), l.getIdBacSi(), l.getNgayGio(), l.getGhiChu(), String.valueOf(l.getTrangThai()))
        ).toList());
    }

    public void them(LichKham lk) { ds.add(lk); }

    // Thêm có kiểm tra xung đột: trả về false nếu có lịch trùng bác sĩ + cùng thời gian
    public boolean themCoKiemTra(LichKham lk) {
        if (lk.getNgayGioDate() == null) return false; // ngày giờ không hợp lệ
        if (coXungDot(lk.getIdBacSi(), lk.getNgayGioDate())) return false;
        ds.add(lk);
        return true;
    }

    // Sửa theo ID (thay đổi tương) và kiểm tra xung đột ngoài trừ chính nó
    public boolean sua(LichKham lk) {
        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getId().equals(lk.getId())) {
                if (lk.getNgayGioDate() == null) return false;
                for (LichKham other : ds) {
                    if (!other.getId().equals(lk.getId())
                        && other.getIdBacSi().equals(lk.getIdBacSi())
                        && other.getNgayGioDate() != null
                        && other.getNgayGioDate().equals(lk.getNgayGioDate())) {
                        return false;
                    }
                }
                ds.set(i, lk);
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String id) {
        return ds.removeIf(l -> l.getId().equals(id));
    }

    public LichKham timTheoId(String id) {
        return ds.stream().filter(l -> l.getId().equals(id)).findFirst().orElse(null);
    }

    public ArrayList<LichKham> timTheoBenhNhan(String idBenhNhan) {
        ArrayList<LichKham> res = new ArrayList<>();
        for (LichKham l : ds) if (l.getIdBenhNhan().equals(idBenhNhan)) res.add(l);
        return res;
    }

    // Kiểm tra xung đột bằng LocalDateTime
    public boolean coXungDot(String idBacSi, java.time.LocalDateTime ngayGio) {
        return ds.stream().anyMatch(l ->
            l.getIdBacSi().equals(idBacSi)
            && l.getNgayGioDate() != null
            && l.getNgayGioDate().equals(ngayGio)
        );
    }

    @Override
    public void hienThiDanhSach() {
        System.out.println("=== Danh sach lich kham ===");
        if (ds.isEmpty()) { System.out.println("(Trong)"); return; }
        ds.forEach(System.out::println);
    }

    public ArrayList<LichKham> getDanhSach() { return ds; }
}
