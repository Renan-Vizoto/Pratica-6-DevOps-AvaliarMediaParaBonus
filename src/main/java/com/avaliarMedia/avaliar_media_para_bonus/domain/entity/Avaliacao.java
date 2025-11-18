package com.avaliarMedia.avaliar_media_para_bonus.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidade de Domínio que representa uma Avaliação.
 * Possui identidade única (ID) e relacionamento com Aluno.
 * 
 * Regra de Negócio:
 * - O aluno tem 1 nota de 1 curso
 * - Se nota > 7.0, ganha 3 cursos bônus
 * - Se nota <= 7.0, não ganha bônus
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
    
    private Double nota; // Nota do curso (única)
}

