import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DuLieuTrongBoNho {

    // Generic method: docFile -> parse each line to T via parser
    public static <T> ArrayList<T> docFile(String path, Function<String, T> parser) {
        ArrayList<T> result = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) {
            // Không nêu ngoại lệ, trả về list rỗng (có thể tạo file khi lưu)
            return result;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                try {
                    T obj = parser.apply(line);
                    if (obj != null) result.add(obj);
                } catch (Exception ex) {
                    // lỗi parse 1 dòng -> bỏ qua, tiếp tục
                    System.err.println("Loi parse dong: " + line + " -> " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Loi doc file " + path + ": " + e.getMessage());
        }
        return result;
    }

    // Ghi file từ danh sách chuỗi, với xử lý ngoại lệ
    public static void ghiFile(String path, List<String> lines) {
        File f = new File(path);
        // Đảm bảo thư mục tồn tại
        f.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Loi ghi file " + path + ": " + e.getMessage());
        }
    }
}
