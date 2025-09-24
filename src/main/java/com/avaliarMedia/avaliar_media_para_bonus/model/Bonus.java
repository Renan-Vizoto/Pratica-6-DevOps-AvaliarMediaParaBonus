package com.avaliarMedia.avaliar_media_para_bonus.model;

public class Bonus {
    private final int quantidadeCursos;
    private final boolean elegivel;
    private final String descricao;
    private final double mediaUtilizada;
    
    public Bonus(double media) {
        this.mediaUtilizada = media;
        this.elegivel = media > 7.0;
        this.quantidadeCursos = elegivel ? 3 : 0;
        this.descricao = gerarDescricao();
    }
    
    private String gerarDescricao() {
        if (elegivel) {
            return String.format("Parabéns! Com média %.1f, você ganhou %d cursos bônus!", 
                               mediaUtilizada, quantidadeCursos);
        } else {
            return String.format("Média %.1f não é suficiente para bônus. Necessário média superior a 7.0", 
                               mediaUtilizada);
        }
    }
    
    public int getQuantidadeCursos() {
        return quantidadeCursos;
    }
    
    public boolean isElegivel() {
        return elegivel;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public double getMediaUtilizada() {
        return mediaUtilizada;
    }
    
    public boolean temBonus() {
        return quantidadeCursos > 0;
    }
}
