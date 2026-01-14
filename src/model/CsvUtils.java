package model;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class CsvUtils {
    public static List<String[]> readCsv(String path) throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!headerSkipped) { headerSkipped = true; continue; }
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replaceAll("^\"|\"$", "").trim();
                }
                rows.add(values);
            }
        }
        return rows;
    }
    public static void appendLine(String path, String[] values) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(String.join(",", values));
            bw.newLine();
        }
    }
    public static void writeCsv(String path, List<String[]> rows) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String[] row : rows) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        }
    }
}
