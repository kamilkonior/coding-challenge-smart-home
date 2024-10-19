package home.smart.controller;

import home.smart.dto.occupancy.OccupancyHttpResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class OccupancyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @ParameterizedTest
    @MethodSource("occupancyEstimationArguments")
    public void shouldEstimateOccupancy(
        int premiumRooms,
        int economyRooms,
        OccupancyHttpResponse expectedOccupancyHttpResponse
    ) {
        //given
        var url = "http://localhost:" + port + "/occupancy";
        var occupancyHttpRequest = Map.of(
            "premiumRooms", premiumRooms,
            "economyRooms", economyRooms,
            "potentialGuests", List.of(23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209)
        );

        //when
        var response = restTemplate.postForEntity(
            url,
            occupancyHttpRequest,
            OccupancyHttpResponse.class
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedOccupancyHttpResponse);
    }

    private static Stream<Arguments> occupancyEstimationArguments() {
        return Stream.of(
            Arguments.of(7, 5, new OccupancyHttpResponse(6, "1054", 4, "189.99")),
            Arguments.of(7, 5, new OccupancyHttpResponse(6, "1054", 4, "189.99")),
            Arguments.of(7, 5, new OccupancyHttpResponse(6, "1054", 4, "189.99"))
        );
    }
}