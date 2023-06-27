@Library('MyTestLibrary') _

node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
            getExistingJobs(jobsToTrigger: UE5_2BaseJobs, jobTemplate: "testing/template")
            getExistingJobs(jobsToTrigger: UE5_2PlatformsJobs, jobTemplate: "testing/template")
        }
    }
}

def countUE5_2BaseJobs = UE5_2BaseJobs.size()
def countUE5_2PlatformsJobs = UE5_2PlatformsJobs.size()
def parallelUE5_2BaseJobs = [:]
def parallelUE5_2PlatformsJobs = [:]

for (def i = 0; i < countUE5_2BaseJobs; i++) {
    def jobParams = UE5_2BaseJobs[i]
    parallelUE5_2BaseJobs[jobParams.job] = stageUE5_2BaseJobs(jobParams)
}

for (def i = 0; i < countUE5_2PlatformsJobs; i++) {
    def jobParams = UE5_2PlatformsJobs[i]
    parallelUE5_2PlatformsJobs[jobParams.job] = stageUE5_2PlatformsJobs(jobParams)
}

def stageUE5_2BaseJobs(jobParams) {
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

def stageUE5_2PlatformsJobs(jobParams) {
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
                        parallel parallelUE5_2BaseJobs
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
                        parallel parallelUE5_2PlatformsJobs
                    }
                }
            }
        }
    }
}
