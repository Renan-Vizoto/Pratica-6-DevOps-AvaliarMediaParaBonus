package ac2_project.example.ac2_ca.domain.port;

import ac2_project.example.ac2_ca.domain.model.CarteiraDeMoedas;

import java.util.Optional;

public interface CarteiraRepository {

    Optional<CarteiraDeMoedas> findByAlunoId(Long alunoId);

    CarteiraDeMoedas save(CarteiraDeMoedas carteira);
}
