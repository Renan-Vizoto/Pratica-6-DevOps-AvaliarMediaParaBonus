package ac2_project.example.ac2_ca.infrastructure.persistence;

import ac2_project.example.ac2_ca.domain.model.CarteiraDeMoedas;
import ac2_project.example.ac2_ca.domain.port.CarteiraRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryCarteiraRepository implements CarteiraRepository {

    private final Map<Long, CarteiraDeMoedas> db = new ConcurrentHashMap<>();

    @Override
    public Optional<CarteiraDeMoedas> findByAlunoId(Long alunoId) {
        return Optional.ofNullable(db.get(alunoId));
    }

    @Override
    public CarteiraDeMoedas save(CarteiraDeMoedas carteira) {
        db.put(carteira.getAlunoId(), carteira);
        return carteira;
    }
}
