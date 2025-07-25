pipeline {
    agent any

    tools {
        maven 'Maven 3.8.8'      // configured in Jenkins
        jdk 'JDK11'              // configured in Jenkins
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
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_AUTH_TOKEN')]) {
                    withSonarQubeEnv('sonar') {
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

        stage('Docker Build & Deploy') {
            steps {
                script {
                    sh '''
                        echo "Building Docker image..."
                        docker build -t sonar-demo-app .

                        echo "Stopping old container if running..."
                        docker stop sonar-demo-app || true
                        docker rm sonar-demo-app || true

                        echo "Running new container on port 8081..."
                        docker run -d --name sonar-demo-app -p 8081:8081 sonar-demo-app
                    '''
                }
            }
        }
    }
}
