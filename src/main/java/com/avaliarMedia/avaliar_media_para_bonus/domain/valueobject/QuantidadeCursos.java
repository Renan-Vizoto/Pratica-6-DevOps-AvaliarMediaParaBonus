package com.avaliarMedia.avaliar_media_para_bonus.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Value Object que representa a quantidade de cursos bônus.
 * Imutável e sem identidade própria.
 * 
 * Importância dos recursos Lombok:
 * - @Getter: Elimina necessidade de escrever métodos getters manualmente
 * - @EqualsAndHashCode: Permite comparação correta entre objetos (importante para collections)
 * - @ToString: Gera representação textual útil para logs e debug
 * - @AllArgsConstructor: Cria construtor com todos os campos para imutabilidade
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class QuantidadeCursos {
    
    private final Integer quantidade;
    
    /**
     * Cria quantidade de cursos bônus baseado na média
     */
    public static QuantidadeCursos calcular(Double media) {
        if (media != null && media > 7.0) {
            return new QuantidadeCursos(3);
        }
        return new QuantidadeCursos(0);
    }
    
    /**
     * Verifica se há bônus disponível
     */
    public boolean temBonus() {
        return quantidade != null && quantidade > 0;
    }
}

