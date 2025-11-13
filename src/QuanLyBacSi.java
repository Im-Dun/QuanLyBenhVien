import java.util.ArrayList;

/*
  QuanLyBacSi: đọc/ghi ngày sinh theo dd-MM-yyyy tương tự QuanLyBenhNhan
*/
public class QuanLyBacSi implements IQuanLy {
    private ArrayList<BacSi> ds = new ArrayList<>();
    private final String file = "data/bacsi.txt";

    public void load() {
        ds = DuLieuTrongBoNho.docFile(file, line -> {
            // format: id,ten,ngaysinh(dd-MM-yyyy),sdt,chuyenkhoa
            String[] p = line.split(",");
            return new BacSi(
                p[0].trim(),
                p[1].trim(),
                p.length>2 ? p[2].trim() : "",
                p.length>3 ? p[3].trim() : "",
                p.length>4 ? p[4].trim() : ""
            );
        });
    }

    public void save() {
        DuLieuTrongBoNho.ghiFile(file, ds.stream().map(b ->
            String.join(",", b.getId(), b.getTen(), b.getNgaySinh(), b.getSoDienThoai(), b.getChuyenKhoa())
        ).toList());
    }

    public void them(BacSi bs) { ds.add(bs); }

    public boolean sua(BacSi bs) {
        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getId().equals(bs.getId())) {
                ds.set(i, bs);
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String id) {
        return ds.removeIf(b -> b.getId().equals(id));
    }

    public BacSi timTheoId(String id) {
        return ds.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void hienThiDanhSach() {
        System.out.println("=== Danh sach bac si ===");
        if (ds.isEmpty()) { System.out.println("(Trong)"); return; }
        ds.forEach(System.out::println);
    }

    public ArrayList<BacSi> getDanhSach() { return ds; }
}
