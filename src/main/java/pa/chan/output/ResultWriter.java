package pa.chan.output;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ResultWriter {

    public void write(Path file, Map<String, BigDecimal> totals) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, BigDecimal> entry : totals.entrySet()) {
                writer.write(entry.getKey() + " - "
                        + entry.getValue().setScale(2, RoundingMode.HALF_UP));
                writer.newLine();
            }
        }
    }
}
