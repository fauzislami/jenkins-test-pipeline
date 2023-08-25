pipeline {
    agent any

    stages {
        stage('Triggering Intermediate Jobs') {
            steps {
                script {
                    def failedJobs = []

                    def buildUrl = { jobName, buildNumber ->
                        return "${env.BUILD_URL}${jobName}/${buildNumber}/"
                    }

                    // Load the job list from the listOfJobs.groovy file
                    load 'listOfJobs.groovy'

                    // Consolidate the base jobs for each UE version
                    def ueVersions = ['UE4_27', 'UE5_0', 'UE5_1', 'UE5_2']
                    def baseJobs = [:]
                    ueVersions.each { version ->
                        baseJobs[version] = eval(version + 'BaseJobs')
                    }

                    parallel baseJobs.collectEntries { version, jobs ->
                        ["Intermediate-$version": {
                            try {
                                jobs.each { jobData ->
                                    build job: jobData.job, parameters: jobData.params, wait: true
                                }
                            } catch (Exception e) {
                                failedJobs.add("[Intermediate-$version](${buildUrl("Intermediate-$version", currentBuild.number)})")
                            }
                        }]
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
