/*
  BenhNhan kế thừa Nguoi, thêm: tuổi, giới tính, địa chỉ
*/
public class BenhNhan extends Nguoi {
    private String soTheBHYT;
    private int tuoi;
    private String gioiTinh;        // Nam / Nu
    private String diaChi;

    public BenhNhan(String id, String ten, String ngaySinh, String soDienThoai, 
                    String soTheBHYT, int tuoi, String gioiTinh, String diaChi) {
        super(id, ten, ngaySinh, soDienThoai);
        this.soTheBHYT = soTheBHYT;
        this.tuoi = tuoi;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
    }

    public String getSoTheBHYT() { return soTheBHYT; }
    public int getTuoi() { return tuoi; }
    public String getGioiTinh() { return gioiTinh; }
    public String getDiaChi() { return diaChi; }

    @Override
    public String toString() {
        return "BenhNhan: " + id + " | " + ten + " | " + getNgaySinh() + " | " + 
               tuoi + " | " + gioiTinh + " | " + diaChi + " | SDT: " + soDienThoai + 
               " | BHYT: " + soTheBHYT;
    }
}
