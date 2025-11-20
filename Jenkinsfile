pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven-3.9.9'
    }

    stages {
        stage('Build') {
            steps {
                bat "mvn clean install -DskipTests"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('Local-SonarQube') {
                    bat "mvn sonar:sonar"
                }
            }
        }

        stage('Test') {
            steps {
                bat "mvn test"
            }
        }

        stage('Package') {
            steps {
                bat "mvn package"
            }
        }
    }
}
