package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Entidade JPA para persistência de Avaliação.
 * 
 * @Entity: Marca como entidade JPA
 * @Table: Define nome da tabela
 * @OneToOne: Relacionamento 1:1 com Aluno
 * @JoinColumn: Define a coluna de chave estrangeira
 * @ElementCollection: Permite armazenar lista de valores simples (notas)
 * @CollectionTable: Define tabela para a coleção
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
    
    @ElementCollection
    @CollectionTable(name = "avaliacao_notas", joinColumns = @JoinColumn(name = "avaliacao_id"))
    @Column(name = "nota")
    private List<Double> notas;
    
    @Column(name = "media_final")
    private Double mediaFinal;
}

