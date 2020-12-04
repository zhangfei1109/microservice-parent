//构建版本的名称
def tag = "latest"
//Harbor私服地址
def harbor_url = "10.14.0.64:85"
//Harbor的项目名称
def harbor_project_name = "tensquare"
node {
   stage('拉取代码') {
                checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '789cd760-ed0d-453f-b722-d593e50ac3b3', url: 'http://10.14.0.62:82/microservice/microservice-parent.git']]])
   }
   stage('代码审查') {
                   //引入sonarqubescanner工具
               def scannerHome = tool 'sonarqube-scanner'
                   //引入sonarqube服务器环境
               withSonarQubeEnv('sonarqube') {
                       sh """
                          cd ${project_name}
                          ${scannerHome}/bin/sonar-scanner
                       """
               }
   }

   stage('编译，构建镜像，部署服务') {
   //定义镜像名称
       def imageName = "${project_name}:${tag}
   //编译，构建本地镜像
       sh "mvn -f ${project_name} clean package dockerfile:build"
   //给镜像打标签
       sh "docker tag ${imageName} ${harbor_url}/${harbor_project_name}/${imageName}"

    }

}
