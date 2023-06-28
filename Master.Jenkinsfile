/*
pipeline {
    agent any
    
    stages {
        stage('Triggering Intermediate Jobs') {
            steps {
                parallel (
                    'Intermediate-ue4_27': {
                        build job: 'testing/Intermediates/Intermediate-ue4_27'
                    },
                    'Intermediate-ue5_0': {
                        build job: 'testing/Intermediates/Intermediate-ue5_0'
                    },
                    'Intermediate-ue5_1': {
                        build job: 'testing/Intermediates/Intermediate-ue5_1'
                    },
                    'Intermediate-ue5_2': {
                        build job: 'testing/Intermediates/Intermediate-ue5_2'
                    }
                )
            }
        }
    }
}
*/

pipeline {
    agent any

    stages {
        stage('Triggering Intermediate Jobs') {
            parallel {
                stage('Intermediate-ue4_27') {
                    steps {
                        build job: 'testing/Intermediates/Intermediate-ue4_27'
                    }
                }
                stage('Intermediate-ue5_0') {
                    steps {
                        build job: 'testing/Intermediates/Intermediate-ue5_0'
                    }
                }
                stage('Intermediate-ue5_1') {
                    steps {
                        build job: 'testing/Intermediates/Intermediate-ue5_1'
                    }
                }
                stage('Intermediate-ue5_2') {
                    steps {
                        build job: 'testing/Intermediates/Intermediate-ue5_2'
                    }
                }
            }
        }
    }
}
