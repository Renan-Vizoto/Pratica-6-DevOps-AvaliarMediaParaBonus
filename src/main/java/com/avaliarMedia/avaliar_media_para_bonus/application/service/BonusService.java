package com.avaliarMedia.avaliar_media_para_bonus.application.service;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.BonusResponseDTO;
import com.avaliarMedia.avaliar_media_para_bonus.domain.valueobject.Media;
import com.avaliarMedia.avaliar_media_para_bonus.domain.valueobject.QuantidadeCursos;
import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Aluno;
import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Avaliacao;
import com.avaliarMedia.avaliar_media_para_bonus.domain.repository.AlunoRepository;
import com.avaliarMedia.avaliar_media_para_bonus.domain.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço para calcular e verificar bônus de cursos.
 * Implementa a regra de negócio: média > 7.0 = 3 cursos bônus, caso contrário = 0.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BonusService {
    
    private final AlunoRepository alunoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    
    /**
     * Calcula o bônus baseado na média do aluno.
     * 
     * @param alunoId ID do aluno
     * @return BonusResponseDTO com informações sobre elegibilidade e quantidade de bônus
     */
    public BonusResponseDTO calcularBonus(Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + alunoId));
        
        Avaliacao avaliacao = avaliacaoRepository.findByAlunoId(alunoId)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada para aluno ID: " + alunoId));
        
        if (avaliacao.getMediaFinal() == null) {
            throw new RuntimeException("Média final não calculada para aluno ID: " + alunoId);
        }
        
        // Usa Value Objects para calcular bônus
        Media media = new Media(avaliacao.getMediaFinal());
        QuantidadeCursos quantidade = QuantidadeCursos.calcular(media.getValor());
        
        return BonusResponseDTO.builder()
                .alunoId(aluno.getId())
                .nomeAluno(aluno.getNome())
                .mediaFinal(media.getValor())
                .elegivel(media.isElegivelParaBonus())
                .quantidadeCursosBonus(quantidade.getQuantidade())
                .descricao(gerarDescricao(media.getValor(), quantidade.getQuantidade()))
                .build();
    }
    
    private String gerarDescricao(Double media, Integer quantidade) {
        if (quantidade > 0) {
            return String.format("Parabéns! Com média %.1f, você ganhou %d cursos bônus!", media, quantidade);
        } else {
            return String.format("Média %.1f não é suficiente para bônus. Necessário média superior a 7.0", media);
        }
    }
}

