 pipeline {
    agent any

    stages {
        stage('Triggering Intermediate Jobs') {
            parallel {
                stage('Intermediate-ue4_27') {
                    steps {
                      script{
                         sleep time: 5, unit: 'SECONDS'
                      }
                        sh "sed -i \"s|4_27|4_27|g\" Intermediates.Jenkinsfile"
                        build job: 'testing/Intermediates/Intermediate-ue4_27-new', parameters: [string(name: 'BaseJobs', value: 'UE4_27BaseJobs'),  string(name: 'PlatformsJobs', value: 'UE4_27PlatformsJobs')], wait: true
                    }
                }
                stage('Intermediate-ue5_0') {
                    steps {
                      script{
                         sleep time: 5, unit: 'SECONDS'
                      }
                        sh "sed -i \"s|4_27|5_0|g\" Intermediates.Jenkinsfile"
                        build job: 'testing/Intermediates/Intermediate-ue5_0-new', parameters: [string(name: 'BaseJobs', value: 'UE5_0BaseJobs'),  string(name: 'PlatformsJobs', value: 'UE5_0PlatformsJobs')], wait: true
                    }
                }
                stage('Intermediate-ue5_1') {
                    steps {
                      script{
                         sleep time: 5, unit: 'SECONDS'
                      }
                        sh "sed -i \"s|5_0|5_1|g\" Intermediates.Jenkinsfile"
                        build job: 'testing/Intermediates/Intermediate-ue5_1-new', parameters: [string(name: 'BaseJobs', value: 'UE5_1BaseJobs'),  string(name: 'PlatformsJobs', value: 'UE5_1PlatformsJobs')], wait: true
                    }
                }
                stage('Intermediate-ue5_2') {
                    steps {
                      script{
                         sleep time: 5, unit: 'SECONDS'
                      }
                        sh "sed -i \"s|5_1|5_2|g\" Intermediates.Jenkinsfile"
                        build job: 'testing/Intermediates/Intermediate-ue5_2-new', parameters: [string(name: 'BaseJobs', value: 'UE5_2BaseJobs'),  string(name: 'PlatformsJobs', value: 'UE5_2PlatformsJobs')], wait: true
                    }
                }
            }
        }

        // stage('Triggering Intermediate Jobs') {
        //     parallel {
        //         stage('Intermediate-ue4_27') {
        //             steps {
        //                 build job: 'testing/Intermediates/Intermediate-ue4_27'
        //             }
        //         }
        //         stage('Intermediate-ue5_0') {
        //             steps {
        //                 build job: 'testing/Intermediates/Intermediate-ue5_0'
        //             }
        //         }
        //         stage('Intermediate-ue5_1') {
        //             steps {
        //                 build job: 'testing/Intermediates/Intermediate-ue5_1'
        //             }
        //         }
        //         stage('Intermediate-ue5_2') {
        //             steps {
        //                 build job: 'testing/Intermediates/Intermediate-ue5_2'
        //             }
        //         }
        //     }
        // }
    }
}
