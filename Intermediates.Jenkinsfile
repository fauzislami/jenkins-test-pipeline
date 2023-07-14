@Library('MyTestLibrary') _


parameters {
    string(name: 'BaseJobs', defaultValue: '', description: '')
    string(name: 'PlatformsJobs', defaultValue: '', description: '')
}

//def BaseJobs = "${params.BaseJobs}"
//def PlatformsJobs = "${params.PlatformsJobs}"

def convertToMap(value) {
    def map = [:]
    if (value) {
        value.split(',').each { entry ->
            def keyValue = entry.trim().split(':')
            if (keyValue.size() >= 2) {
                map[keyValue[0].trim()] = keyValue[1].trim()
            }
        }
    }
    return map
}

node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
            def baseJobsMap = convertToMap(params.BaseJobs)
            def platformsJobsMap = convertToMap(params.PlatformsJobs)
            getExistingJobs(jobsToTrigger: baseJobsMap, jobTemplate: "testing/template")
            getExistingJobs(jobsToTrigger: platformsJobsMap, jobTemplate: "testing/template")
        }
    }
}

//def countBaseJobs = BaseJobs.size()
//def countPlatformsJobs = PlatformsJobs.size()
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
