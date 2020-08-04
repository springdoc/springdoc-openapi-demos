pipeline {
	agent any
	stages {
		stage('Clean') {
			steps {
				sh './gradlew --no-daemon clean'
			}
		}
		stage('Build') {
			steps {
				sh './gradlew --no-daemon :springdoc-openapi-spring-boot-2-webmvc:build'
			}
		}
		stage('Package Docker Image') {
			steps {
				script {
					sh "rm -rf target"
					sh "mkdir -p target"
					sh "cp -R springdoc-openapi-spring-boot-2-webmvc/Dockerfile target/"
					sh "cp -R springdoc-openapi-spring-boot-2-webmvc/build/libs/*.jar target/"
					dockerImage = docker.build('springdocdemos/springdoc-openapi-spring-boot-2-webmvc', "--build-arg JAR_FILE=target/*.jar target")
				}
			}
		}
		stage('Deploy Docker Image') {
			steps {
				script {
					docker.withRegistry('https://registry.hub.docker.com', 'docker-login', dockerImage.push())
				}
			}
		}
	}
}
