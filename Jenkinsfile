pipeline {
    agent any

    tools {
        maven "Maven-3.9.9"
        jdk "jdk-17"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONARQUBE = credentials('sonarqube-token')
            }
            steps {
                withSonarQubeEnv('My-SonarQube') {
                    bat """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=SecurityTaskTracker \
                        -Dsonar.projectName=SecurityTaskTracker \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.login=$SONARQUBE
                    """
                }
            }
        }

        stage("Quality Gate") {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package'
            }
        }
    }
}
