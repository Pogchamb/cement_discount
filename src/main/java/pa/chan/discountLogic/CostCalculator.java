package pa.chan.discountLogic;

import pa.chan.model.Order;
import pa.chan.model.PromotionConfig;

import java.math.BigDecimal;
import java.util.*;

public class CostCalculator {

    private final BigDecimal pricePerWeight;
    private final DiscountPolicy discountPolicy;

    public CostCalculator(PromotionConfig config) {
        this.pricePerWeight = config.basePricePerKg();
        this.discountPolicy = new DiscountPolicy(
                config.startDiscountPercent(),
                config.discountStepPercent());
    }

    public Map<String, BigDecimal> totals(List<Order> orders) {
        List<Order> queue = new ArrayList<>(orders);
        queue.sort(Comparator.comparing(Order::submittedAt));

        Map<String, BigDecimal> totals = new LinkedHashMap<>();
        for (int i = 0; i < queue.size(); i++) {
            Order order = queue.get(i);
            double percent = discountPolicy.forPosition(i);
            BigDecimal factor = BigDecimal.ONE.subtract(BigDecimal.valueOf(percent / 100));
            BigDecimal cost = BigDecimal.valueOf(order.amountWeight())
                    .multiply(pricePerWeight)
                    .multiply(factor);
            totals.merge(order.companyName(), cost, BigDecimal::add);
        }
        return totals;
    }


}
