@Library('MyTestLibrary') _

node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
            getExistingJobs(jobsToTrigger: UE4_27BaseJobs, jobTemplate: "testing/template")
            getExistingJobs(jobsToTrigger: UE4_27PlatformsJobs, jobTemplate: "testing/template")
        }
    }
}

def countUE4_27BaseJobs = UE4_27BaseJobs.size()
def countUE4_27PlatformsJobs = UE4_27PlatformsJobs.size()
def parallelUE4_27BaseJobs = [:]
def parallelUE4_27PlatformsJobs = [:]

for (def i = 0; i < countUE4_27BaseJobs; i++) {
    def jobParams = UE4_27BaseJobs[i]
    parallelUE4_27BaseJobs[jobParams.job] = stageUE4_27BaseJobs(jobParams)
    println parallelUE4_27BaseJobs[jobParams.job]
}

for (def i = 0; i < countUE4_27PlatformsJobs; i++) {
    def jobParams = UE4_27PlatformsJobs[i]
    parallelUE4_27PlatformsJobs[jobParams.job] = stageUE4_27PlatformsJobs(jobParams)
    println parallelUE4_27PlatformsJobs[jobParams.job]
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

def stageUE4_27PlatformsJobs(jobParams) {
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
                        parallel parallelUE4_27BaseJobs
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
                        parallel parallelUE4_27PlatformsJobs
                    }
                }
            }
        }
    }
}
