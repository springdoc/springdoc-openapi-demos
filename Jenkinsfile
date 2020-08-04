def agentWorkspace
def pushToRegitry = {
  dockerImage.push()
  dockerImage.push("${env.BUILD_NUMBER}")
}
pipeline {
  agent any
  stages {
    stage('do everything in docker') {
      agent {
        docker {
          image 'gradle:6.5.1-jdk8'
          args '-v $HOME/.gradle:/root/.gradle'
        }
      }
      stages {
        stage('Test') {
          steps {
            sh './gradlew --no-daemon clean'
          }
        }
        stage('Build') {
          steps { 
            script {
            agentWorkspace = "${env.WORKSPACE}"
            }
            sh './gradlew --no-daemon :springdoc-openapi-spring-boot-2-webmvc:build'
            sh "mkdir -p target"
            sh "cp -R springdoc-openapi-spring-boot-2-webmvc/Dockerfile target/"
            sh "cp -R springdoc-openapi-spring-boot-2-webmvc/build/libs/*.jar target/"
          }
        }
      }
    }
    stage('build docker') {
      steps {
        script {
          dockerImage = docker.build('springdocdemos/springdoc-openapi-spring-boot-2-webmvc', "--build-arg JAR_FILE=$agentWorkspace/target/*.jar $agentWorkspace/target")
        }
      }
    }
    stage('Deploy Image') {
      steps {
        script {
          docker.withRegistry('https://registry.hub.docker.com', 'docker-login', pushToRegitry)
        }
      }
    }
  }
}
