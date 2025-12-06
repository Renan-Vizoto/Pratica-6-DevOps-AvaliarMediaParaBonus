package ac2_project.example.ac2_ca.domain.model;

public class ProgressoDeCursos {

    private Long alunoId;
    private int cursosConcluidos;
    private int cursosDisponiveis;

    public ProgressoDeCursos() {
    }

    public ProgressoDeCursos(Long alunoId, int cursosConcluidos, int cursosDisponiveis) {
        this.alunoId = alunoId;
        this.cursosConcluidos = cursosConcluidos;
        this.cursosDisponiveis = cursosDisponiveis;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public int getCursosConcluidos() {
        return cursosConcluidos;
    }

    public void setCursosConcluidos(int cursosConcluidos) {
        this.cursosConcluidos = cursosConcluidos;
    }

    public int getCursosDisponiveis() {
        return cursosDisponiveis;
    }

    public void setCursosDisponiveis(int cursosDisponiveis) {
        this.cursosDisponiveis = cursosDisponiveis;
    }

    public void registrarConclusao() {
        this.cursosConcluidos++;
    }

    public void adicionarCursosDisponiveis(int quantidade) {
        this.cursosDisponiveis += quantidade;
    }
}
