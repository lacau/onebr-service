package br.com.onebr.service.importer;

import com.google.common.base.CaseFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ImporterStats {

    private List<ImportError> errors = new ArrayList<>();

    private Map<String, Set<String>> compiled = new HashMap<>();

    private String sheetName;

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public void setCurrentLine(int line) {
        errors.add(ImportError.builder().lineNumber(line).build());
    }

    public void addError(String fieldName, String value) {
        final Map<String, List<String>> map = getCurrentError().getMap();
        if (map.get(fieldName) == null) {
            map.put(fieldName, new ArrayList<>());
        }
        map.get(fieldName).add(value);

        final Set<String> comp = compiled.get(fieldName);
        if (comp == null) {
            compiled.put(fieldName, new HashSet<>());
        }
        compiled.get(fieldName).add(value);
    }

    public boolean hasError() {
        return errors.size() != 0 && errors.get(0).hasError();
    }

    public void printReport() {
        System.out.println("==========================================");
        System.out.println("File name: " + sheetName);
        for (ImportError importError : errors) {
            System.out.println("# Line " + importError.getLineNumber());
            importError.printReport();
        }
        System.out.println("### RESUME ###");
        for (String key : compiled.keySet()) {
            final StringBuilder line = new StringBuilder("- ").append(key).append(": ");
            final Set<String> values = compiled.get(key);
            for (String value : values) {
                line.append(value).append(", ");
            }
            line.delete(line.length() - 2, line.length() - 1);
            System.out.println(line);
        }
        System.out.println("==========================================");
        System.out.println("### SQL ###");
        printSQLInserts();
    }

    private void printSQLInserts() {
        for (String key : compiled.keySet()) {
            final Set<String> values = compiled.get(key);
            final String tableName = parseToTableName(key);
            System.out.println(String.format("-- %s", tableName));
            for (String value : values) {
                final StringBuilder line = new StringBuilder("insert into ")
                    .append(tableName)
                    .append("(name) values(")
                    .append("'")
                    .append(value.replaceAll("'", "''"))
                    .append("');");
                System.out.println(line);
            }
        }
    }

    private String parseToTableName(String key) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key);
    }

    private ImportError getCurrentError() {
        return errors.get(errors.size() - 1);
    }
}
