pipeline {
    agent any

    stages {
        stage('Triggering Intermediate Jobs') {
            steps {
                script {
                    def failedJobs = []

                    def triggerIntermediateJob = { jobName, ueVersion ->
                        def buildInfo = build job: "testing/Intermediates/${jobName}", parameters: [string(name: 'UEVersion', value: ueVersion)], wait: true
                        def buildResult = buildInfo.getResult  // Use 'result' instead of 'getResult()'
                        def jobUrl = "${buildInfo.getAbsoluteUrl}"  // Use 'absoluteUrl' instead of 'getAbsoluteUrl()'

                        if (buildResult == hudson.model.Result.FAILURE) {  // Use 'hudson.model.Result.FAILURE' instead of 'FAILURE'
                            failedJobs.add("[${jobName}] ${jobUrl}")
                        }
                    }

                    // Use a map instead of parallel steps
                    def jobMap = [
                        "Intermediate-ue4_27": '4.27',
                        "Intermediate-ue5_0": '5.0',
                        "Intermediate-ue5_1": '5.1',
                        "Intermediate-ue5_2": '5.2'
                    ]

                    jobMap.each { jobName, ueVersion ->
                        triggerIntermediateJob(jobName, ueVersion)
                    }

                    if (!failedJobs.isEmpty()) {
                        def message = "The following intermediate jobs failed:\n" + failedJobs.join('\n')
                        slackSend(channel: '#jenkins-notif-test', message: message, color: 'danger')
                    }
                }
            }
        }
    }
}
