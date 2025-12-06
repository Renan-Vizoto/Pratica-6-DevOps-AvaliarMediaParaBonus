package ac2_project.example.ac2_ca.infrastructure.persistence;

import ac2_project.example.ac2_ca.domain.model.ProgressoDeCursos;
import ac2_project.example.ac2_ca.domain.port.ProgressoRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryProgressoRepository implements ProgressoRepository {

    private final Map<Long, ProgressoDeCursos> db = new ConcurrentHashMap<>();

    @Override
    public Optional<ProgressoDeCursos> findByAlunoId(Long alunoId) {
        return Optional.ofNullable(db.get(alunoId));
    }

    @Override
    public ProgressoDeCursos save(ProgressoDeCursos progresso) {
        db.put(progresso.getAlunoId(), progresso);
        return progresso;
    }
}
