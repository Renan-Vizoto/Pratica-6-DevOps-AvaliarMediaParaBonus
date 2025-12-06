package ac2_project.example.ac2_ca.interfaceadapters.rest;

import ac2_project.example.ac2_ca.application.dto.GamificationRequest;
import ac2_project.example.ac2_ca.application.dto.GamificationResponse;
import ac2_project.example.ac2_ca.application.usecase.RegistrarConclusaoCursoUseCase;
import ac2_project.example.ac2_ca.domain.model.Aluno;
import ac2_project.example.ac2_ca.domain.port.AlunoRepository;
import ac2_project.example.ac2_ca.infrastructure.ai.LangChainTutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gamification")
public class GamificationController {

    private final RegistrarConclusaoCursoUseCase registrarConclusaoCursoUseCase;
    private final AlunoRepository alunoRepository;
    private final LangChainTutorService tutorService;

    public GamificationController(RegistrarConclusaoCursoUseCase registrarConclusaoCursoUseCase,
                                  AlunoRepository alunoRepository,
                                  LangChainTutorService tutorService) {
        this.registrarConclusaoCursoUseCase = registrarConclusaoCursoUseCase;
        this.alunoRepository = alunoRepository;
        this.tutorService = tutorService;
    }

    @PostMapping("/courses/completion")
    public ResponseEntity<GamificationResponse> registrarConclusao(@RequestBody GamificationRequest request) {
        GamificationResponse response = registrarConclusaoCursoUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{alunoId}/ai/recommendations")
    public ResponseEntity<String> recomendar(@PathVariable("alunoId") Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        String recomendacao = tutorService.sugerirProximosCursos(aluno);
        return ResponseEntity.ok(recomendacao);
    }
}
