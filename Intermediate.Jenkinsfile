@Library('MyTestLibrary') _

node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
            getExistingJobs(jobsToTrigger: UE${params.UEVersion}BaseJobs, jobTemplate: "testing/template")
            getExistingJobs(jobsToTrigger: UE${params.UEVersion}PlatformsJobs, jobTemplate: "testing/template")
        }
    }
}

def countUE${params.UEVersion}BaseJobs = UE${params.UEVersion}BaseJobs.size()
def countUE${params.UEVersion}PlatformsJobs = UE${params.UEVersion}PlatformsJobs.size()
def parallelUE${params.UEVersion}BaseJobs = [:]
def parallelUE${params.UEVersion}PlatformsJobs = [:]

for (def i = 0; i < countUE${params.UEVersion}BaseJobs; i++) {
    def jobParams = UE${params.UEVersion}BaseJobs[i]
    parallelUE${params.UEVersion}BaseJobs[jobParams.job] = stageUE${params.UEVersion}BaseJobs(jobParams)
}

for (def i = 0; i < countUE${params.UEVersion}PlatformsJobs; i++) {
    def jobParams = UE${params.UEVersion}PlatformsJobs[i]
    parallelUE${params.UEVersion}PlatformsJobs[jobParams.job] = stageUE${params.UEVersion}PlatformsJobs(jobParams)
}

def stageUE${params.UEVersion}BaseJobs(jobParams) {
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

def stageUE${params.UEVersion}PlatformsJobs(jobParams) {
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
                        parallel parallelUE${params.UEVersion}BaseJobs
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
                        parallel parallelUE${params.UEVersion}PlatformsJobs
                    }
                }
            }
        }
    }
}
