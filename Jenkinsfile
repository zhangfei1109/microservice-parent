//git凭证ID
def git_auth = "789cd760-ed0d-453f-b722-d593e50ac3b3"
//git的url地址
def git_url = "http://10.14.0.62:82/microservice/microservice-parent.git"
//镜像的版本号
def tag = "latest"
//Harbor的url地址
def harbor_url = "10.14.0.64:85"
//镜像库项目名称
def harbor_project = "tensquare"
//Harbor的登录凭证ID
def harbor_auth = "31e1ce09-8e38-4c98-9d2e-661608b1abfd"

node {
   //获取当前选择的项目名称
   def selectedProjectNames = "${project_name}".split(",")
   //获取当前选择的服务器名称
   def selectedServers = "${publish_server}".split(",")

   stage('拉取代码') {
      checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "${git_auth}", url: "${git_url}"]]])
   }
   stage('代码审查') {
        for(int i=0;i<selectedProjectNames.length;i++){
            //tensquare_eureka_server@10086
            def projectInfo = selectedProjectNames[i];
            //当前遍历的项目名称
            def currentProjectName = "${projectInfo}".split("@")[0]
            //当前遍历的项目端口
            def currentProjectPort = "${projectInfo}".split("@")[1]

            //定义当前Jenkins的SonarQubeScanner工具
            def scannerHome = tool 'sonar-scanner'
            //引用当前JenkinsSonarQube环境
            withSonarQubeEnv('sonarqube') {
                 sh """
                         cd ${currentProjectName}
                         ${scannerHome}/bin/sonar-scanner
                 """
            }
        }


   }

   stage('编译，打包微服务工程，上传镜像') {
       for(int i=0;i<selectedProjectNames.length;i++){
                 //tensquare_eureka_server@10086
                 def projectInfo = selectedProjectNames[i];
                 //当前遍历的项目名称
                 def currentProjectName = "${projectInfo}".split("@")[0]
                 //当前遍历的项目端口
                 def currentProjectPort = "${projectInfo}".split("@")[1]

                 sh "mvn -f ${currentProjectName} clean package dockerfile:build"

                 //定义镜像名称
                 def imageName = "${currentProjectName}:${tag}"

                 //对镜像打上标签
                 sh "docker tag ${imageName} ${harbor_url}/${harbor_project}/${imageName}"

                //把镜像推送到Harbor
                withCredentials([usernamePassword(credentialsId: "${harbor_auth}", passwordVariable: 'password', usernameVariable: 'username')]) {

                    //登录到Harbor
                    sh "docker login -u ${username} -p ${password} ${harbor_url}"

                    //镜像上传
                    sh "docker push ${harbor_url}/${harbor_project}/${imageName}"

                    sh "echo 镜像上传成功"
                }

                //遍历所有服务器，分别部署
                for(int j=0;j<selectedServers.length;j++){
                       //获取当前遍历的服务器名称
                       def currentServerName = selectedServers[j]

                       //加上的参数格式：--spring.profiles.active=eureka-server1/eureka-server2
                       def activeProfile = "--spring.profiles.active="

                       //根据不同的服务名称来读取不同的Eureka配置信息
                       if(currentServerName=="master_server"){
                          activeProfile = activeProfile+"eureka-server1"
                       }else if(currentServerName=="slave_server"){
                          activeProfile = activeProfile+"eureka-server2"
                       }

                       //部署应用
                       sshPublisher(publishers: [sshPublisherDesc(configName: "${currentServerName}", transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: "/data/devops/jenkins_shell/deployCluster.sh $harbor_url $harbor_project $currentProjectName $tag $currentProjectPort $activeProfile", execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])


                }

        }
   }
}