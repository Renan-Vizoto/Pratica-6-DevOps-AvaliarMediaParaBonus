package com.avaliarMedia.avaliar_media_para_bonus.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Entidade de Domínio que representa uma Avaliação.
 * Possui identidade única (ID) e relacionamento com Aluno.
 * 
 * Importância dos recursos Lombok:
 * - @Getter/@Setter: Automatiza acesso aos campos, mantendo encapsulamento
 * - @NoArgsConstructor: Essencial para JPA - permite criação de proxy pelo Hibernate
 * - @AllArgsConstructor: Útil para testes e criação direta de objetos
 * - @Builder: Facilita criação de objetos complexos com muitos campos opcionais
 * - @EqualsAndHashCode: Compara por ID (identidade), não por conteúdo (evita problemas com collections)
 * - @ToString: Exclui relacionamentos para evitar loops e facilitar debug
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Avaliacao {
    
    @EqualsAndHashCode.Include
    private Long id;
    
    private Aluno aluno;
    
    private List<Double> notas; // Lista de notas para cálculo da média
    
    private Double mediaFinal;
    
    /**
     * Calcula a média final baseada nas notas
     */
    public void calcularMedia() {
        if (notas != null && !notas.isEmpty()) {
            double soma = notas.stream().mapToDouble(Double::doubleValue).sum();
            this.mediaFinal = soma / notas.size();
        }
    }
}

