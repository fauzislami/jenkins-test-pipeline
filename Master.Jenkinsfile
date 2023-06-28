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
    
    parameters {
        string(name: 'UEVersion', defaultValue: '', description: 'Specify UE Version')
    }
    
    stages {
        stage("Triggering Intermediate Jobs") {
            steps {
                script {
                    build job: 'testing/Intermediates/Intermediate-jobs', parameters: [string(name: 'UEVersion', value: '4_27')]
                }
            }
        }
    }
}
