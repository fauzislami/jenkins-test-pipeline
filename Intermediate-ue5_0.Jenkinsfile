@Library('MyTestLibrary') _

node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
            getExistingJobs(jobsToTrigger: UE5_0BaseJobs, jobTemplate: "testing/template")
            getExistingJobs(jobsToTrigger: UE5_0PlatformsJobs, jobTemplate: "testing/template")
        }
    }
}

def countUE5_0BaseJobs = UE5_0BaseJobs.size()
def countUE5_0PlatformsJobs = UE5_0PlatformsJobs.size()
def parallelUE5_0BaseJobs = [:]
def parallelUE5_0PlatformsJobs = [:]

for (def i = 0; i < countUE5_0BaseJobs; i++) {
    def jobParams = UE5_0BaseJobs[i]
    parallelUE5_0BaseJobs[jobParams.job] = stageUE5_0BaseJobs(jobParams)
}

for (def i = 0; i < countUE5_0PlatformsJobs; i++) {
    def jobParams = UE5_0PlatformsJobs[i]
    parallelUE5_0PlatformsJobs[jobParams.job] = stageUE5_0PlatformsJobs(jobParams)
}

def stageUE5_0BaseJobs(jobParams) {
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

def stageUE5_0PlatformsJobs(jobParams) {
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
                        parallel parallelUE5_0BaseJobs
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
                        parallel parallelUE5_0PlatformsJobs
                    }
                }
            }
        }
    }
}
