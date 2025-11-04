package com.avaliarMedia.avaliar_media_para_bonus.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger/OpenAPI para documentação da API.
 * 
 * @Configuration: Marca como classe de configuração Spring
 * @Bean: Define métodos que retornam objetos gerenciados pelo Spring
 * 
 * Acesse a documentação em: http://localhost:8080/swagger-ui.html
 * JSON/OpenAPI em: http://localhost:8080/v3/api-docs
 */
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Avaliar Média para Bônus")
                        .version("1.0.0")
                        .description("API REST para avaliação de média de alunos e cálculo de cursos bônus. " +
                                   "Se a média do aluno for maior que 7.0, ele recebe 3 cursos bônus. " +
                                   "Caso contrário, não recebe nenhum bônus.")
                        .contact(new Contact()
                                .name("Equipe DevOpsQA")
                                .email("devops@facens.edu.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}

