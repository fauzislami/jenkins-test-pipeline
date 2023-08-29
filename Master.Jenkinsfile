def retrieveLatestBuild(jobName) {
    def build = jenkins.model.Jenkins.instance.getItemByFullName(jobName).getLastBuild()
    return build
}

@Library('MyTestLibrary') _

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
        stage('Send Notifications'){
            slackNotif(["UE4_27.groovy", "UE5_0.groovy", "UE5_1.groovy", "UE5_2.groovy"])
        }
    }
   // post {
    //     always {
    //         script {
    //             def jobResultsByType = [:]
    //             def groovyFiles = ["UE4_27.groovy", "UE5_0.groovy", "UE5_1.groovy", "UE5_2.groovy"]
    //             def combinedMessage = ""
    //             def jobType

    //             for (groovyFile in groovyFiles) {
    //                 jobType = groovyFile.tokenize('.')[0]
    //                 def varsFile = load groovyFile
    //                 def allJobs = BaseJobs + PlatformsJobs

    //                 for (job in allJobs) {
    //                     def jobName = job.job
    //                     def build = retrieveLatestBuild(jobName)
    //                     def buildResult = "${build.result}"
    //                     def buildUrl = build.getAbsoluteUrl()

    //                     if (buildResult != "SUCCESS") {
    //                         def emoji = buildResult == "FAILURE" ? ":x:" : ":no_entry_sign:"
    //                         if (!jobResultsByType.containsKey(jobType)) {
    //                             jobResultsByType[jobType] = []
    //                         }
    //                         jobResultsByType[jobType].add("[${jobName}] - <${buildUrl}|See here> - ${buildResult} $emoji")               
    //                     }
    //                 }
    //             }
    //             jobResultsByType.each { version, results -> 
    //                 if (!results.isEmpty()) {
    //                     combinedMessage += "*THE FOLLOWING JOBS FAILED FOR ${version}:*\n" + results.join('\n') + "\n"
    //                 }
    //             }
    //             if (!combinedMessage.isEmpty()) {
    //                 slackSend(channel: '#jenkins-notif-test', message: combinedMessage, color: 'danger')
    //             } else {
    //                 slackSend(channel: '#jenkins-notif-test', message: "All jobs succeed :white_check_mark:", color: 'good')
    //             }
    //         }
    //     }
    // }
}
