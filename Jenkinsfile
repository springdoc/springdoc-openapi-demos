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
			sh "mvn clean"
		}
	}
	stage('Package') {
		withMaven(maven: 'maven') {
			sh "mvn package jib:build"
		}
	}
	stage("Deploy") {
		build 'springdoc-openapi-demos-deploy'
	}
}
