package ac2_project.example.ac2_ca.application.dto;

public class GamificationResponse {

    private Long alunoId;
    private int cursosDisponiveis;
    private int moedas;
    private String plano;

    public GamificationResponse() {
    }

    public GamificationResponse(Long alunoId, int cursosDisponiveis, int moedas, String plano) {
        this.alunoId = alunoId;
        this.cursosDisponiveis = cursosDisponiveis;
        this.moedas = moedas;
        this.plano = plano;
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

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }
}
