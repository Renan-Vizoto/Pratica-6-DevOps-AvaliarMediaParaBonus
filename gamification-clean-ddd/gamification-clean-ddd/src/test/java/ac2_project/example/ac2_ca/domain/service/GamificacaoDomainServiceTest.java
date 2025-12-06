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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GamificacaoDomainServiceTest {

    @Mock
    AlunoRepository alunoRepository;

    @Mock
    CarteiraRepository carteiraRepository;

    @Mock
    ProgressoRepository progressoRepository;

    @Mock
    DomainEventPublisher eventPublisher;

    @InjectMocks
    GamificacaoDomainService service;

    @Test
    void deveRegistrarConclusaoDeCursoEGerarEvento() {
        Aluno aluno = new Aluno(1L, "Teste", PlanoAssinatura.BASIC);
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(progressoRepository.findByAlunoId(1L))
                .thenReturn(Optional.of(new ProgressoDeCursos(1L, 11, 0)));
        when(carteiraRepository.findByAlunoId(1L))
                .thenReturn(Optional.of(new CarteiraDeMoedas(1L, 0)));

        GamificationEvent result = service.registrarConclusaoCurso(
                1L, "Curso X", 9.0, true);

        assertThat(aluno.getPlano()).isEqualTo(PlanoAssinatura.PREMIUM);
        assertThat(result.getMoedas()).isEqualTo(2);
        assertThat(result.getCursosDisponiveis()).isEqualTo(3);

        verify(alunoRepository).save(aluno);
        verify(progressoRepository).save(any(ProgressoDeCursos.class));
        verify(carteiraRepository).save(any(CarteiraDeMoedas.class));

        ArgumentCaptor<GamificationEvent> captor = ArgumentCaptor.forClass(GamificationEvent.class);
        verify(eventPublisher).publish(captor.capture());
        GamificationEvent published = captor.getValue();
        assertThat(published.getAlunoId()).isEqualTo(1L);
    }
}
