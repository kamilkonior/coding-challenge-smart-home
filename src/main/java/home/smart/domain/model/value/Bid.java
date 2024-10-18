package home.smart.domain.model.value;

import lombok.Value;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class Bid implements Comparable<Bid> {
    Price value;

    @Override
    public int compareTo(Bid that) {
        return this.value.compareTo(that.value);
    }
}
