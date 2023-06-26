pipeline {
	agent any
	triggers {
        pollSCM '* * * * *'
	}
	environment {
	    DOCKER_IMAGE_NAME = 'rajuchigicherla/springboot-forecast:latest'
	    DOCKER_USER_NAME = 'rajuchigicherla'
	}
	stages {
		stage('SCM Checkout') {
			steps {
				git branch: 'dev', credentialsId: '5d8c9e98-ddca-4df5-97cb-f333e6583cb0', url: 'https://github.com/Raju-Chigicherla/springboot-forecast.git'
			}
		}
		stage('mvn package') {
			steps {
			    script {
			        def mvnHome = tool name: 'maven-3.8.5', type: 'maven'
			        echo "maven: ${mvnHome}"
			        def mvnCmd = "${mvnHome}\\bin\\mvn"
			        bat "${mvnCmd} --version"
			        bat "${mvnCmd} clean package"
			    }
			}
		}
		stage('docker build image') {
			steps {
			    script {
					bat "docker build -t ${env.DOCKER_IMAGE_NAME} ."
    			}
			}
		}
		
		stage('docker push image') {
			steps {
			    script {
    			    withCredentials([string(credentialsId: 'dockerHubPwd', variable: 'dockerHubPwd')]) {
    			    	bat "docker login -u ${env.DOCKER_USER_NAME} -p ${dockerHubPwd}"
					}
					bat "docker push ${env.DOCKER_IMAGE_NAME}"
    			}
			}
		}
	}
}
