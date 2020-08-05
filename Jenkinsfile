#!/usr/bin/env groovy

def packageArtifact(String projectName) {
	stage('Build') {
		sh "./gradlew clean --no-daemon :" + projectName + ":build"
	}
	stage('Package Docker Image') {
		sh "rm -rf target"
		sh "mkdir -p target"
		sh "cp -R " + projectName + "/Dockerfile target/"
		sh "cp -R " + projectName + "/build/libs/*.jar target/"
		dockerImage = docker.build("springdocdemos/" + projectName, "--build-arg JAR_FILE=target/*.jar target")
	}
	stage('Deploy Docker Image') {
		docker.withRegistry('https://registry.hub.docker.com', 'docker-login') {
			dockerImage.push 'latest'
		}
	}
}

node {
	packageArtifact('springdoc-openapi-spring-boot-2-webmvc')
}
