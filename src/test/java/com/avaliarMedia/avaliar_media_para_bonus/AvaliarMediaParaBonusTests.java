package com.avaliarMedia.avaliar_media_para_bonus;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.avaliarMedia.avaliar_media_para_bonus.service.MediaService;
import com.avaliarMedia.avaliar_media_para_bonus.model.Bonus;

class AvaliarMediaParaBonusTests {

    private MediaService mediaService;
    
    @BeforeEach
    void setUp() {
        mediaService = new MediaService();
    }

    @Test
    void testMediaMenosSete() {
        // Arrange
        double media = 6.0;
        
        // Action
        Bonus bonus = mediaService.calcularBonus(media);
        
        // Assert
        assertAll("Verificações para média menor que 7",
            () -> assertFalse(bonus.isElegivel(), "Não deve ter direito ao bônus"),
            () -> assertEquals(0, bonus.getQuantidadeCursos(), "Deve receber 0 cursos bônus"),
            () -> assertFalse(bonus.temBonus(), "Não deve ter bônus"),
            () -> assertTrue(bonus.getDescricao().contains("não é suficiente"), "Descrição deve indicar insuficiência")
        );
    }
    
    @Test
    void testMediaIgualSete() {
        // Arrange
        double media = 7.0;
        
        // Action
        Bonus bonus = mediaService.calcularBonus(media);
        
        // Assert
        assertAll("Verificações para média igual a 7",
            () -> assertFalse(bonus.isElegivel(), "Não deve ter direito ao bônus"),
            () -> assertEquals(0, bonus.getQuantidadeCursos(), "Deve receber 0 cursos bônus"),
            () -> assertFalse(bonus.temBonus(), "Não deve ter bônus"),
            () -> assertEquals(7.0, bonus.getMediaUtilizada(), "Deve armazenar a média correta")
        );
    }
    
    @Test
    void testMediaMaiorSete() {
        // Arrange
        double media = 8.5;
        
        // Action
        Bonus bonus = mediaService.calcularBonus(media);
        
        // Assert
        assertAll("Verificações para média maior que 7",
            () -> assertTrue(bonus.isElegivel(), "Deve ter direito ao bônus"),
            () -> assertEquals(3, bonus.getQuantidadeCursos(), "Deve receber 3 cursos bônus"),
            () -> assertTrue(bonus.temBonus(), "Deve ter bônus"),
            () -> assertTrue(bonus.getDescricao().contains("Parabéns"), "Descrição deve parabenizar"),
            () -> assertEquals(8.5, bonus.getMediaUtilizada(), "Deve armazenar a média correta")
        );
    }
    
    
    @Test
    void testDescricaoBonus() {
        // Arrange & Action
        String descricaoSemBonus = mediaService.obterDescricaoBonus(6.0);
        String descricaoComBonus = mediaService.obterDescricaoBonus(8.0);
        
        // Assert
        assertAll("Verificações de descrição",
            () -> assertTrue(descricaoSemBonus.contains("não é suficiente")),
            () -> assertTrue(descricaoComBonus.contains("Parabéns")),
            () -> assertTrue(descricaoComBonus.contains("3 cursos"))
        );
    }
    
    @Test
    void testAvaliarMediaParaBonus() {
        // Testa o método avaliarMediaParaBonus diretamente
        assertAll("Teste direto do método avaliarMediaParaBonus",
            () -> assertFalse(mediaService.avaliarMediaParaBonus(6.0), "Média 6.0 não deve qualificar"),
            () -> assertFalse(mediaService.avaliarMediaParaBonus(7.0), "Média 7.0 não deve qualificar"),
            () -> assertTrue(mediaService.avaliarMediaParaBonus(8.0), "Média 8.0 deve qualificar")
        );
    }
    
    @Test
    void testDarBonus() {
        // Testa o método darBonus diretamente
        assertAll("Teste direto do método darBonus",
            () -> assertEquals(0, mediaService.darBonus(6.0), "Média 6.0 deve retornar 0 cursos"),
            () -> assertEquals(0, mediaService.darBonus(7.0), "Média 7.0 deve retornar 0 cursos"),
            () -> assertEquals(3, mediaService.darBonus(8.0), "Média 8.0 deve retornar 3 cursos")
        );
    }
    
    @Test
    void testTemDireitoAoBonus() {
        // Testa o método temDireitoAoBonus diretamente
        assertAll("Teste direto do método temDireitoAoBonus",
            () -> assertFalse(mediaService.temDireitoAoBonus(6.0), "Média 6.0 não deve ter direito"),
            () -> assertFalse(mediaService.temDireitoAoBonus(7.0), "Média 7.0 não deve ter direito"),
            () -> assertTrue(mediaService.temDireitoAoBonus(8.0), "Média 8.0 deve ter direito")
        );
    }
}
