//构建版本的名称
def tag = "latest"
//Harbor私服地址
def harbor_url = "10.14.0.64:85"
//Harbor的项目名称
def harbor_project_name = "tensquare"
//Harbor的凭证
def harbor_auth = "31e1ce09-8e38-4c98-9d2e-661608b1abfd"
node {
   //把选择的项目信息转为数组
   def selectedProjects = "${project_name}".split(',')

   stage('拉取代码') {
                checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '789cd760-ed0d-453f-b722-d593e50ac3b3', url: 'http://10.14.0.62:82/microservice/microservice-parent.git']]])
   }
   stage('代码审查') {
         for(int i=0;i<selectedProjects.size();i++){
           //取出每个项目的名称和端口
           def projectInfo = selectedProjects[i];
           //项目名称
           def currentProjectName = "${projectInfo}".split("@")[0]
           //项目启动端口
           def currentProjectPort = "${projectInfo}".split("@")[1]

            //引入sonarqubescanner工具
            def scannerHome = tool 'sonar-scanner'
                   //引入sonarqube服务器环境
               withSonarQubeEnv('sonarqube') {
                       sh """
                          cd ${currentProjectName}
                          ${scannerHome}/bin/sonar-scanner
                       """
                     }
            }
   }

   stage('编译，构建镜像，部署服务') {
     for(int i=0;i<selectedProjects.size();i++){
      //取出每个项目的名称和端口
      def projectInfo = selectedProjects[i];
      //项目名称
      def currentProjectName = "${projectInfo}".split("@")[0]
      //项目启动端口
      def currentProjectPort = "${projectInfo}".split("@")[1]
   //定义镜像名称
       def imageName = "${currentProjectName}:${tag}"
   //编译，构建本地镜像
       sh "mvn -f ${currentProjectName} clean package dockerfile:build"
   //给镜像打标签
       sh "docker tag ${imageName} ${harbor_url}/${harbor_project_name}/${imageName}"
   //登录Harbor，并上传镜像
       withCredentials([usernamePassword(credentialsId: '31e1ce09-8e38-4c98-9d2e-661608b1abfd', passwordVariable: 'password', usernameVariable: 'username')]) {
   //登录
       sh "docker login -u ${username} -p ${password} ${harbor_url}"
   //上传镜像
       sh "docker push ${harbor_url}/${harbor_project_name}/${imageName}"
       }
   //以下为远程调用进行项目部署
   //    sshPublisher(publishers: [sshPublisherDesc(configName: 'master_server', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: "/data/devops/jenkins_shell/deploy.sh $harbor_url $harbor_project_name $project_name $tag $port", execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
    }

}
