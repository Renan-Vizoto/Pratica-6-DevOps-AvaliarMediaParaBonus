package com.avaliarMedia.avaliar_media_para_bonus.service;

import com.avaliarMedia.avaliar_media_para_bonus.model.Bonus;

public class MediaService {
    
    private static final double MEDIA_MINIMA_BONUS = 7.0;
    
    public boolean avaliarMediaParaBonus(double media) {
        return media > MEDIA_MINIMA_BONUS;
    }
    
    public int darBonus(double media) {
        return calcularBonus(media).getQuantidadeCursos();
    }
    
    public Bonus calcularBonus(double media) {
        return new Bonus(media);
    }
    
    public String obterDescricaoBonus(double media) {
        return calcularBonus(media).getDescricao();
    }
    
    public boolean temDireitoAoBonus(double media) {
        return calcularBonus(media).isElegivel();
    }
}
