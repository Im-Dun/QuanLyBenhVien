import java.util.ArrayList;

/*
 QuanLyHoaDon: quản lý hóa đơn
 - CRUD hóa đơn
 - Tìm theo bệnh nhân, bác sĩ
 - Load/save từ file
*/
public class QuanLyHoaDon implements IQuanLy {
    private ArrayList<HoaDon> ds = new ArrayList<>();
    private final String file = "data/hoadon.txt";

    public void load() {
        ds = DuLieuTrongBoNho.docFile(file, line -> {
            // format: id,idBenhNhan,idBacSi,idLichKham,ngayLapHD,chanDoan,dichVu
            String[] p = line.split(",", 7);
            HoaDon hd = new HoaDon(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(),
                    p.length>4 ? p[4].trim() : "", p.length>5 ? p[5].trim() : "");
            if (p.length>6) {
                String[] items = p[6].split("\\|");
                for (String it : items) {
                    String[] kv = it.split(":", 2);
                    if (kv.length==2) {
                        try {
                            hd.themDichVu(kv[0].trim(), Double.parseDouble(kv[1].trim()));
                        } catch (Exception e) {
                            // b? qua n?u parse gi� l?i
                        }
                    }
                }
            }
            return hd;
        });
    }

    public void save() {
        DuLieuTrongBoNho.ghiFile(file, ds.stream().map(hd -> {
            String dv = String.join("|", hd.getDichVu().entrySet().stream()
                    .map(e -> e.getKey()+":"+e.getValue()).toList());
            return String.join(",", hd.getId(), hd.getIdBenhNhan(), hd.getIdBacSi(),
                    hd.getIdLichKham(), hd.getNgayLapHD(), hd.getChanDoan(), dv);
        }).toList());
    }

    public void them(HoaDon hd) { ds.add(hd); }

    public boolean sua(HoaDon hd) {
        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getId().equals(hd.getId())) {
                ds.set(i, hd);
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String id) {
        return ds.removeIf(h -> h.getId().equals(id));
    }

    public HoaDon timTheoId(String id) {
        return ds.stream().filter(h -> h.getId().equals(id)).findFirst().orElse(null);
    }

    public ArrayList<HoaDon> timTheoBeNhan(String idBenhNhan) {
        ArrayList<HoaDon> res = new ArrayList<>();
        for (HoaDon h : ds) if (h.getIdBenhNhan().equals(idBenhNhan)) res.add(h);
        return res;
    }

    public ArrayList<HoaDon> timTheoBacSi(String idBacSi) {
        ArrayList<HoaDon> res = new ArrayList<>();
        for (HoaDon h : ds) if (h.getIdBacSi().equals(idBacSi)) res.add(h);
        return res;
    }

    @Override
    public void hienThiDanhSach() {
        System.out.println("=== Danh sach hoa don ===");
        if (ds.isEmpty()) { System.out.println("(Trong)"); return; }
        ds.forEach(System.out::println);
    }
}
