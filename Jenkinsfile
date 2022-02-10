void setBuildStatus(String message, String state) {
  step([
      $class: "GitHubCommitStatusSetter",
      reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/RemoteBoatX/-its_a_test-.git"],
      contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
      errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
      statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
  ]);
}

pipeline {
    agent any

    tools {
        maven 'Maven 3.8.3'
        jdk 'jdk11'
    }

    stages {
        stage('Build') {
            steps {
                script{
                    sh 'mvn clean package -Dmaven.test.skip=true'
                }
            }

            post {
                success {
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
      
        stage('Test') {
            steps {
                script{
                    sh 'mvn test'
                }
            }

            post {
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }

        stage('Deploy'){
            steps{
                script{
                    sh 'docker build -t moc-server .'
                    sh 'docker run --rm -p 8080:8080 moc-server'
                }
            }
        }
    }

    post {
        success {
            setBuildStatus("Build succeeded", "SUCCESS");
        }
        failure {
            setBuildStatus("Build failed", "FAILURE");
        }
    }
 }
