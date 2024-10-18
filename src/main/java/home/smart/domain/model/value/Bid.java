package home.smart.domain.model.value;


public record Bid(Price value) implements Comparable<Bid> {
    @Override
    public int compareTo(Bid that) {
        return this.value.compareTo(that.value);
    }
}
