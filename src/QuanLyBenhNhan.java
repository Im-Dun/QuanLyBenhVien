import java.util.ArrayList;

/*
  QuanLyBenhNhan: quản lý danh sách bệnh nhân
  - load/save kỳ vọng ngày sinh lưu trong file theo dd-MM-yyyy
  - CRUD: them, sua, xoa, timTheoId
  - Có validate và xử lý ngoại lệ
*/
public class QuanLyBenhNhan implements IQuanLy {
    private ArrayList<BenhNhan> ds = new ArrayList<>();
    private final String file = "data/benhnhan.txt";

    /**
     * Load dữ liệu từ file với định dạng: id,ten,ngaysinh(dd-MM-yyyy),sdt,sothe,tuoi,gioitinh,diachi
     * Xử lý lỗi parse từng dòng, bỏ qua dòng lỗi
     */
    public void load() {
        ds = DuLieuTrongBoNho.docFile(file, line -> {
            try {
                String[] p = line.split(",");
                // Ki?m tra s? l�?ng tr�?ng t?i thi?u
                if (p.length < 5) {
                    System.err.println("Warning: Thiếu trường trong dòng: " + line);
                    return null;
                }
                // Tạo đối tượng BenhNhan với đủ 8 tham số (bao gồm tuoi, gioitinh, diachi)
                String id = p[0].trim();
                String ten = p[1].trim();
                String ngaySinh = p[2].trim();
                String sdt = p[3].trim();
                String soTheBHYT = p[4].trim();
                int tuoi = (p.length > 5) ? Integer.parseInt(p[5].trim()) : 0;
                String gioiTinh = (p.length > 6) ? p[6].trim() : "";
                String diaChi = (p.length > 7) ? p[7].trim() : "";
                
                return new BenhNhan(id, ten, ngaySinh, sdt, soTheBHYT, tuoi, gioiTinh, diaChi);
            } catch (NumberFormatException ex) {
                System.err.println("Loi parse tuoi trong dong: " + line + " - " + ex.getMessage());
                return null;
            } catch (Exception ex) {
                System.err.println("Loi parse dong: " + line + " - " + ex.getMessage());
                return null;
            }
        });
        // Loại bỏ các phần tử null nếu có lỗi parse
        ds.removeIf(bn -> bn == null);
    }

    /**
     * Lưu dữ liệu bệnh nhân vào file theo định dạng CSV
     */
    public void save() {
        DuLieuTrongBoNho.ghiFile(file, ds.stream().map(b ->
            String.join(",", b.getId(), b.getTen(), b.getNgaySinh(), b.getSoDienThoai(), 
                        b.getSoTheBHYT(), String.valueOf(b.getTuoi()), b.getGioiTinh(), b.getDiaChi())
        ).toList());
    }

    /**
     * Thêm bệnh nhân mới (kiểm tra null và không trùng ID)
     */
    public void them(BenhNhan bn) {
        if (bn != null && timTheoId(bn.getId()) == null) {
            ds.add(bn);
        } else if (bn == null) {
            System.err.println("Benh nhan null khong the them.");
        } else {
            System.err.println("ID " + bn.getId() + " da ton tai.");
        }
    }

    /**
     * Tìm bệnh nhân theo ID (an toàn với null)
     */
    public BenhNhan timTheoId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return ds.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Sửa thông tin bệnh nhân theo ID
     */
    public boolean sua(BenhNhan bn) {
        if (bn == null) {
            System.err.println("Benh nhan null khong the sua.");
            return false;
        }
        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getId().equals(bn.getId())) {
                ds.set(i, bn);
                return true;
            }
        }
        return false;
    }

    /**
     * Xóa bệnh nhân theo ID (an toàn với null)
     */
    public boolean xoa(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.err.println("ID khong hop le.");
            return false;
        }
        return ds.removeIf(b -> b.getId().equals(id));
    }

    /**
     * Hiển thị danh sách bệnh nhân (thực hiện interface IQuanLy)
     */
    @Override
    public void hienThiDanhSach() {
        System.out.println("=== Danh sach benh nhan ===");
        if (ds.isEmpty()) {
            System.out.println("(Trong)");
            return;
        }
        ds.forEach(System.out::println);
    }

    /**
     * Trả về danh sách bệnh nhân
     */
    public ArrayList<BenhNhan> getDanhSach() {
        return ds;
    }
}