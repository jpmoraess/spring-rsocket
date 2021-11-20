package br.com.moraesit.springrsocket.service;

import br.com.moraesit.springrsocket.dtos.ChartResponseDTO;
import br.com.moraesit.springrsocket.dtos.ComputationRequestDTO;
import br.com.moraesit.springrsocket.dtos.ComputationResponseDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MathService {

    // fire forget
    public Mono<Void> print(Mono<ComputationRequestDTO> requestDTOMono) {
        return requestDTOMono
                .doOnNext(System.out::println)
                .then();
    }

    // request response
    public Mono<ComputationResponseDTO> findSquare(Mono<ComputationRequestDTO> requestDTOMono) {
        return requestDTOMono
                .map(ComputationRequestDTO::getInput)
                .map(i -> new ComputationResponseDTO(i, i * i));
    }

    // request stream
    public Flux<ComputationResponseDTO> tableStream(ComputationRequestDTO requestDTO) {
        return Flux.range(1, 10)
                .map(i -> new ComputationResponseDTO(requestDTO.getInput(), requestDTO.getInput() * i));
    }

    // request channel -> x ^2 + 1
    public Flux<ChartResponseDTO> chartStream(Flux<ComputationRequestDTO> requestDTOFlux) {
        return requestDTOFlux
                .map(ComputationRequestDTO::getInput)
                .map(i -> new ChartResponseDTO(i, (i * i) + 1));
    }

}
