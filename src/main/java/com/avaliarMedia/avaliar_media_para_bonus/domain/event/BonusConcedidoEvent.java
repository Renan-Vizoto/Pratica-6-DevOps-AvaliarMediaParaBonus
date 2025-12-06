package com.avaliarMedia.avaliar_media_para_bonus.domain.event;

import java.time.Instant;

/**
 * Evento de domínio que representa quando um bônus de cursos é concedido a um aluno.
 * Este evento será publicado no Kafka para outros serviços consumirem.
 */
public class BonusConcedidoEvent {
    
    private Long alunoId;
    private String nomeAluno;
    private Double nota;
    private Integer quantidadeCursosBonus;
    private Instant ocorridoEm;
    
    public BonusConcedidoEvent() {
        this.ocorridoEm = Instant.now();
    }
    
    public BonusConcedidoEvent(Long alunoId, String nomeAluno, Double nota, Integer quantidadeCursosBonus) {
        this.alunoId = alunoId;
        this.nomeAluno = nomeAluno;
        this.nota = nota;
        this.quantidadeCursosBonus = quantidadeCursosBonus;
        this.ocorridoEm = Instant.now();
    }
    
    // Getters e Setters
    public Long getAlunoId() {
        return alunoId;
    }
    
    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }
    
    public String getNomeAluno() {
        return nomeAluno;
    }
    
    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }
    
    public Double getNota() {
        return nota;
    }
    
    public void setNota(Double nota) {
        this.nota = nota;
    }
    
    public Integer getQuantidadeCursosBonus() {
        return quantidadeCursosBonus;
    }
    
    public void setQuantidadeCursosBonus(Integer quantidadeCursosBonus) {
        this.quantidadeCursosBonus = quantidadeCursosBonus;
    }
    
    public Instant getOcorridoEm() {
        return ocorridoEm;
    }
    
    public void setOcorridoEm(Instant ocorridoEm) {
        this.ocorridoEm = ocorridoEm;
    }
    
    @Override
    public String toString() {
        return String.format("BonusConcedidoEvent{alunoId=%d, nome='%s', nota=%.1f, cursosBonus=%d, ocorridoEm=%s}",
                alunoId, nomeAluno, nota, quantidadeCursosBonus, ocorridoEm);
    }
}

