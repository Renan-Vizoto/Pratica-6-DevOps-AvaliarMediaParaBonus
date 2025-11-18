package com.avaliarMedia.avaliar_media_para_bonus.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Value Object que representa uma nota individual.
 * Imutável e sem identidade própria.
 * 
 * Importância dos recursos Lombok:
 * - @Getter: Gera getters automaticamente, mantendo encapsulamento sem código repetitivo
 * - @EqualsAndHashCode: Garante que duas notas com mesmo valor sejam consideradas iguais
 * - @ToString: Facilita visualização em logs e depuração
 * - @AllArgsConstructor: Cria construtor completo para imutabilidade
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Nota {
    
    private final Double valor;
    
    /**
     * Valida se a nota está no intervalo válido (0.0 a 10.0)
     */
    public boolean isValid() {
        return valor != null && valor >= 0.0 && valor <= 10.0;
    }
}

