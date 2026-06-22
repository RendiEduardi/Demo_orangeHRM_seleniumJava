pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/RendiEduardi/Demo_orangeHRM_seleniumJava.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean test'
            }
        }
    }
}