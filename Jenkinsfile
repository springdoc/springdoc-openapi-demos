#!/usr/bin/env groovy

node {
	stage('checkout') {
		deleteDir()
	}
	stage('checkout') {
		checkout scm
	}
	stage('Clean') {
		withMaven(maven: 'maven') {
			sh "mvn clean -T100"
		}
	}
	stage('Package') {
		withMaven(maven: 'maven') {
			sh "mvn -Pjib package jib:build -T100"
		}
	}
	stage("Deploy") {
		build 'springdoc-openapi-demos-v2-deploy'
	}
}
