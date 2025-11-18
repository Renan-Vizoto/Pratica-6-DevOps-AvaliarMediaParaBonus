package com.avaliarMedia.avaliar_media_para_bonus.presentation.controller;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.BonusResponseDTO;
import com.avaliarMedia.avaliar_media_para_bonus.application.service.BonusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para calcular bônus de cursos.
 * Endpoint principal da aplicação - calcula bônus baseado na média do aluno.
 */
@RestController
@RequestMapping("/api/bonus")
@RequiredArgsConstructor
@Tag(name = "Bônus", description = "Endpoints para cálculo de bônus de cursos")
public class BonusController {
    
    private final BonusService bonusService;
    
    @GetMapping("/aluno/{alunoId}")
    @Operation(
        summary = "Calcular bônus de cursos",
        description = "Calcula a quantidade de cursos bônus baseado na média final do aluno. " +
                      "Se média > 7.0, retorna 3 cursos bônus. Caso contrário, retorna 0."
    )
    public ResponseEntity<BonusResponseDTO> calcularBonus(@PathVariable Long alunoId) {
        BonusResponseDTO bonus = bonusService.calcularBonus(alunoId);
        return ResponseEntity.ok(bonus);
    }
}

