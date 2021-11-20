package br.com.moraesit.springrsocket;

import br.com.moraesit.springrsocket.dtos.ChartResponseDTO;
import br.com.moraesit.springrsocket.dtos.ComputationRequestDTO;
import br.com.moraesit.springrsocket.dtos.ComputationResponseDTO;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Lect01RSocketTest {

    private RSocketRequester requester;

    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeAll
    public void setup() {
        this.requester = this.builder.transport(TcpClientTransport.create("localhost", 6565));
    }

    @Test
    @DisplayName("fireAndForget")
    public void fireAndForget() {
        Mono<Void> mono = this.requester.route("math.service.print")
                .data(new ComputationRequestDTO(5))
                .send();

        StepVerifier.create(mono)
                .verifyComplete();
    }

    @Test
    @DisplayName("requestResponse")
    public void requestResponse() {
        Mono<ComputationResponseDTO> mono = this.requester.route("math.service.square")
                .data(new ComputationRequestDTO(5))
                .retrieveMono(ComputationResponseDTO.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

	@Test
	@DisplayName("requestStream")
	public void requestStream() {
		Flux<ComputationResponseDTO> flux = this.requester.route("math.service.table")
				.data(new ComputationRequestDTO(5))
				.retrieveFlux(ComputationResponseDTO.class)
				.doOnNext(System.out::println);

		StepVerifier.create(flux)
				.expectNextCount(10)
				.verifyComplete();
	}

	@Test
	@DisplayName("requestChannel")
	public void requestChannel() {
		Flux<ComputationRequestDTO> dtoFlux = Flux.range(-10, 21)
				.map(ComputationRequestDTO::new);

		Flux<ChartResponseDTO> flux = this.requester.route("math.service.chart")
				.data(dtoFlux)
				.retrieveFlux(ChartResponseDTO.class)
				.doOnNext(System.out::println);

		StepVerifier.create(flux)
				.expectNextCount(21)
				.verifyComplete();
	}
}
