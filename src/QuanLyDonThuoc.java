import java.util.ArrayList;
import java.util.HashMap;

/*
  QuanLyDonThuoc: quản lý đơn thuốc
  - Lưu trữ theo HashMap idBenhNhan -> danh sách đơn
  - Thêm/sửa/xóa, lấy danh sách theo bệnh nhân
*/
public class QuanLyDonThuoc implements IQuanLy {
    private HashMap<String, ArrayList<DonThuoc>> ds = new HashMap<>();
    private final String file = "data/donthuoc.txt";

    public void load() {
        // format per line: id, idBenhNhan, tenThuoc1:lieu1|tenThuoc2:lieu2
        ArrayList<DonThuoc> raw = DuLieuTrongBoNho.docFile(file, line -> {
            String[] p = line.split(",", 3);
            DonThuoc d = new DonThuoc(p[0].trim(), p[1].trim());
            if (p.length > 2) {
                String[] items = p[2].split("\\|");
                for (String it : items) {
                    String[] kv = it.split(":", 2);
                    if (kv.length==2) d.themThuoc(kv[0].trim(), kv[1].trim());
                }
            }
            return d;
        });
        ds.clear();
        for (DonThuoc d : raw) {
            ds.computeIfAbsent(d.getIdBenhNhan(), k -> new ArrayList<>()).add(d);
        }
    }

    public void save() {
        ArrayList<String> lines = new ArrayList<>();
        for (var arr : ds.values()) {
            for (var d : arr) {
                String meds = String.join("|", d.getThuoc().entrySet().stream()
                    .map(e -> e.getKey()+":"+e.getValue()).toList());
                lines.add(String.join(",", d.getId(), d.getIdBenhNhan(), meds));
            }
        }
        DuLieuTrongBoNho.ghiFile(file, lines);
    }

    public void them(DonThuoc d) {
        ds.computeIfAbsent(d.getIdBenhNhan(), k -> new ArrayList<>()).add(d);
    }

    // Sửa đơn thuốc theo ID (tìm trong tất cả danh sách)
    public boolean sua(DonThuoc d) {
        ArrayList<DonThuoc> arr = ds.get(d.getIdBenhNhan());
        if (arr == null) return false;
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getId().equals(d.getId())) {
                arr.set(i, d);
                return true;
            }
        }
        return false;
    }

    // Xóa đơn theo ID (và trả về true nếu xóa)
    public boolean xoa(String idDon) {
        for (String key : ds.keySet()) {
            ArrayList<DonThuoc> arr = ds.get(key);
            boolean removed = arr.removeIf(d -> d.getId().equals(idDon));
            if (removed) {
                if (arr.isEmpty()) ds.remove(key);
                return true;
            }
        }
        return false;
    }

    // Lấy danh sách đơn theo ID bệnh nhân
    public ArrayList<DonThuoc> layTheoBenhNhan(String idBenhNhan) {
        return ds.getOrDefault(idBenhNhan, new ArrayList<>());
    }

    @Override
    public void hienThiDanhSach() {
        System.out.println("=== Danh sach don thuoc ===");
        if (ds.isEmpty()) {
            System.out.println("(Trong)");
            return;
        }
        ds.forEach((bn, list) -> {
            System.out.println("BN: " + bn);
            list.forEach(System.out::println);
        });
    }
}
