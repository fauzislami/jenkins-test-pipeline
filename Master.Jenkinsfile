def retrieveLatestBuild(jobName) {
    def build = jenkins.model.Jenkins.instance.getItemByFullName(jobName).getLastBuild()
    return build
}

def printBuildResult(build) {
    echo "Job: ${build.project.name}"
    echo "Build Status: ${build.result}"
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
                def failedJobsByUEVersion = [:]
                def groovyFiles = ["UE4_27.groovy", "UE5_0.groovy", "UE5_1.groovy", "UE5_2.groovy"]

                for (groovyFile in groovyFiles) {
                    def varsFile = load groovyFile

                    for (job in BaseJobs) {
                        def jobName = job.job
                        def build = retrieveLatestBuild(jobName)
                        if (build) {
                            printBuildResult(build)
                            def ueVersion = groovyFile.replaceAll("[^0-9]", "_")
                            failedJobsByUEVersion[ueVersion] ||= []
                            failedJobsByUEVersion[ueVersion] << "[${jobName}]"
                        } else {
                            echo "No builds found for job: ${jobName}"
                        }
                    }
                }
                for (ueVersion, jobsList in failedJobsByUEVersion) {
                    if (!jobsList.isEmpty()) {
                        def message = "The following jobs failed for UE version ${ueVersion}:\n" + jobsList.join('\n')
                        slackSend(channel: '#jenkins-notif-test', message: message, color: 'danger')
                    }
                }
            }
        }
    }
}
