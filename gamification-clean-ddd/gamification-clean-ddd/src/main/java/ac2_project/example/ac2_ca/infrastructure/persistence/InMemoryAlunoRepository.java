package ac2_project.example.ac2_ca.infrastructure.persistence;

import ac2_project.example.ac2_ca.domain.model.Aluno;
import ac2_project.example.ac2_ca.domain.model.PlanoAssinatura;
import ac2_project.example.ac2_ca.domain.port.AlunoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryAlunoRepository implements AlunoRepository {

    private final Map<Long, Aluno> db = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        db.put(1L, new Aluno(1L, "Aluno Demo", PlanoAssinatura.BASIC));
    }

    @Override
    public Optional<Aluno> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public Aluno save(Aluno aluno) {
        if (aluno.getId() == null) {
            aluno.setId((long) (db.size() + 1));
        }
        db.put(aluno.getId(), aluno);
        return aluno;
    }

    // Método novo — usado nos testes para limpar o banco em memória
    public void clear() {
        db.clear();
    }
}
