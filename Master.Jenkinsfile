pipeline {
    agent any

    stages {
        stage('Triggering Base Jobs') {
            steps {
                script {
                    def failedJobs = []

                    def loadJobList = load 'listOfJobs.groovy'

                    def triggerBaseJobs = { baseJobs, ueVersion ->
                        baseJobs.each { baseJob ->
                            try {
                                def jobName = baseJob.job
                                def params = baseJob.params + [string(name: 'UEVersion', value: ueVersion)]
                                build job: jobName, parameters: params, wait: true
                            } catch (Exception e) {
                                failedJobs.add("[${ueVersion}] ${baseJob.job}")
                            }
                        }
                    }

                    parallel(
                        "UE4.27": {
                            triggerBaseJobs(loadJobList.UE4_27BaseJobs, '4.27')
                        },
                        "UE5.0": {
                            triggerBaseJobs(loadJobList.UE5_0BaseJobs, '5.0')
                        },
                        "UE5.1": {
                            triggerBaseJobs(loadJobList.UE5_1BaseJobs, '5.1')
                        },
                        "UE5.2": {
                            triggerBaseJobs(loadJobList.UE5_2BaseJobs, '5.2')
                        }
                    )

                    if (!failedJobs.isEmpty()) {
                        def message = "The following base jobs failed:\n" + failedJobs.join('\n')
                        slackSend(channel: '#jenkins-notif-test', message: message, color: 'danger')
                    }
                }
            }
        }
    }
}
