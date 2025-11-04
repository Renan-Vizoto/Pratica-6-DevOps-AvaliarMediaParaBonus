package com.avaliarMedia.avaliar_media_para_bonus.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para Avaliação.
 * Recebe dados da requisição HTTP e transfere para camada de serviço.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDTO {
    
    private Long id;
    
    @NotNull(message = "ID do aluno é obrigatório")
    private Long alunoId;
    
    @NotNull(message = "Lista de notas é obrigatória")
    @Size(min = 1, message = "Deve ter pelo menos uma nota")
    private List<Double> notas;
    
    private Double mediaFinal;
}

