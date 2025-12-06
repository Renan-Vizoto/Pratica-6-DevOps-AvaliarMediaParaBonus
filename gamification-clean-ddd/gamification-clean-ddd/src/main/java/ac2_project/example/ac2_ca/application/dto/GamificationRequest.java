package ac2_project.example.ac2_ca.application.dto;

public class GamificationRequest {

    private Long alunoId;
    private String nomeCurso;
    private double mediaFinal;
    private boolean ajudouOutros;

    public GamificationRequest() {
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public double getMediaFinal() {
        return mediaFinal;
    }

    public void setMediaFinal(double mediaFinal) {
        this.mediaFinal = mediaFinal;
    }

    public boolean isAjudouOutros() {
        return ajudouOutros;
    }

    public void setAjudouOutros(boolean ajudouOutros) {
        this.ajudouOutros = ajudouOutros;
    }
}
