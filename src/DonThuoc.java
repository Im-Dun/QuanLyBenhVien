import java.util.HashMap;
import java.util.Map;

public class DonThuoc {
    private String id;
    private String idBenhNhan;
    private Map<String, String> thuoc; // tên thuốc -> liều lượng ghi chú

    public DonThuoc(String id, String idBenhNhan) {
        this.id = id;
        this.idBenhNhan = idBenhNhan;
        this.thuoc = new HashMap<>();
    }

    public void themThuoc(String ten, String lieu) {
        thuoc.put(ten, lieu);
    }

    public String getId() { return id; }
    public String getIdBenhNhan() { return idBenhNhan; }
    public Map<String, String> getThuoc() { return thuoc; }

    @Override
    public String toString() {
        return id + " | BN:" + idBenhNhan + " | Thuoc: " + thuoc.toString();
    }
}
