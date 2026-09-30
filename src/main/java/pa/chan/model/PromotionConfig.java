package pa.chan.model;

import java.math.BigDecimal;
import java.util.Objects;

public record PromotionConfig(
        BigDecimal basePricePerKg,
        double startDiscountPercent,
        double discountStepPercent
) {
    public PromotionConfig {
        Objects.requireNonNull(basePricePerKg, "цена не задана");
        if (basePricePerKg.signum() <= 0) {
            throw new IllegalArgumentException("цена должна быть больше нуля");
        }
        if (startDiscountPercent < 0 || startDiscountPercent > 100) {
            throw new IllegalArgumentException("стартовая скидка — от 0 до 100");
        }
        if (discountStepPercent < 0) {
            throw new IllegalArgumentException("шаг не может быть отрицательным");
        }
    }
}
