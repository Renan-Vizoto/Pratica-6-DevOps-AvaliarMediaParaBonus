package ac2_project.example.ac2_ca.domain.event;

import java.time.Instant;

public class GamificationEvent {

    private Long alunoId;
    private int cursosDisponiveis;
    private int moedas;
    private String planoAssinatura;
    private Instant criadoEm = Instant.now();

    public GamificationEvent() {
    }

    public GamificationEvent(Long alunoId, int cursosDisponiveis, int moedas, String planoAssinatura) {
        this.alunoId = alunoId;
        this.cursosDisponiveis = cursosDisponiveis;
        this.moedas = moedas;
        this.planoAssinatura = planoAssinatura;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public int getCursosDisponiveis() {
        return cursosDisponiveis;
    }

    public void setCursosDisponiveis(int cursosDisponiveis) {
        this.cursosDisponiveis = cursosDisponiveis;
    }

    public int getMoedas() {
        return moedas;
    }

    public void setMoedas(int moedas) {
        this.moedas = moedas;
    }

    public String getPlanoAssinatura() {
        return planoAssinatura;
    }

    public void setPlanoAssinatura(String planoAssinatura) {
        this.planoAssinatura = planoAssinatura;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }
}
