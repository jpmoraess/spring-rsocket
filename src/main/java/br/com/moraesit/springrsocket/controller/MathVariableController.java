package br.com.moraesit.springrsocket.controller;

import br.com.moraesit.springrsocket.dtos.ComputationRequestDTO;
import br.com.moraesit.springrsocket.service.MathService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@MessageMapping("math.service")
public class MathVariableController {

    private final MathService mathService;

    public MathVariableController(MathService mathService) {
        this.mathService = mathService;
    }

    @MessageMapping("print.{input}")
    public Mono<Void> print(@DestinationVariable int input) {
        System.out.println("Received: " + input);
        Mono<ComputationRequestDTO> requestDTOMono = Mono.just(new ComputationRequestDTO(input));
        return mathService.print(requestDTOMono);
    }
}
