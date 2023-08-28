pipeline {
    agent any

    stages {
        stage('Triggering Intermediate Jobs') {
            steps {
                script {
                    def failedJobs = []
                    def triggerIntermediateJob = { jobName, ueVersion ->
                        def buildInfo = build job: "testing/Intermediates/${jobName}", parameters: [string(name: 'UEVersion', value: ueVersion)], propagate: false, wait: true
                        def buildResult = buildInfo.getResult()
                        def jobUrl = buildInfo.getAbsoluteUrl()
                        def jobCauses = buildInfo.getBuildCauses()
                        println "${jobCauses}"

                        if (buildResult == 'FAILURE') {
                            failedJobs.add("[${jobName}] ${jobUrl}")
                            //error "${jobName} failed"
                        }
                    }

                    parallel(
                        "Intermediate-ue4_27": {
                            triggerIntermediateJob("Intermediate-ue4_27", '4.27')
                        },
                        "Intermediate-ue5_0": {
                            triggerIntermediateJob("Intermediate-ue5_0", '5.0')
                        },
                        "Intermediate-ue5_1": {
                            triggerIntermediateJob("Intermediate-ue5_1", '5.1')
                        },
                        "Intermediate-ue5_2": {
                            triggerIntermediateJob("Intermediate-ue5_2", '5.2')
                        }
                    )

                    if (!failedJobs.isEmpty()) {
                        def message = "The following intermediate jobs failed:\n" + failedJobs.join('\n')
                        slackSend(channel: '#jenkins-notif-test', message: message, color: 'danger')
                    }
                }
            }
        }
    }
}
