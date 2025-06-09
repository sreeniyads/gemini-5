// user-service/Jenkinsfile

pipeline {
    // Tell Jenkins to run this on any available agent/node
    agent any

    // Define the tools we need. Jenkins will ensure they are available.
    tools {
        maven 'M3' // Assumes you have a Maven tool named 'M3' configured in Jenkins Global Tool Configuration
    }

    stages {
        // Stage 1: Checkout the code from Git
        stage('Checkout') {
            steps {
                echo 'Checking out the code...'
                // This command is automatically provided by the Jenkins Git plugin
                checkout scm
            }
        }

        // Stage 2: Build the application with Maven
        stage('Build') {
            steps {
                echo 'Building the application...'
                // On Windows agents, use bat. On Linux, use sh.
                sh 'mvn clean package -DskipTests' // Skipping tests for now to focus on the pipeline flow
            }
        }

        // Stage 3: Run SonarQube Analysis
        stage('SonarQube Analysis') {
            steps {
                // This 'withSonarQubeEnv' block is provided by the SonarQube Scanner plugin
                withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN_CRED')]) {
                            sh "mvn sonar:sonar -Dsonar.token=${SONAR_TOKEN_CRED}"
                }
            }
        }

        // Stage 4: Wait for SonarQube Quality Gate result
        stage("Quality Gate") {
            steps {
                // This command waits for the SonarQube analysis to complete and then checks the result.
                // If the Quality Gate fails, this step will fail the entire pipeline.
                waitForQualityGate abortPipeline: true
            }
        }

        // Stage 5: Build the Docker Image
        stage('Build Docker Image') {
            steps {
                echo 'Building the Docker image...'
                // This shell command uses the Docker daemon that we shared with the Jenkins container
                sh 'docker build -t user-service:latest .'
            }
        }
    }
}