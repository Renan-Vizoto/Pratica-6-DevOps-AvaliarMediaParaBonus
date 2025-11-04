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
 * Entidade JPA para persistência no banco de dados.
 * Mapeia a entidade de domínio Aluno para o banco.
 * 
 * @Entity: Define que esta classe é uma entidade JPA
 * @Table: Define o nome da tabela no banco
 * @Id: Marca o campo como chave primária
 * @GeneratedValue: Define estratégia de geração automática de ID
 * @OneToOne: Define relacionamento 1:1 com Avaliacao
 */
@Entity
@Table(name = "aluno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "avaliacao")
public class AlunoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @OneToOne(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private AvaliacaoEntity avaliacao;
}

