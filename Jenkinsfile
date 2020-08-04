#!/usr/bin/env groovy

def packageArtifact(String projectName) {
	stage('Build') {
		sh "./gradlew --no-daemon :"+sprojectName+":build"
	}
	stage('Package Docker Image') {
		sh "rm -rf target"
		sh "mkdir -p target"
		sh "cp -R springdoc-openapi-spring-boot-2-webmvc/Dockerfile target/"
		sh "cp -R springdoc-openapi-spring-boot-2-webmvc/build/libs/*.jar target/"
		dockerImage = docker.build('springdocdemos/springdoc-openapi-spring-boot-2-webmvc', "--build-arg JAR_FILE=target/*.jar target")
	}
}

node {
	packageArtifact('springdoc-openapi-spring-boot-2-webmvc')

	stage('Clean') {
		sh './gradlew --no-daemon clean'
	}
	stage('Deploy Docker Image') {
		docker.withRegistry('https://registry.hub.docker.com', 'docker-login') {
			dockerImage.push 'latest'
		}
	}
}
