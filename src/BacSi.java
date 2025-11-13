/*
  BacSi kế thừa Nguoi; dùng cùng định dạng ngày dd-MM-yyyy.
*/
public class BacSi extends Nguoi {
    private String chuyenKhoa;

    public BacSi(String id, String ten, String ngaySinh, String soDienThoai, String chuyenKhoa) {
        super(id, ten, ngaySinh, soDienThoai);
        this.chuyenKhoa = chuyenKhoa;
    }

    public String getChuyenKhoa() { return chuyenKhoa; }

    @Override
    public String toString() {
        return "BacSi: " + super.toString() + " | Khoa: " + chuyenKhoa;
    }
}
