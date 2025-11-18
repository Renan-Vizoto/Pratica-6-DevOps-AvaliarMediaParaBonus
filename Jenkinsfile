pipeline {
    agent any

    stages {
        stage('Pre-Build: Limpeza do Workspace') {
            steps {
                echo 'Iniciando a limpeza do workspace...'
                cleanWs()
                echo 'Workspace limpo.'
            }
        }

        stage('Checkout: Obter Código-Fonte') {
            steps {
                echo 'Baixando o código do repositório Git...'
                checkout scm
                echo 'Código baixado com sucesso.'
            }
        }

        stage('Build: Compilar e Testar o Projeto') {
            steps {
                echo 'Iniciando a compilação e execução dos testes com o Maven Wrapper...'
                script {
                    if (isUnix()) {
                        sh 'chmod +x ./mvnw'
                        sh './mvnw clean install'
                    } else {
                        bat 'mvnw.cmd clean install'
                    }
                }
            }
        }

        stage('Post-Build: Publicar Relatórios') {
            steps {
                echo 'Coletando e publicando os resultados...'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'

                    jacoco(
                        execPattern: 'target/jacoco.exec',
                        classPattern: 'target/classes',
                        sourcePattern: 'src/main/java',
                        changeBuildStatus: true,
                        minimumLineCoverage: '70',
                        minimumBranchCoverage: '70',
                        minimumInstructionCoverage: '70'
                    )

                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
    }
}
