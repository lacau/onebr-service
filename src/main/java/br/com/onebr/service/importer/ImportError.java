package br.com.onebr.service.importer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportError {

    private int lineNumber;

    @Builder.Default
    private Map<String, List<String>> map = new LinkedHashMap<>();

    boolean hasError() {
        return map.size() != 0;
    }

    public void printReport() {
        for (String key : map.keySet()) {
            final StringBuilder line = new StringBuilder();
            line.append("- ").append(key).append(": ");
            for (String value : map.get(key)) {
                line.append(value).append(", ");
            }
            line.delete(line.length() - 2, line.length() - 1);
            System.out.println(line);
        }
    }
}
