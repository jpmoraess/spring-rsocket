package br.com.moraesit.springrsocket.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ComputationResponseDTO {
    private int input;
    private int output;
}
