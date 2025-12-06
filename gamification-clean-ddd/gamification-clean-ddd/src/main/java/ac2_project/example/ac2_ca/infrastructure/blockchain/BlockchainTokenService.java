package ac2_project.example.ac2_ca.infrastructure.blockchain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BlockchainTokenService {

    private static final Logger log = LoggerFactory.getLogger(BlockchainTokenService.class);

    public void registrarMoedasEmBlockchain(Long alunoId, int quantidade) {
        log.info("[BLOCKCHAIN] Registrando {} moedas para o aluno {} em blockchain.", quantidade, alunoId);
    }
}
