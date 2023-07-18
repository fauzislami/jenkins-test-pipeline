 pipeline {
    agent any

    stages {
        stage('Triggering Intermediate Jobs') {
            parallel {
                stage('Intermediate-ue4_27') {
                    steps {
                        build job: 'testing/Intermediates/Intermediate-ue4_27-new', parameters: [string(name: 'UEVersion', value: '4.27')], wait: true
                    }
                }
                stage('Intermediate-ue5_0') {
                    steps {
                        build job: 'testing/Intermediates/Intermediate-ue5_0-new', parameters: [string(name: 'UEVersion', value: '5.0')], wait: true
                    }
                }
                stage('Intermediate-ue5_1') {
                    steps {
                        build job: 'testing/Intermediates/Intermediate-ue5_1-new', parameters: [string(name: 'UEVersion', value: '5.1')], wait: true
                    }
                }
                stage('Intermediate-ue5_2') {
                    steps {
                        build job: 'testing/Intermediates/Intermediate-ue5_2-new', parameters: [string(name: 'UEVersion', value: '5.2')], wait: true
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
