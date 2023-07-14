 pipeline {
    agent any

    stages {
        stage('Triggering Intermediate Jobs') {
            parallel {
                stage('Intermediate-ue4_27') {
                    steps {
                        build job: 'testing/Intermediates/Intermediate-ue4_27-new', parameters: [string(name: 'BaseJobs', value: 'UE4_27BaseJobs'),  string(name: 'PlatformsJobs', value: 'UE4_27PlatformsJobs')], wait: true
                    }
                }
                stage('Intermediate-ue5_0') {
                    steps {
                        build job: 'testing/Intermediates/Intermediate-ue5_0-new', parameters: [string(name: 'BaseJobs', value: 'UE5_0BaseJobs'),  string(name: 'PlatformsJobs', value: 'UE5_0PlatformsJobs')], wait: true
                    }
                }
                stage('Intermediate-ue5_1') {
                    steps {
                        build job: 'testing/Intermediates/Intermediate-ue5_1-new', parameters: [string(name: 'BaseJobs', value: 'UE5_1BaseJobs'),  string(name: 'PlatformsJobs', value: 'UE5_1PlatformsJobs')], wait: true
                    }
                }
                stage('Intermediate-ue5_2') {
                    steps {
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
