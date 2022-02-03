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

    stages {
        stage('Build') {
            steps {
               sh 'make'
               archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
      
        stage('Test') {
            steps {
                sh 'make check || true'
                junit '**/target/*.xml'
            }
        }
        stage('Deploy'){
            steps{
                script{
                    if(env.BRANCH_NAME == 'main'){
                        sh 'docker build -t moc-server .'
                        sh 'docker run --rm -p 8080:8080 moc-server'
                    }
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
