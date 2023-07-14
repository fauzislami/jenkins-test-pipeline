@Library('MyTestLibrary') _


parameters {
    string(name: 'BaseJobs', defaultValue: 'UE4_27BaseJobs', description: '')
    string(name: 'PlatformsJobs', defaultValue: 'UE4_27PlatformsJobs', description: '')
}


node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
            getExistingJobs(jobsToTrigger: "UE4_27BaseJobs", jobTemplate: "testing/template")
            getExistingJobs(jobsToTrigger: "UE4_27PlatformsJobs", jobTemplate: "testing/template")
        }
    }
}

def countBaseJobs = UE4_27BaseJobs.size()
def countPlatformsJobs = UE4_27PlatformsJobs.size()
def parallelBaseJobs = [:]
def parallelPlatformsJobs = [:]

for (def i = 0; i < countBaseJobs; i++) {
    def jobParams = UE4_27BaseJobs[i]
    parallelBaseJobs[jobParams.job] = stageBaseJobs(jobParams)
}

for (def i = 0; i < countPlatformsJobs; i++) {
    def jobParams = UE4_27PlatformsJobs[i]
    parallelPlatformsJobs[jobParams.job] = stagePlatformsJobs(jobParams)
}

def stageBaseJobs(jobParams) {
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

def stagePlatformsJobs(jobParams) {
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
        stage('Base Jobs') {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    script {
                        parallel parallelBaseJobs
                    }                
                }
            }
        }
        stage('Jobs for Other Platforms') {
            when {
                expression { currentBuild.result != 'FAILURE' }
            }
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    script {
                        parallel parallelPlatformsJobs
                    }
                }
            }
        }
    }
}
