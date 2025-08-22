#!/usr/bin/env groovy

node {
	stage('checkout') {
		deleteDir()
		checkout scm
	}
	stage('Clean') {
		withMaven(jdk: 'java-17', maven: 'maven-3.8.4'){
				sh "mvn clean -T100"
		}
	}
	stage('Package') {
		withMaven(jdk: 'java-17', maven: 'maven-3.8.4'){
			sh "mvn -Pjib package jib:build -T100"
		}
	}
	stage("Deploy") {
		build 'springdoc-openapi-demos-deploy'
		deleteDir()
	}
}
