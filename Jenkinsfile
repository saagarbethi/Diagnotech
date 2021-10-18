

sourceCodePipeline([buildNode: 'pcf-build-node']) {

  addStage('get-modules','any:any') {
        def pom = readMavenPom file: 'pom.xml'
        env.SELECTED_MODULES = '['
        env.ALL_MODULES = '['
        pom.modules.each { m ->

            env.ALL_MODULES = env.ALL_MODULES == '[' ? "${env.ALL_MODULES}'${m}'" : "${env.ALL_MODULES},'${m}'"

            if (env.GIT_COMMIT_MESSAGE.contains(m)) {
                env.SELECTED_MODULES = env.SELECTED_MODULES == '[' ? "${env.SELECTED_MODULES}'${m}'" : "${env.SELECTED_MODULES},'${m}'"
            }
        }
        env.SELECTED_MODULES = "${env.SELECTED_MODULES}]"
        env.ALL_MODULES = "${env.ALL_MODULES}]"

        // if no module mentioned defaults to osms-subscriptionservice
        // if you want to remove the default just delete the below line
        env.SELECTED_MODULES = env.SELECTED_MODULES == '[]' ? "['diagnotech']" : env.SELECTED_MODULES;
    }

        // disabling this stage to make the pipeline build, package and deploy
        //mvnBuildWithSonar('master:any','clean install test sonar:sonar', [ mvnLogsOnFailure: 100 ])
        buildWithSonar([
                  runSonarStageName: 'build',
                  runFor: 'any:any',
                  checkSonarStageName: 'sonar-check',
                  buildLogFile: 'maven.log'
                ]) {
                  sh '''
                    chmod +x mvnw
                    #./mvnw clean install -Dmaven.test.skip=true -Drevision='${env.ARTIFACT_REVISION}' -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn --log-file maven.log
                  '''
                  def returnStatus
                   withEnv(["JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64"]) {
                    returnStatus = sh returnStatus: true, script: "./mvnw clean install test -Dtest-groups=all sonar:sonar -Drevision=${ARTIFACT_REVISION}"
                        }
                    returnStatus
        }

        addStage('prepare-artifacts','master:dev,release:any,devops:any'){
            def moduleList = Eval.me(env.ALL_MODULES)

            sh 'mkdir target || true'

            moduleList.each { module ->
                sh returnStatus: true, script: """
                    cp ${BUILD_INFO_FILE} target/${module}-${ARTIFACT_REVISION}-buildInfo.properties
                    echo 'ARTIFACT=${module}' >> target/${module}-${ARTIFACT_REVISION}-buildInfo.properties
                    cp ${module}/target/${module}-${ARTIFACT_REVISION}.jar target
					cp ${module}/target/${module}-${ARTIFACT_REVISION}.jar.original target
                    ls -l target/
                """
            }
        }

        addStage('services-deploy','master:dev,devops:any') {
          def moduleList = Eval.me(env.ALL_MODULES)
          moduleList.each { module ->
              pcfDeploy.services([
                   foundationName: 'stratus-west',
                   servicesYamlPath:  "app-deploy/stratus/services/${module}.yaml"
              ])
           }
        }

         addStage('app-deploy','master:dev,devops:any') {
           def moduleList = Eval.me(env.ALL_MODULES)
           moduleList.each { module ->
    switch(module) {
                default:
           pcfDeploy.app([
                        pcfAppName: module,
                        pcfAppNameGreen: "${module}-green-${env.PCF_SPACE}",
                        foundationName: 'stratus-west',
                        deployablePath: "target/${module}-${ARTIFACT_REVISION}.jar",
                        servicesYamlPath: "app-deploy/stratus/services/${module}.yaml",
                        manifestYamlPath: PCF_MANIFEST_PATH,
                        springActiveProfile: "${PCF_SPACE}",
                        additionalPushOptions: "--vars-file ${PCF_CFVARS_PATH}/${PCF_SPACE}.yml",
                    ])
                     break
         }
       }
     }

     addStage('push-artifact','master:dev,release:any,devops:any') {
             artifact.push "target/*"
         }

}
