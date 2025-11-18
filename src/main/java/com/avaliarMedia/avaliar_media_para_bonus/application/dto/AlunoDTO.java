package com.avaliarMedia.avaliar_media_para_bonus.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para Aluno.
 * Usado para transferir dados entre camadas sem expor entidades.
 * 
 * @Data: Gera getters, setters, toString, equals e hashCode (Lombok)
 * @Builder: Facilita criação de objetos
 * @NoArgsConstructor/@AllArgsConstructor: Construtores para serialização JSON
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO {
    
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    private String email;
}

