@Library('MyTestLibrary') _

node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
            getExistingJobs(jobsToTrigger: UE5_1BaseJobs, jobTemplate: "testing/template")
            getExistingJobs(jobsToTrigger: UE5_1PlatformsJobs, jobTemplate: "testing/template")
        }
    }
}

def countUE5_1BaseJobs = UE5_1BaseJobs.size()
def countUE5_1PlatformsJobs = UE5_1PlatformsJobs.size()
def parallelUE5_1BaseJobs = [:]
def parallelUE5_1PlatformsJobs = [:]

for (def i = 0; i < countUE5_1BaseJobs; i++) {
    def jobParams = UE5_1BaseJobs[i]
    parallelUE5_1BaseJobs[jobParams.job] = stageUE5_1BaseJobs(jobParams)
}

for (def i = 0; i < countUE5_1PlatformsJobs; i++) {
    def jobParams = UE5_1PlatformsJobs[i]
    parallelUE5_1PlatformsJobs[jobParams.job] = stageUE5_1PlatformsJobs(jobParams)
}

def stageUE5_1BaseJobs(jobParams) {
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

def stageUE5_1PlatformsJobs(jobParams) {
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
        stage('UE4.27 Base Jobs') {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    script {
                        parallel parallelUE5_1BaseJobs
                    }                
                }
            }
        }
        stage('UE4.27 Jobs for Other Platforms') {
            when {
                expression { currentBuild.result != 'FAILURE' }
            }
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    script {
                        parallel parallelUE5_1PlatformsJobs
                    }
                }
            }
        }
    }
}
