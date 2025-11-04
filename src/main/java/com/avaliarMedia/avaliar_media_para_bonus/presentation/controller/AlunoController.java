package com.avaliarMedia.avaliar_media_para_bonus.presentation.controller;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.AlunoDTO;
import com.avaliarMedia.avaliar_media_para_bonus.application.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para gerenciar Alunos.
 * Expõe endpoints HTTP para operações CRUD.
 * 
 * @RestController: Combina @Controller + @ResponseBody
 * @RequestMapping: Define path base da API
 * @RequiredArgsConstructor: Injeta dependências via construtor
 */
@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
@Tag(name = "Alunos", description = "Endpoints para gerenciamento de alunos")
public class AlunoController {
    
    private final AlunoService alunoService;
    
    @PostMapping
    @Operation(summary = "Criar novo aluno", description = "Cria um novo aluno no sistema")
    public ResponseEntity<AlunoDTO> criar(@Valid @RequestBody AlunoDTO dto) {
        AlunoDTO criado = alunoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluno por ID", description = "Retorna um aluno pelo seu ID")
    public ResponseEntity<AlunoDTO> buscarPorId(@PathVariable Long id) {
        AlunoDTO aluno = alunoService.buscarPorId(id);
        return ResponseEntity.ok(aluno);
    }
    
    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar aluno por email", description = "Retorna um aluno pelo seu email")
    public ResponseEntity<AlunoDTO> buscarPorEmail(@PathVariable String email) {
        AlunoDTO aluno = alunoService.buscarPorEmail(email);
        return ResponseEntity.ok(aluno);
    }
}

