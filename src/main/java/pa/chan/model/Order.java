package pa.chan.model;

import java.time.LocalDateTime;
import java.util.Objects;

public record Order(
        LocalDateTime submittedAt,
        String companyName,
        long amountWeight) {

    public Order {
        Objects.requireNonNull(submittedAt, "submittedAt не задано");
        Objects.requireNonNull(companyName, "companyName не задано");
        if (companyName.isBlank() || amountWeight <= 0) {
            throw new IllegalArgumentException(
                    "Некорректный заказ: компания: \"" + companyName + "\", кг : " + amountWeight);
        }
        companyName = companyName.trim();
    }

}
