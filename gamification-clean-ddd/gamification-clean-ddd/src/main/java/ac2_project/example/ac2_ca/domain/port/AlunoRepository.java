package ac2_project.example.ac2_ca.domain.port;

import ac2_project.example.ac2_ca.domain.model.Aluno;

import java.util.Optional;

public interface AlunoRepository {

    Optional<Aluno> findById(Long id);

    Aluno save(Aluno aluno);
}
