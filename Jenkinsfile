pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/RendiEduardi/Demo_orangeHRM_seleniumJava.git'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn clean test -Dheadless=true'
            }
        }

    }

    post {
        always {
            allure([
                includeProperties: false,
                jdk: '',
                results: [[path: 'target/allure-results']]
            ])
        }
    }
}
