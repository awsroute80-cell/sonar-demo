pipeline {
    agent any

    tools {
        maven 'Maven 3.8.8'      // configure Maven in Global Tool Config
        jdk 'JDK11'              // configure JDK in Global Tool Config
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/awsroute80-cell/sonar-demo.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONAR_SCANNER_HOME = tool 'sonar-scanner'
            }
            steps {
                withSonarQubeEnv('sonar') {
                    //Inject SonarQube token saved in Jenkins credentials
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_AUTH_TOKEN')]) {
                        sh '''
                            $SONAR_SCANNER_HOME/bin/sonar-scanner \
                                -Dsonar.projectKey=sonar-demo \
                                -Dsonar.sources=. \
                                -Dsonar.java.binaries=target/classes \
                                -Dsonar.host.url=http://13.219.162.136:9000 \
                                -Dsonar.login=$SONAR_AUTH_TOKEN
                        '''
                    }
                }
            }
        }
    }
}
