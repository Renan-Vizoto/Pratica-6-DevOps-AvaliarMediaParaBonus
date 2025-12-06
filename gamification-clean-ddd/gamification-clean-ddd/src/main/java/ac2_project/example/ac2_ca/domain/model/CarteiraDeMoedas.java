package ac2_project.example.ac2_ca.domain.model;

public class CarteiraDeMoedas {

    private Long alunoId;
    private int saldo;

    public CarteiraDeMoedas() {
    }

    public CarteiraDeMoedas(Long alunoId, int saldo) {
        this.alunoId = alunoId;
        this.saldo = saldo;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public void adicionar(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade negativa");
        }
        this.saldo += quantidade;
    }
}
