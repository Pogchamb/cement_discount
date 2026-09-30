package pa.chan.source;

import pa.chan.model.Order;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface OrderSource {

    boolean supports(Path file) throws IOException;

    List<Order> readOrders(Path file) throws IOException;

}
