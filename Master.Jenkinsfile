def retrieveLatestBuild(jobName) {
    def build = jenkins.model.Jenkins.instance.getItemByFullName(jobName).getLastBuild()
    return build
}


pipeline {
    agent any

    stages {
       stage('Triggering Intermediate Jobs') {
            parallel {
             stage('Intermediate-ue4_27') {
                 steps {
                     build job: 'testing/Intermediates/Intermediate-ue4_27', parameters: [string(name: 'UEVersion', value: '4.27')], wait: true
                 }
             }
             stage('Intermediate-ue5_0') {
                 steps {
                     build job: 'testing/Intermediates/Intermediate-ue5_0', parameters: [string(name: 'UEVersion', value: '5.0')], wait: true
                 }
             }
             stage('Intermediate-ue5_1') {
                 steps {
                     build job: 'testing/Intermediates/Intermediate-ue5_1', parameters: [string(name: 'UEVersion', value: '5.1')], wait: true
                 }
             }
             stage('Intermediate-ue5_2') {
                 steps {
                     build job: 'testing/Intermediates/Intermediate-ue5_2', parameters: [string(name: 'UEVersion', value: '5.2')], wait: true
                 }
             }
          }
       }
    }
    post {
        always {
            script {
                def jobsResultsByUE = [:]
                def groovyFiles = ["UE4_27.groovy", "UE5_0.groovy", "UE5_1.groovy", "UE5_2.groovy"]
                def combinedMessage = ""
                def allJobs = BaseJobs + PlatformJobs

                for (groovyFile in groovyFiles) {
                    def ueVersion = groovyFile.tokenize('_')[0]
                    def varsFile = load groovyFile

                    for (job in allJobs) {
                        def jobName = job.job
                        def build = retrieveLatestBuild(jobName)
                        def buildResult = "${build.result}"
                        def buildUrl = build.getAbsoluteUrl()

                        if (buildResult) {
                            def emoji = buildResult == "FAILURE" ? ":x:" : ":white_check_mark:"
                            if (!jobsResultsByUE.containsKey(ueVersion)) {
                                jobsResultsByUE[ueVersion] = []
                            }
                            jobsResultsByUE[ueVersion].add("[${jobName}] - <${buildUrl}|See here> - ${buildResult} $emoji")               
                        }
                    }
                }
                jobsResultsByUE.each { version, results -> 
                    if (!results.isEmpty()) {
                        combinedMessage += "*THE FOLLOWING JOBS FAILED FOR ${version}:*\n" + results.join('\n') + "\n"
                    }
                }
                if (!combinedMessage.isEmpty()) {
                    slackSend(channel: '#jenkins-notif-test', message: combinedMessage, color: 'danger')
                }
            }
        }
    }
}
