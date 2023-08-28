pipeline {
    agent any

    stages {
        stage('Triggering Intermediate Jobs') {
            steps {
                script {
                    def failedJobs = []
                    def triggerIntermediateJob = { jobName, ueVersion ->
                        def buildInfo = build job: "testing/Intermediates/${jobName}", parameters: [string(name: 'UEVersion', value: ueVersion)], propagate: false, wait: true
                        def buildResult = buildInfo.result
                        def jobUrl = buildInfo.absoluteUrl

                        println "${buildResult}"

                        if (buildResult == 'FAILURE') {
                            failedJobs.add("[${jobName}] ${jobUrl}")
                            //error "${jobName} failed"
                        }
                    }

                    def intermediateJobs = [
                        ["Intermediate-ue4_27", '4.27'],
                        ["Intermediate-ue5_0", '5.0'],
                        ["Intermediate-ue5_1", '5.1'],
                        ["Intermediate-ue5_2", '5.2']
                    ]

                    def jobClosures = intermediateJobs.collect { job ->
                        return { "${job[0]}": { triggerIntermediateJob(job[0], job[1]) } }
                    }

                    parallel jobClosures

                    if (!failedJobs.isEmpty()) {
                        def message = "The following intermediate jobs failed:\n" + failedJobs.join('\n')
                        slackSend(channel: '#jenkins-notif-test', message: message, color: 'danger')
                    }
                }
            }
        }
    }
}
