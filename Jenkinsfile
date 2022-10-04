pipeline {
    agent { label 'master' }
      stages {
        stage('Initialize') {
      steps {
        echo 'log version info'  
        sh 'mvn --version'
      }
    }
        stage('Build') {
            steps {
                echo 'Building'
                git 'https://github.com/vwocu3/mvn-prometheus-plugin.git' 
                sh 'mvn clean install'
                sh 'mvn hpi:hpi'                
            }
            post {
                always {
                    archiveArtifacts artifacts: 'target/**.hpi,target/**.jpi,target/**.jar', fingerprint: true
                    junit(
                  allowEmptyResults: true,
                  testResults: '**/*.xml'
                )  
                }
    }              
        }
        stage('Test') {
            steps {
                echo 'Testing'
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying'
            }
        }        
  }
}
