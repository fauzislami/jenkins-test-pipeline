pipeline {
    agent any

    stages {
        stage('Triggering Intermediate Jobs') {
            steps {
                script {
                    def failedJobs = []

                    def intermediateJobs = [
                        ["name": "Intermediate-ue4_27", "version": '4.27'],
                        ["name": "Intermediate-ue5_0", "version": '5.0'],
                        ["name": "Intermediate-ue5_1", "version": '5.1'],
                        ["name": "Intermediate-ue5_2", "version": '5.2']
                    ]

                    def triggerIntermediateJob = { jobName, ueVersion ->
                        def buildInfo = build job: "testing/Intermediates/${jobName}", parameters: [string(name: 'UEVersion', value: ueVersion)], propagate: false, wait: true
                        def buildResult = buildInfo.getResult()
                        def jobUrl = buildInfo.getAbsoluteUrl()

                        if (buildResult == 'FAILURE') {
                            failedJobs.add("[${jobName}] ${jobUrl}")
                            error "${jobName} failed"
                        }
                    }

                    for (job in intermediateJobs) {
                        stage("Trigger ${job.name}") {
                            steps {
                                script {
                                    triggerIntermediateJob(job.name, job.version)
                                }
                            }
                        }
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
