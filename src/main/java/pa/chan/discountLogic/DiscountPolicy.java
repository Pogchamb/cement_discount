package pa.chan.discountLogic;

public record DiscountPolicy (double startPercent, double stepPercent) {

    public DiscountPolicy {
        if (startPercent < 0 || stepPercent < 0) {
            throw new IllegalArgumentException("Скидка и шаг не могут быть отрицательными");
        }
    }

    public double forPosition(int position) {
        return Math.max(0, startPercent - position * stepPercent);
    }

}
