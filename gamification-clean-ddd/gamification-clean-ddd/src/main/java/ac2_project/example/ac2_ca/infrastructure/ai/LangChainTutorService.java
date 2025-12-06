package ac2_project.example.ac2_ca.infrastructure.ai;

import ac2_project.example.ac2_ca.domain.model.Aluno;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.stereotype.Service;

@Service
public class LangChainTutorService {

    private final ChatLanguageModel model;

    public LangChainTutorService(ChatLanguageModel model) {
        this.model = model;
    }

    public String sugerirProximosCursos(Aluno aluno) {
        String prompt = "Você é um tutor de educação continuada. " +
                "O aluno se chama " + aluno.getNome() + " e está no plano " + aluno.getPlano().name() + ". " +
                "Sugira próximos cursos e estratégias de estudo em 3 tópicos.";
        return model.generate(prompt);
    }
}
