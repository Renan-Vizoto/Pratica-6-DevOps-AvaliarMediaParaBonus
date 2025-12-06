package ac2_project.example.ac2_ca.config;

import ac2_project.example.ac2_ca.domain.port.AlunoRepository;
import ac2_project.example.ac2_ca.domain.port.CarteiraRepository;
import ac2_project.example.ac2_ca.domain.port.DomainEventPublisher;
import ac2_project.example.ac2_ca.domain.port.ProgressoRepository;
import ac2_project.example.ac2_ca.domain.service.GamificacaoDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public GamificacaoDomainService gamificacaoDomainService(AlunoRepository alunoRepository,
                                                             CarteiraRepository carteiraRepository,
                                                             ProgressoRepository progressoRepository,
                                                             DomainEventPublisher publisher) {
        return new GamificacaoDomainService(alunoRepository, carteiraRepository, progressoRepository, publisher);
    }
}
