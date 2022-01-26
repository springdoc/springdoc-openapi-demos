#!/usr/bin/env groovy

tools {
	jdk 'java-17'
}
node {
	stage('checkout') {
		deleteDir()
	}
	stage('checkout') {
		checkout scm
	}
	stage('Clean') {
		withMaven(maven: 'maven-3.8.4') {
			sh "mvn clean -T100"
		}
	}
	stage('Package') {
		withMaven(maven: 'maven-3.8.4') {
			sh "mvn -Pjib package jib:build -T100"
		}
	}
	stage("Deploy") {
		build 'springdoc-openapi-demos-v2-deploy'
	}
}
