pipeline {
    agent any

    stages {
        stage('Triggering Intermediate Jobs') {
            steps {
                script {
                    def failedJobs = []
                    def jobUrl

                    def buildUrl = { jobName, buildNumber ->
                        return "${env.JENKINS_URL}job/${jobName}/${buildNumber}/"
                    }

                    def triggerIntermediateJob = { jobName, ueVersion ->
                        try {
                            def buildInfo = build job: "testing/Intermediates/${jobName}", parameters: [string(name: 'UEVersion', value: ueVersion)], wait: true
                            //def buildNumber = buildInfo.getNumber()
                            //def jobUrl = buildUrl("testing/Intermediates/${jobName}", buildNumber)
                            jobUrl = buildInfo.getAbsoluteUrl()
                        } catch (Exception e) {
                            //failedJobs.add("[${jobName}](${buildUrl("testing/Intermediates/${jobName}", currentBuild.number)})")
                            failedJobs.add("[${jobName}] ${jobUrl}")
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
