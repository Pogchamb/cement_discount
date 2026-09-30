package pa.chan.source;

import pa.chan.exceptions.OrderParseException;
import pa.chan.model.Order;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AbstractDelimitedOrderSource implements OrderSource {
    private final String delimiter;

    protected AbstractDelimitedOrderSource(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public boolean supports(Path file) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String firstLine = reader.readLine();
            return firstLine != null && firstLine.contains(delimiter);
        }
    }

    @Override
    public List<Order> readOrders(Path file) throws IOException {
        List<Order> orders = new ArrayList<>();
        int lineNumber = 0;

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                orders.add(parseLine(file, line, lineNumber));
            }
        }

        return orders;
    }

    private Order parseLine(Path file, String line, int lineNumber) {
        String[] lineParts = line.split(Pattern.quote(delimiter), -1);

        if (lineParts.length != 3) {
            throw new OrderParseException(file.getFileName() + ", строка " + lineNumber
                    + ": ожидалось 3 поля, найдено " + lineParts.length);
        }

        try {
            return new Order(
                    LocalDateTime.parse(lineParts[0]),
                    lineParts[1].trim(),
                    Long.parseLong(lineParts[2].trim())
            );
        } catch (IllegalArgumentException e) {
            throw new OrderParseException(file.getFileName() + ", строка " + lineNumber
                    + ": " + e.getMessage());
        }
    }
}
