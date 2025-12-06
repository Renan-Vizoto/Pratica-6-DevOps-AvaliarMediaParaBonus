package ac2_project.example.ac2_ca.application.usecase;

import ac2_project.example.ac2_ca.application.dto.GamificationRequest;
import ac2_project.example.ac2_ca.application.dto.GamificationResponse;
import ac2_project.example.ac2_ca.domain.event.GamificationEvent;
import ac2_project.example.ac2_ca.domain.service.GamificacaoDomainService;
import org.springframework.stereotype.Service;

@Service
public class RegistrarConclusaoCursoUseCase {

    private final GamificacaoDomainService domainService;

    public RegistrarConclusaoCursoUseCase(GamificacaoDomainService domainService) {
        this.domainService = domainService;
    }

    public GamificationResponse execute(GamificationRequest request) {
        GamificationEvent event = domainService.registrarConclusaoCurso(
                request.getAlunoId(),
                request.getNomeCurso(),
                request.getMediaFinal(),
                request.isAjudouOutros()
        );

        return new GamificationResponse(
                event.getAlunoId(),
                event.getCursosDisponiveis(),
                event.getMoedas(),
                event.getPlanoAssinatura()
        );
    }
}
