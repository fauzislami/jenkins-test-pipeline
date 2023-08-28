pipeline {
    agent any

    stages {
       stage('Triggering Intermediate Jobs') {
            parallel {
             stage('Intermediate-ue4_27') {
                 steps {
                     build job: 'testing/Intermediates/Intermediate-ue4_27', parameters: [string(name: 'UEVersion', value: '4.27')], wait: true
                 }
             }
             stage('Intermediate-ue5_0') {
                 steps {
                     build job: 'testing/Intermediates/Intermediate-ue5_0', parameters: [string(name: 'UEVersion', value: '5.0')], wait: true
                 }
             }
             stage('Intermediate-ue5_1') {
                 steps {
                     build job: 'testing/Intermediates/Intermediate-ue5_1', parameters: [string(name: 'UEVersion', value: '5.1')], wait: true
                 }
             }
             stage('Intermediate-ue5_2') {
                 steps {
                     build job: 'testing/Intermediates/Intermediate-ue5_2', parameters: [string(name: 'UEVersion', value: '5.2')], wait: true
                 }
             }
          }
       }
    }
    post {
        always {
            stage("Retrieve and Print Build Results") {
                script {
                    def groovyFiles = ["UE4_27.groovy", "UE5_0.groovy", "UE5_1.groovy"]

                    for (groovyFile in groovyFiles) {
                        def varsFile = load groovyFile

                        for (job in BaseJobs) {
                            def jobName = job.job
                            def build = retrieveLatestBuild(jobName)
                            if (build) {
                                printBuildResult(build)
                            } else {
                                echo "No builds found for job: ${jobName}"
                            }
                        }
                    }
                }
            }
        }
    }
}
