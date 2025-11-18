package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidade JPA para persistência de Avaliação.
 * 
 * Regra de Negócio:
 * - O aluno tem 1 nota de 1 curso
 * - Se nota > 7.0, ganha 3 cursos bônus
 * 
 * @Entity: Marca como entidade JPA
 * @Table: Define nome da tabela
 * @OneToOne: Relacionamento 1:1 com Aluno
 * @JoinColumn: Define a coluna de chave estrangeira
 */
@Entity
@Table(name = "avaliacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "aluno")
public class AvaliacaoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "aluno_id", nullable = false, unique = true)
    private AlunoEntity aluno;
    
    @Column(name = "nota", nullable = false)
    private Double nota;
}

