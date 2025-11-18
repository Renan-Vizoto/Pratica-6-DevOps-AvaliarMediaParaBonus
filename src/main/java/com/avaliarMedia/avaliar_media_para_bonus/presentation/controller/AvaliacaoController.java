package com.avaliarMedia.avaliar_media_para_bonus.presentation.controller;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.AvaliacaoDTO;
import com.avaliarMedia.avaliar_media_para_bonus.application.service.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para gerenciar Avaliações.
 * Expõe endpoints para criar e consultar avaliações.
 */
@RestController
@RequestMapping("/api/avaliacoes")
@RequiredArgsConstructor
@Tag(name = "Avaliações", description = "Endpoints para gerenciamento de avaliações")
public class AvaliacaoController {
    
    private final AvaliacaoService avaliacaoService;
    
    @PostMapping
    @Operation(summary = "Criar nova avaliação", description = "Cria uma avaliação para um aluno e calcula a média final")
    public ResponseEntity<AvaliacaoDTO> criar(@Valid @RequestBody AvaliacaoDTO dto) {
        AvaliacaoDTO criada = avaliacaoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação por ID", description = "Retorna uma avaliação pelo seu ID")
    public ResponseEntity<AvaliacaoDTO> buscarPorId(@PathVariable Long id) {
        AvaliacaoDTO avaliacao = avaliacaoService.buscarPorId(id);
        return ResponseEntity.ok(avaliacao);
    }
    
    @GetMapping("/aluno/{alunoId}")
    @Operation(summary = "Buscar avaliação por aluno", description = "Retorna a avaliação de um aluno específico")
    public ResponseEntity<AvaliacaoDTO> buscarPorAlunoId(@PathVariable Long alunoId) {
        AvaliacaoDTO avaliacao = avaliacaoService.buscarPorAlunoId(alunoId);
        return ResponseEntity.ok(avaliacao);
    }
}

