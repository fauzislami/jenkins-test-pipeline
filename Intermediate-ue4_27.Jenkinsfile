@Library('MyTestLibrary') _

node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
            getExistingJobs(jobsToTrigger: UE4_27BaseJobs, jobTemplate: "testing/template")
            getExistingJobs(jobsToTrigger: secondJob, jobTemplate: "testing/template")
        }
    }
}

def countUE4_27BaseJobs = UE4_27BaseJobs.size()
def countSecondJob = secondJob.size()
def parallelUE4_27BaseJobs = [:]
def parallelSecondJobs = [:]

for (def i = 0; i < countUE4_27BaseJobs; i++) {
    def jobParams = UE4_27BaseJobs[i]
    parallelUE4_27BaseJobs[jobParams.job] = stageUE4_27BaseJobs(jobParams)
}

for (def i = 0; i < countSecondJob; i++) {
    def jobParams = secondJob[i]
    parallelSecondJobs[jobParams.job] = stageSecondJobs(jobParams)
}

def stageUE4_27BaseJobs(jobParams) {
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
                        parallel parallelUE4_27BaseJobs
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
