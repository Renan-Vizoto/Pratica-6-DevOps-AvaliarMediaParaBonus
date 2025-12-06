package ac2_project.example.ac2_ca.domain.service;

import ac2_project.example.ac2_ca.domain.event.GamificationEvent;
import ac2_project.example.ac2_ca.domain.model.Aluno;
import ac2_project.example.ac2_ca.domain.model.CarteiraDeMoedas;
import ac2_project.example.ac2_ca.domain.model.PlanoAssinatura;
import ac2_project.example.ac2_ca.domain.model.ProgressoDeCursos;
import ac2_project.example.ac2_ca.domain.port.AlunoRepository;
import ac2_project.example.ac2_ca.domain.port.CarteiraRepository;
import ac2_project.example.ac2_ca.domain.port.DomainEventPublisher;
import ac2_project.example.ac2_ca.domain.port.ProgressoRepository;

public class GamificacaoDomainService {

    private final AlunoRepository alunoRepository;
    private final CarteiraRepository carteiraRepository;
    private final ProgressoRepository progressoRepository;
    private final DomainEventPublisher eventPublisher;

    public GamificacaoDomainService(AlunoRepository alunoRepository,
                                    CarteiraRepository carteiraRepository,
                                    ProgressoRepository progressoRepository,
                                    DomainEventPublisher eventPublisher) {
        this.alunoRepository = alunoRepository;
        this.carteiraRepository = carteiraRepository;
        this.progressoRepository = progressoRepository;
        this.eventPublisher = eventPublisher;
    }

    public GamificationEvent registrarConclusaoCurso(Long alunoId,
                                                     String nomeCurso,
                                                     double mediaFinal,
                                                     boolean ajudouOutros) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        ProgressoDeCursos progresso = progressoRepository.findByAlunoId(alunoId)
                .orElse(new ProgressoDeCursos(alunoId, 0, 0));

        CarteiraDeMoedas carteira = carteiraRepository.findByAlunoId(alunoId)
                .orElse(new CarteiraDeMoedas(alunoId, 0));

        if (mediaFinal >= 7.0) {
            progresso.registrarConclusao();
            progresso.adicionarCursosDisponiveis(3);
            carteira.adicionar(1);
        }

        if (ajudouOutros) {
            carteira.adicionar(1);
        }

        if (progresso.getCursosConcluidos() >= 12) {
            aluno.setPlano(PlanoAssinatura.PREMIUM);
        }

        alunoRepository.save(aluno);
        progressoRepository.save(progresso);
        carteiraRepository.save(carteira);

        GamificationEvent event = new GamificationEvent(
                aluno.getId(),
                progresso.getCursosDisponiveis(),
                carteira.getSaldo(),
                aluno.getPlano().name()
        );

        eventPublisher.publish(event);
        return event;
    }
}
