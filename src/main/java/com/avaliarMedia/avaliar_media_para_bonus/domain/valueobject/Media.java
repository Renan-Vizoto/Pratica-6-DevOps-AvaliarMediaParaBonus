package com.avaliarMedia.avaliar_media_para_bonus.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Value Object que representa a média final do aluno.
 * Imutável e sem identidade própria.
 * 
 * Importância dos recursos Lombok:
 * - @Getter: Gera automaticamente getters, eliminando código boilerplate
 * - @EqualsAndHashCode: Implementa equals() e hashCode() corretamente para comparação
 * - @ToString: Gera toString() para facilitar debug e logs
 * - @AllArgsConstructor: Cria construtor com todos os campos (útil para Value Objects imutáveis)
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Media {
    
    private final Double valor;
    
    /**
     * Verifica se a média é suficiente para receber bônus (> 7.0)
     */
    public boolean isElegivelParaBonus() {
        return valor != null && valor > 7.0;
    }
    
    /**
     * Valida se a média está no intervalo válido (0.0 a 10.0)
     */
    public boolean isValid() {
        return valor != null && valor >= 0.0 && valor <= 10.0;
    }
}

