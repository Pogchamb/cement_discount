package pa.chan.source;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class OrderSourceFactory {
    private final List<OrderSource> sources;

    public OrderSourceFactory(List<OrderSource> sources) {
        this.sources = List.copyOf(sources);
    }

    public OrderSource forFile(Path file) throws IOException {
        for (OrderSource source : sources) {
            if (source.supports(file)) {
                return source;
            }
        }

        throw new IllegalArgumentException("Неизвестный формат файла: " + file.getFileName());
    }
}
