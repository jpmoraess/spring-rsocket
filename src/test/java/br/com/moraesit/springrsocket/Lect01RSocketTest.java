package br.com.moraesit.springrsocket;

import br.com.moraesit.springrsocket.dtos.ComputationRequestDTO;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
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
}
