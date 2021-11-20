package br.com.moraesit.springrsocket.controller;

import br.com.moraesit.springrsocket.dtos.ChartResponseDTO;
import br.com.moraesit.springrsocket.dtos.ComputationRequestDTO;
import br.com.moraesit.springrsocket.dtos.ComputationResponseDTO;
import br.com.moraesit.springrsocket.service.MathService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class MathController {

    private final MathService mathService;

    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    @MessageMapping("math.service.print")
    public Mono<Void> print(Mono<ComputationRequestDTO> requestDTOMono) {
        return mathService.print(requestDTOMono);
    }

    @MessageMapping("math.service.square")
    public Mono<ComputationResponseDTO> findSquare(Mono<ComputationRequestDTO> requestDTOMono) {
        return mathService.findSquare(requestDTOMono);
    }

    @MessageMapping("math.service.table")
    public Flux<ComputationResponseDTO> tableStream(Mono<ComputationRequestDTO> requestDTOMono) {
        return requestDTOMono.flatMapMany(mathService::tableStream);
    }

    @MessageMapping("math.service.chart")
    public Flux<ChartResponseDTO> chartStream(Flux<ComputationRequestDTO> requestDTOFlux) {
        return mathService.chartStream(requestDTOFlux);
    }
}
