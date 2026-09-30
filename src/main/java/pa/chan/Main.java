package pa.chan;


import pa.chan.discountLogic.CostCalculator;
import pa.chan.model.Order;
import pa.chan.model.PromotionConfig;
import pa.chan.output.ResultWriter;
import pa.chan.source.HashDelimitedOrderAdapter;
import pa.chan.source.OrderSource;
import pa.chan.source.OrderSourceFactory;
import pa.chan.source.PipeDelimitedOrderAdapter;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    static void main() throws IOException {

        List<Path> files = List.of(
                Path.of("data", "discount_day.txt"),
                Path.of("data", "discount_day_without_ext")
        );

        OrderSourceFactory factory = new OrderSourceFactory(List.of(
                new PipeDelimitedOrderAdapter(),
                new HashDelimitedOrderAdapter()
        ));

        List<Order> orders = new ArrayList<>();
        for (Path file : files) {
            OrderSource source = factory.forFile(file);
            orders.addAll(source.readOrders(file));
        }

        CostCalculator calculator = new CostCalculator(
                new PromotionConfig(new BigDecimal("10"), 50, 5));


        Map<String, BigDecimal> totals = calculator.totals(orders);

        new ResultWriter().write(Path.of("data", "result.txt"), totals);
        System.out.println("Готово: заказов — " + orders.size()
                + ", компаний — " + totals.size());
    }
}
