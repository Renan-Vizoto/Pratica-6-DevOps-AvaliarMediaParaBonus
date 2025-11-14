package com.avaliarMedia.avaliar_media_para_bonus.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para Avaliação.
 * Recebe dados da requisição HTTP e transfere para camada de serviço.
 * 
 * Regra: O aluno tem 1 nota de 1 curso.
 * Se nota > 7.0, ganha 3 cursos bônus.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDTO {
    
    private Long id;
    
    @NotNull(message = "ID do aluno é obrigatório")
    private Long alunoId;
    
    @NotNull(message = "Nota é obrigatória")
    private Double nota;
}

