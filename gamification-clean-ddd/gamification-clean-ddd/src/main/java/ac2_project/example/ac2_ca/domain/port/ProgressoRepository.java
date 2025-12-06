package ac2_project.example.ac2_ca.domain.port;

import ac2_project.example.ac2_ca.domain.model.ProgressoDeCursos;

import java.util.Optional;

public interface ProgressoRepository {

    Optional<ProgressoDeCursos> findByAlunoId(Long alunoId);

    ProgressoDeCursos save(ProgressoDeCursos progresso);
}
