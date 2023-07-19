@Library('MyTestLibrary') _

parameters {
    string(name: 'UEVersion', defaultValue: '', description: '')
}

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

def listOfMaps = [
  [baseJobInMap: UE4_27BaseJobs, platformJobInMap: UE4_27PlatformsJobs, UEVersion: '4.27'],
  [baseJobInMap: UE5_0BaseJobs, platformJobInMap: UE5_0PlatformsJobs, UEVersion: '5.0'],
  [baseJobInMap: UE5_1BaseJobs, platformJobInMap: UE5_1PlatformsJobs, UEVersion: '5.1'],
  [baseJobInMap: UE5_2BaseJobs, platformJobInMap: UE5_2PlatformsJobs, UEVersion: '5.2'],
]

listOfMaps.each { map ->
  if (map.UEVersion == params.UEVersion) {

    def countBaseJobs = map.baseJobInMap.size()
    def countPlatformsJobs = map.platformJobInMap.size()
    def parallelBaseJobs = [:]
    def parallelPlatformsJobs = [:]

    for (def i = 0; i < countBaseJobs; i++) {
        def jobParams = map.baseJobInMap[i]
        parallelBaseJobs[jobParams.job] = stageBaseJobs(jobParams)
        println parallelBaseJobs[jobParams.job]
    }

    for (def i = 0; i < countPlatformsJobs; i++) {
        def jobParams = map.platformJobInMap[i]
        parallelPlatformsJobs[jobParams.job] = stagePlatformsJobs(jobParams)
        println parallelPlatformsJobs[jobParams.job]
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
