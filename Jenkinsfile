#!/usr/bin/env groovy

def packageArtifact(String projectName) {

	stage('Build') {
		sh "./gradlew --no-daemon :" + projectName + ":build"
	}
	stage('Package Docker Image') {
		sh "rm -rf target"
		sh "mkdir -p target"
		sh "cp -R " + projectName + "/Dockerfile target/"
		sh "cp -R " + projectName + "/build/libs/*.jar target/"
		dockerImage = docker.build("springdocdemos/" + projectName, 'target')
	}
	stage('Deploy Docker Image') {
		docker.withRegistry('https://registry.hub.docker.com', 'docker-login') {
			dockerImage.push 'latest'
		}
	}
}

node {
	stage('checkout') {
		deleteDir()
	}
	stage('checkout') {
		checkout scm
	}
	stage('Clean') {
		sh "./gradlew clean --no-daemon"
	}
	packageArtifact('springdoc-openapi-spring-boot-2-webmvc')
	packageArtifact('springdoc-openapi-spring-boot-2-webflux')
	packageArtifact('springdoc-openapi-spring-boot-2-webflux-functional')
	packageArtifact('springdoc-openapi-spring-boot-1')
	packageArtifact('sample-springdoc-openapi-hateoas-service')
}
