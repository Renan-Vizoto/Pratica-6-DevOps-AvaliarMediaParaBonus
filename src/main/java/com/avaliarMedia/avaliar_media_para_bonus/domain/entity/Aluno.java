package com.avaliarMedia.avaliar_media_para_bonus.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidade de Domínio que representa um Aluno.
 * Possui identidade única (ID).
 * 
 * Importância dos recursos Lombok:
 * - @Getter/@Setter: Gera getters e setters automaticamente, reduzindo código em ~50%
 * - @NoArgsConstructor: Cria construtor sem argumentos (necessário para JPA/Hibernate)
 * - @AllArgsConstructor: Cria construtor com todos os campos
 * - @Builder: Implementa padrão Builder para criação flexível de objetos
 * - @EqualsAndHashCode: Compara entidades pelo ID (identidade), não por todos os campos
 * - @ToString: Gera toString() excluindo relacionamentos (evita loops infinitos)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Aluno {
    
    @EqualsAndHashCode.Include
    private Long id;
    
    private String nome;
    
    private String email;
    
    /**
     * Relacionamento 1:1 com Avaliacao
     * Um aluno possui uma avaliação do curso atual
     */
    // Será mapeado na camada de infraestrutura
}

