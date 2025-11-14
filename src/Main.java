import java.util.Scanner;

/*
 Main.java - Điểm vào chương trình quản lý bệnh viện
 - Menu chính để điều hướng các submenu (Bệnh nhân, Bác sĩ, Lịch khám, Đơn thuốc, Hóa đơn)
 - Sử dụng đa hình qua IQuanLy.hienThiDanhSach()
 - Xử lý ngoại lệ trong load/save thông qua DuLieuTrongBoNho
*/
public class Main {
    public static void main(String[] args) {
        // Khởi tạo các bộ quản lý (mỗi bộ quản lý 1 loại dữ liệu)
        QuanLyBenhNhan qbn = new QuanLyBenhNhan();
        QuanLyBacSi qbs = new QuanLyBacSi();
        QuanLyLichKham qlk = new QuanLyLichKham();
        QuanLyDonThuoc qdt = new QuanLyDonThuoc();
        QuanLyHoaDon qhd = new QuanLyHoaDon();

        // Load dữ liệu từ thư mục data/ (có xử lý try/catch trong DuLieuTrongBoNho)
        qbn.load();
        qbs.load();
        qlk.load();
        qdt.load();
        qhd.load();

        // Hiển thị trạng thái ban đầu của các danh sách
        // qbn.hienThiDanhSach();
        // qbs.hienThiDanhSach();
        // qlk.hienThiDanhSach();
        // qdt.hienThiDanhSach();
        // qhd.hienThiDanhSach();

        // Bắt đầu menu tương tác
        runMainMenu(qbn, qbs, qlk, qdt, qhd);
    }

    /**
     * Menu chính: điều hướng đến các submenu quản lý
     */
    private static void runMainMenu(QuanLyBenhNhan qbn, QuanLyBacSi qbs, QuanLyLichKham qlk, QuanLyDonThuoc qdt, QuanLyHoaDon qhd) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("=== QUAN LY BENH VIEN - MENU CHINH ===");
            System.out.println("1. Quan ly Benh Nhan");
            System.out.println("2. Quan ly Bac Si");
            System.out.println("3. Quan ly Lich Kham");
            System.out.println("4. Quan ly Don Thuoc");
            System.out.println("5. Quan ly Hoa Don");
            System.out.println("6. Hien thi tat ca");
            System.out.println("7. Luu tat ca thay doi");
            System.out.println("0. Thoat");
            System.out.print("Lua chon: ");
            String c = sc.nextLine().trim();
            switch (c) {
                case "1" -> menuBenhNhan(qbn, sc);
                case "2" -> menuBacSi(qbs, sc);
                case "3" -> menuLichKham(qlk, qbn, qbs, sc);
                case "4" -> menuDonThuoc(qdt, qbn, sc);
                case "5" -> menuHoaDon(qhd, qbn, qbs, qlk, sc);
                case "6" -> {
                    qbn.hienThiDanhSach();
                    qbs.hienThiDanhSach();
                    qlk.hienThiDanhSach();
                    qdt.hienThiDanhSach();
                    qhd.hienThiDanhSach();
                }
                case "7" -> {
                    qbn.save();
                    qbs.save();
                    qlk.save();
                    qdt.save();
                    qhd.save();
                    System.out.println("Da luu tat ca thay doi.");
                }
                case "0" -> { running = false; System.out.println("Thoat chuong trinh."); }
                default -> System.out.println("Lua chon khong hop le.");
            }
        }
        sc.close();
    }

    //SUBMENU BENH NHAN
    private static void menuBenhNhan(QuanLyBenhNhan qbn, Scanner sc) {
        System.out.println("\n--- BENH NHAN ---");
        System.out.println("1. Xem danh sach");
        System.out.println("2. Them");
        System.out.println("3. Sua");
        System.out.println("4. Xoa");
        System.out.println("0. Tro ve");
        System.out.print("Lua chon: ");
        String c = sc.nextLine().trim();
        switch (c) {
            case "1" -> qbn.hienThiDanhSach();
            case "2" -> {
                // Thêm bệnh nhân mới: kiểm tra trùng ID trước khi thêm
                System.out.print("ID: "); String id = sc.nextLine().trim();
                if (qbn.timTheoId(id) != null) { System.out.println("ID da ton tai."); break; }
                System.out.print("Ten: "); String ten = sc.nextLine().trim();
                System.out.print("Ngay sinh (dd-MM-yyyy): "); String ns = sc.nextLine().trim();
                System.out.print("SDT: "); String sdt = sc.nextLine().trim();
                System.out.print("So BHYT: "); String bhyt = sc.nextLine().trim();
                System.out.print("Tuoi: "); int tuoi = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Gioi tinh (Nam/Nu): "); String gt = sc.nextLine().trim();
                System.out.print("Dia chi: "); String dc = sc.nextLine().trim();
                qbn.them(new BenhNhan(id, ten, ns, sdt, bhyt, tuoi, gt, dc));
                System.out.println("Da them benh nhan.");
            }
            case "3" -> {
                // Sửa thông tin bệnh nhân: cho phép giữ nguyên trường bằng Enter
                System.out.print("ID can sua: "); String id = sc.nextLine().trim();
                BenhNhan f = qbn.timTheoId(id);
                if (f==null) { System.out.println("Khong tim thay."); break; }
                System.out.println("Thong tin hien tai: " + f);
                System.out.print("Ten moi (Enter de giu): "); String ten = sc.nextLine().trim(); if (ten.isEmpty()) ten=f.getTen();
                System.out.print("NS moi (Enter de giu): "); String ns = sc.nextLine().trim(); if (ns.isEmpty()) ns=f.getNgaySinh();
                System.out.print("SDT moi (Enter de giu): "); String sdt = sc.nextLine().trim(); if (sdt.isEmpty()) sdt=f.getSoDienThoai();
                System.out.print("BHYT moi (Enter de giu): "); String bhyt = sc.nextLine().trim(); if (bhyt.isEmpty()) bhyt=f.getSoTheBHYT();
                System.out.print("Tuoi moi (Enter de giu): "); String tuoiStr = sc.nextLine().trim(); int tuoi = tuoiStr.isEmpty() ? f.getTuoi() : Integer.parseInt(tuoiStr);
                System.out.print("Gioi tinh moi (Enter de giu): "); String gt = sc.nextLine().trim(); if (gt.isEmpty()) gt=f.getGioiTinh();
                System.out.print("Dia chi moi (Enter de giu): "); String dc = sc.nextLine().trim(); if (dc.isEmpty()) dc=f.getDiaChi();
                boolean ok = qbn.sua(new BenhNhan(id,ten,ns,sdt,bhyt,tuoi,gt,dc));
                System.out.println(ok ? "Da cap nhat." : "Cap nhat that bai.");
            }
            case "4" -> {
                // Xóa bệnh nhân theo ID
                System.out.print("ID can xoa: "); String id = sc.nextLine().trim();
                boolean ok = qbn.xoa(id);
                System.out.println(ok ? "Da xoa." : "Xoa that bai.");
            }
            default -> {}
        }
    }

    //SUBMENU BAC SI
    private static void menuBacSi(QuanLyBacSi qbs, Scanner sc) {
        System.out.println("\n--- BAC SI ---");
        System.out.println("1. Xem danh sach");
        System.out.println("2. Them");
        System.out.println("3. Sua");
        System.out.println("4. Xoa");
        System.out.println("0. Tro ve");
        System.out.print("Lua chon: ");
        String c = sc.nextLine().trim();
        switch (c) {
            case "1" -> qbs.hienThiDanhSach();
            case "2" -> {
                // Thêm bác sĩ mới
                System.out.print("ID: "); String id = sc.nextLine().trim();
                if (qbs.timTheoId(id) != null) { System.out.println("ID da ton tai."); break; }
                System.out.print("Ten: "); String ten = sc.nextLine().trim();
                System.out.print("Ngay sinh (dd-MM-yyyy): "); String ns = sc.nextLine().trim();
                System.out.print("SDT: "); String sdt = sc.nextLine().trim();
                System.out.print("Chuyen khoa: "); String ck = sc.nextLine().trim();
                qbs.them(new BacSi(id,ten,ns,sdt,ck));
                System.out.println("Da them bac si.");
            }
            case "3" -> {
                // Sửa thông tin bác sĩ
                System.out.print("ID can sua: "); String id = sc.nextLine().trim();
                BacSi f = qbs.timTheoId(id);
                if (f==null) { System.out.println("Khong tim thay."); break; }
                System.out.println("Thong tin hien tai: " + f);
                System.out.print("Ten moi (Enter de giu): "); String ten = sc.nextLine().trim(); if (ten.isEmpty()) ten=f.getTen();
                System.out.print("NS moi (Enter de giu): "); String ns = sc.nextLine().trim(); if (ns.isEmpty()) ns=f.getNgaySinh();
                System.out.print("SDT moi (Enter de giu): "); String sdt = sc.nextLine().trim(); if (sdt.isEmpty()) sdt=f.getSoDienThoai();
                System.out.print("Chuyen khoa moi (Enter de giu): "); String ck = sc.nextLine().trim(); if (ck.isEmpty()) ck=f.getChuyenKhoa();
                boolean ok = qbs.sua(new BacSi(id,ten,ns,sdt,ck));
                System.out.println(ok ? "Da cap nhat." : "Cap nhat that bai.");
            }
            case "4" -> {
                // Xóa bác sĩ
                System.out.print("ID can xoa: "); String id = sc.nextLine().trim();
                boolean ok = qbs.xoa(id);
                System.out.println(ok ? "Da xoa." : "Xoa that bai.");
            }
            default -> {}
        }
    }

    //SUBMENU LICH KHAM
    private static void menuLichKham(QuanLyLichKham qlk, QuanLyBenhNhan qbn, QuanLyBacSi qbs, Scanner sc) {
        System.out.println("\n--- LICH KHAM ---");
        System.out.println("1. Xem danh sach");
        System.out.println("2. Them (co kiem tra xung dot)");
        System.out.println("3. Sua");
        System.out.println("4. Xoa");
        System.out.println("5. Tim theo benh nhan");
        System.out.println("6. Cap nhat trang thai kham");
        System.out.println("0. Tro ve");
        System.out.print("Lua chon: ");
        String c = sc.nextLine().trim();
        switch (c) {
            case "1" -> qlk.hienThiDanhSach();
            case "2" -> {
                // Thêm lịch khám mới (có kiểm tra xung đột)
                System.out.print("ID lich: "); String id = sc.nextLine().trim();
                System.out.print("ID benh nhan: "); String idbn = sc.nextLine().trim();
                if (qbn.timTheoId(idbn)==null) { System.out.println("Benh nhan khong ton tai."); break; }
                System.out.print("ID bac si: "); String idbs = sc.nextLine().trim();
                if (qbs.timTheoId(idbs)==null) { System.out.println("Bac si khong ton tai."); break; }
                System.out.print("Ngay gio (dd-MM-yyyy HH:mm): "); String ng = sc.nextLine().trim();
                System.out.print("Ghi chu: "); String gh = sc.nextLine().trim();
                LichKham lk = new LichKham(id,idbn,idbs,ng,gh,0);
                if (lk.getNgayGioDate() == null) {
                    System.out.println("Dinh dang ngay gio khong hop le. Hay nhap theo dd-MM-yyyy HH:mm");
                    break;
                }
                boolean ok = qlk.themCoKiemTra(lk);
                System.out.println(ok ? "Da them lich." : "Xung dot lich: bac si da co lich o thoi gian nay.");
            }
            case "3" -> {
                // Sửa lịch khám
                System.out.print("ID can sua: "); String id = sc.nextLine().trim();
                LichKham f = qlk.timTheoId(id);
                if (f==null) { System.out.println("Khong tim thay."); break; }
                System.out.println("Thong tin hien tai: " + f);
                System.out.print("ID benh nhan (Enter de giu): "); String idbn = sc.nextLine().trim(); if (idbn.isEmpty()) idbn=f.getIdBenhNhan();
                System.out.print("ID bac si (Enter de giu): "); String idbs = sc.nextLine().trim(); if (idbs.isEmpty()) idbs=f.getIdBacSi();
                System.out.print("Ngay gio (dd-MM-yyyy HH:mm) (Enter de giu): "); String ng = sc.nextLine().trim();
                String ngFinal = ng.isEmpty() ? f.getNgayGio() : ng;
                System.out.print("Ghi chu (Enter de giu): "); String gh = sc.nextLine().trim(); if (gh.isEmpty()) gh=f.getGhiChu();
                LichKham updated = new LichKham(id,idbn,idbs,ngFinal,gh,f.getTrangThai());
                if (updated.getNgayGioDate() == null) {
                    System.out.println("Dinh dang ngay gio khong hop le. Sua that bai.");
                    break;
                }
                boolean ok = qlk.sua(updated);
                System.out.println(ok ? "Da cap nhat." : "Cap nhat that bai (co the do xung dot).");
            }
            case "4" -> {
                // Xóa lịch khám
                System.out.print("ID can xoa: "); String id = sc.nextLine().trim();
                boolean ok = qlk.xoa(id);
                System.out.println(ok ? "Da xoa." : "Xoa that bai.");
            }
            case "5" -> {
                // Tìm lịch theo bệnh nhân
                System.out.print("ID benh nhan: "); String idbn = sc.nextLine().trim();
                var list = qlk.timTheoBenhNhan(idbn);
                if (list.isEmpty()) System.out.println("Khong co lich cho benh nhan nay.");
                else list.forEach(System.out::println);
            }
            case "6" -> {
                // Cập nhật trạng thái lịch khám (chưa khám -> đã khám)
                System.out.print("ID lich: "); String id = sc.nextLine().trim();
                LichKham lk = qlk.timTheoId(id);
                if (lk==null) { System.out.println("Khong tim thay."); break; }
                System.out.print("Trang thai moi (0=chua kham, 1=da kham): "); int tt = Integer.parseInt(sc.nextLine().trim());
                LichKham updated = new LichKham(id,lk.getIdBenhNhan(),lk.getIdBacSi(),lk.getNgayGio(),lk.getGhiChu(),tt);
                boolean ok = qlk.sua(updated);
                System.out.println(ok ? "Da cap nhat trang thai." : "Cap nhat that bai.");
            }
            default -> {}
        }
    }

    //SUBMENU DON THUOC
    private static void menuDonThuoc(QuanLyDonThuoc qdt, QuanLyBenhNhan qbn, Scanner sc) {
        System.out.println("\n--- DON THUOC ---");
        System.out.println("1. Xem tat ca");
        System.out.println("2. Them don thuoc");
        System.out.println("3. Sua don thuoc");
        System.out.println("4. Xoa don thuoc");
        System.out.println("5. Xem don thuoc theo benh nhan");
        System.out.println("0. Tro ve");
        System.out.print("Lua chon: ");
        String c = sc.nextLine().trim();
        switch (c) {
            case "1" -> qdt.hienThiDanhSach();
            case "2" -> {
                // Thêm đơn thuốc mới
                System.out.print("ID don: "); String id = sc.nextLine().trim();
                System.out.print("ID benh nhan: "); String idbn = sc.nextLine().trim();
                if (qbn.timTheoId(idbn)==null) { System.out.println("Benh nhan khong ton tai."); break; }
                DonThuoc d = new DonThuoc(id,idbn);
                System.out.println("Nhap thuoc (ten:lieu), nhap rong de ket thuc:");
                while (true) {
                    System.out.print("Thuoc: "); String t = sc.nextLine().trim();
                    if (t.isEmpty()) break;
                    String[] kv = t.split(":",2);
                    if (kv.length<2) { System.out.println("Dinh dang sai (ten:lieu)."); continue; }
                    d.themThuoc(kv[0].trim(), kv[1].trim());
                }
                qdt.them(d);
                System.out.println("Da them don thuoc.");
            }
            case "3" -> {
                // Sửa đơn thuốc: nhập lại toàn bộ thuốc
                System.out.print("ID don can sua: "); String id = sc.nextLine().trim();
                System.out.print("ID benh nhan (cung ID neu khong doi): "); String idbn = sc.nextLine().trim();
                DonThuoc d = new DonThuoc(id,idbn);
                System.out.println("Nhap lai cac thuoc (ten:lieu) nhap rong de ket thuc:");
                while (true) {
                    System.out.print("Thuoc: "); String t = sc.nextLine().trim();
                    if (t.isEmpty()) break;
                    String[] kv = t.split(":",2);
                    if (kv.length<2) { System.out.println("Dinh dang sai (ten:lieu)."); continue; }
                    d.themThuoc(kv[0].trim(), kv[1].trim());
                }
                boolean ok = qdt.sua(d);
                System.out.println(ok ? "Da cap nhat." : "Cap nhat that bai (co the khong tim thay don).");
            }
            case "4" -> {
                // Xóa đơn thuốc
                System.out.print("ID don can xoa: "); String id = sc.nextLine().trim();
                boolean ok = qdt.xoa(id);
                System.out.println(ok ? "Da xoa." : "Xoa that bai.");
            }
            case "5" -> {
                // Xem đơn thuốc theo bệnh nhân
                System.out.print("ID benh nhan: "); String idbn = sc.nextLine().trim();
                var list = qdt.layTheoBenhNhan(idbn);
                if (list.isEmpty()) System.out.println("Khong tim thay don nao.");
                else { System.out.println("=== Don thuoc cua BN: " + idbn + " ==="); list.forEach(System.out::println); }
            }
            default -> {}
        }
    }

    //SUBMENU HOA DON
    private static void menuHoaDon(QuanLyHoaDon qhd, QuanLyBenhNhan qbn, QuanLyBacSi qbs, QuanLyLichKham qlk, Scanner sc) {
        System.out.println("\n--- HOA DON ---");
        System.out.println("1. Xem tat ca");
        System.out.println("2. Them hoa don");
        System.out.println("3. Sua hoa don");
        System.out.println("4. Xoa hoa don");
        System.out.println("5. Xem hoa don theo benh nhan");
        System.out.println("0. Tro ve");
        System.out.print("Lua chon: ");
        String c = sc.nextLine().trim();
        switch (c) {
            case "1" -> qhd.hienThiDanhSach();
            case "2" -> {
                // Thêm hóa đơn khám chữa bệnh
                System.out.print("ID hoa don: "); String id = sc.nextLine().trim();
                System.out.print("ID benh nhan: "); String idbn = sc.nextLine().trim();
                if (qbn.timTheoId(idbn)==null) { System.out.println("Benh nhan khong ton tai."); break; }
                System.out.print("ID bac si: "); String idbs = sc.nextLine().trim();
                if (qbs.timTheoId(idbs)==null) { System.out.println("Bac si khong ton tai."); break; }
                System.out.print("ID lich kham: "); String idlk = sc.nextLine().trim();
                if (qlk.timTheoId(idlk)==null) { System.out.println("Lich kham khong ton tai."); break; }
                System.out.print("Ngay lap (dd-MM-yyyy) (Enter de lay hom nay): "); String ngay = sc.nextLine().trim();
                System.out.print("Chan doan: "); String cd = sc.nextLine().trim();
                HoaDon hd = new HoaDon(id, idbn, idbs, idlk, ngay, cd);
                System.out.println("Nhap dich vu (ten:gia), nhap rong de ket thuc:");
                while (true) {
                    System.out.print("Dich vu: "); String dv = sc.nextLine().trim();
                    if (dv.isEmpty()) break;
                    String[] kv = dv.split(":",2);
                    if (kv.length<2) { System.out.println("Dinh dang sai (ten:gia)."); continue; }
                    try {
                        hd.themDichVu(kv[0].trim(), Double.parseDouble(kv[1].trim()));
                    } catch (Exception ex) {
                        System.out.println("Gia khong hop le.");
                    }
                }
                qhd.them(hd);
                System.out.println("Da them hoa don. Tong tien: " + hd.getTongTien());
            }
            case "3" -> {
                // Sửa hóa đơn
                System.out.print("ID can sua: "); String id = sc.nextLine().trim();
                HoaDon f = qhd.timTheoId(id);
                if (f==null) { System.out.println("Khong tim thay."); break; }
                System.out.println("Thong tin hien tai: " + f);
                System.out.print("Chan doan moi (Enter de giu): "); String cd = sc.nextLine().trim(); if (cd.isEmpty()) cd=f.getChanDoan();
                HoaDon hd = new HoaDon(id, f.getIdBenhNhan(), f.getIdBacSi(), f.getIdLichKham(), f.getNgayLapHD(), cd);
                // Copy d?ch v? c?
                for (var entry : f.getDichVu().entrySet()) {
                    hd.themDichVu(entry.getKey(), entry.getValue());
                }
                System.out.println("Dich vu hien tai: " + f.getDichVu());
                System.out.print("Cap nhat dich vu? (y/n): ");
                if (sc.nextLine().trim().equalsIgnoreCase("y")) {
                    hd.getDichVu().clear();
                    System.out.println("Nhap lai dich vu (ten:gia), nhap rong de ket thuc:");
                    while (true) {
                        System.out.print("Dich vu: "); String dv = sc.nextLine().trim();
                        if (dv.isEmpty()) break;
                        String[] kv = dv.split(":",2);
                        if (kv.length<2) { System.out.println("Dinh dang sai (ten:gia)."); continue; }
                        try {
                            hd.themDichVu(kv[0].trim(), Double.parseDouble(kv[1].trim()));
                        } catch (Exception ex) {
                            System.out.println("Gia khong hop le.");
                        }
                    }
                }
                boolean ok = qhd.sua(hd);
                System.out.println(ok ? "Da cap nhat. Tong tien: " + hd.getTongTien() : "Cap nhat that bai.");
            }
            case "4" -> {
                // Xóa hóa đơn
                System.out.print("ID can xoa: "); String id = sc.nextLine().trim();
                boolean ok = qhd.xoa(id);
                System.out.println(ok ? "Da xoa." : "Xoa that bai.");
            }
            case "5" -> {
                // Xem hóa đơn theo bệnh nhân
                System.out.print("ID benh nhan: "); String idbn = sc.nextLine().trim();
                var list = qhd.timTheoBeNhan(idbn);
                if (list.isEmpty()) System.out.println("Khong co hoa don nao.");
                else { System.out.println("=== Hoa don cua BN: " + idbn + " ==="); list.forEach(System.out::println); }
            }
            default -> {}
        }
    }
}
