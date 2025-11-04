package com.avaliarMedia.avaliar_media_para_bonus.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de resposta para cálculo de bônus.
 * Retorna informações sobre elegibilidade e quantidade de cursos bônus.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BonusResponseDTO {
    
    private Long alunoId;
    private String nomeAluno;
    private Double mediaFinal;
    private Boolean elegivel;
    private Integer quantidadeCursosBonus;
    private String descricao;
}

