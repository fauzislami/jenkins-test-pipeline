pipeline {
    agent any

    stages {
        stage('Triggering Intermediate Jobs') {
            steps {
                script {
                    def failedJobs = []

                    parallel(
                        "Intermediate-ue4_27": {
                            try {
                                build job: 'testing/Intermediates/Intermediate-ue4_27', parameters: [string(name: 'UEVersion', value: '4.27')], wait: true
                            } catch (Exception e) {
                                failedJobs.add("Intermediate-ue4_27")
                            }
                        },
                        "Intermediate-ue5_0": {
                            try {
                                build job: 'testing/Intermediates/Intermediate-ue5_0', parameters: [string(name: 'UEVersion', value: '5.0')], wait: true
                            } catch (Exception e) {
                                failedJobs.add("Intermediate-ue5_0")
                            }
                        },
                        "Intermediate-ue5_1": {
                            try {
                                build job: 'testing/Intermediates/Intermediate-ue5_1', parameters: [string(name: 'UEVersion', value: '5.1')], wait: true
                            } catch (Exception e) {
                                failedJobs.add("Intermediate-ue5_1")
                            }
                        },
                        "Intermediate-ue5_2": {
                            try {
                                build job: 'testing/Intermediates/Intermediate-ue5_2', parameters: [string(name: 'UEVersion', value: '5.2')], wait: true
                            } catch (Exception e) {
                                failedJobs.add("Intermediate-ue5_2")
                            }
                        }
                    )

                    if (!failedJobs.isEmpty()) {
                        def message = "The following intermediate jobs failed:\n" + failedJobs.join('\n')
                        slackSend(channel: '#your-slack-channel', message: message, color: 'danger')
                    }
                }
            }
        }
    }
}
