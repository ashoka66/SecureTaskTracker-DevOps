pipeline {
    agent any

    tools {
        jdk "jdk-17"
        maven "Maven-3.9.9"
    }

    environment {
        SONAR_TOKEN = credentials('sonar-token')
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ashoka66/SecureTaskTracker-DevOps.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('Local-Sonar') {
                    bat "sonar-scanner.bat -Dsonar.projectKey=SecurityTaskTracker -Dsonar.sources=src/main/java -Dsonar.host.url=http://localhost:9000 -Dsonar.login=%SONAR_TOKEN%"
                }
            }
        }

        stage("Quality Gate") {
            steps {
                timeout(time: 3, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

    }
}
