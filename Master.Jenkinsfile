pipeline {
    agent any

    stages {
        stage('Triggering Intermediate Jobs') {
            steps {
                script {
                    def failedJobs = []

                    def triggerIntermediateJob = { jobName, ueVersion ->
                        def job = Jenkins.instance.getItemByFullName("testing/Intermediates/${jobName}")
                    
                        if (job) {
                            def latestBuild = job.getLastBuild()
                            if (latestBuild) {
                                def downstreamBuilds = latestBuild.getDownstreamBuilds()
                                def buildResult = latestBuild.getResult()
                                def jobUrl = latestBuild.getAbsoluteUrl()
                    
                                if (buildResult == 'FAILURE') {
                                    failedJobs.add("[${jobName}] ${jobUrl}")
                                } else {
                                    def downstreamJobs = downstreamBuilds.collect { it.project.fullName }
                                    println "Downstream jobs of ${jobName}: ${downstreamJobs}"
                                }
                            } else {
                                println "No builds found for job: ${jobName}"
                            }
                        } else {
                            println "Job not found: ${jobName}"
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
