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

@Library('MyTestLibrary') _

node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
            getExistingJobs(jobsToTrigger: firstJob, jobTemplate: "testing/template")
            getExistingJobs(jobsToTrigger: secondJob, jobTemplate: "testing/template")
        }
    }
}

def countFirstJob = firstJob.size()
def countSecondJob = secondJob.size()
def parallelFirstJobs = [:]
def parallelSecondJobs = [:]

for (def i = 0; i < countFirstJob; i++) {
    def jobParams = firstJob[i]
    parallelFirstJobs[jobParams.job] = stageFirstJobs(jobParams)
}

for (def i = 0; i < countSecondJob; i++) {
    def jobParams = secondJob[i]
    parallelSecondJobs[jobParams.job] = stageSecondJobs(jobParams)
}

def stageFirstJobs(jobParams) {
    return {
        stage("stage: ${jobParams.job}") {
            def triggeredJobs = build job: jobParams.job, parameters: jobParams.params, propagate: true, wait: true
            def buildResult = triggeredJobs.getResult()

            if (buildResult != 'SUCCESS') {
                error "${jobParams.job} failed"
                //notify via slack or email
            }
        }
    }
}

def stageSecondJobs(jobParams) {
    return {
        stage("stage: ${jobParams.job}") {
            def triggeredJobs = build job: jobParams.job, parameters: jobParams.params, propagate: true, wait: true
            def buildResult = triggeredJobs.getResult()

            if (buildResult != 'SUCCESS') {
                error "${jobParams.job} failed"
                //notify via slack or email
            }
        }
    }
}

pipeline {
    agent any
    stages {
        stage('First Job') {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    script {
                        parallel parallelFirstJobs
                    }                
                }
            }
        }
        stage('Second Job') {
            when {
                expression { currentBuild.result != 'FAILURE' }
            }
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    script {
                        parallel parallelSecondJobs
                    }
                }
            }
        }
    }
}
