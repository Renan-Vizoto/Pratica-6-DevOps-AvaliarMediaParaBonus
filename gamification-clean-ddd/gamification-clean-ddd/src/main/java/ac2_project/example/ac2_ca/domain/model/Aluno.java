package ac2_project.example.ac2_ca.domain.model;

public class Aluno {

    private Long id;
    private String nome;
    private PlanoAssinatura plano;

    public Aluno() {
    }

    public Aluno(Long id, String nome, PlanoAssinatura plano) {
        this.id = id;
        this.nome = nome;
        this.plano = plano;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PlanoAssinatura getPlano() {
        return plano;
    }

    public void setPlano(PlanoAssinatura plano) {
        this.plano = plano;
    }

    public boolean isPremium() {
        return PlanoAssinatura.PREMIUM.equals(this.plano);
    }
}
