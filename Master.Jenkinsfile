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

                    // Load the job definitions from the listOfJobs.groovy file
                    def jobDefinitions = load 'listOfJobs.groovy'

                    parallel jobDefinitions.collectEntries { version, jobs ->
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
