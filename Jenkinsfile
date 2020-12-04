pipeline {
    agent any

    stages {
        stage('拉取代码') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '789cd760-ed0d-453f-b722-d593e50ac3b3', url: 'http://10.14.0.62:82/microservice/microservice-parent.git']]])
            }
        }
        stage('代码审查') {
             steps {
                //引入sonarqubescanner工具
                script {
                   scannerHome = tool 'sonar-scanner'
                }
                //引入sonarqube服务器环境
                withSonarQubeEnv('sonarqube') {
                    sh """
                       cd ${project_name}
                       ${scannerHome}/bin/sonar-scanner
                    """
                }
             }
        }
    }
}
