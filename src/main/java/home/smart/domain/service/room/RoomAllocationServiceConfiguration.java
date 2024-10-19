package home.smart.domain.service.room;

import home.smart.domain.model.value.Price;
import home.smart.domain.service.room.allocation.RoomBookingAllocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class RoomAllocationServiceConfiguration {
    @Bean
    public RoomBookingAllocator roomBookingAllocator() {
        return new RoomBookingAllocator(Price.of(BigDecimal.valueOf(100)));
    }
}
