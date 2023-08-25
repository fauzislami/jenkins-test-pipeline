@Library('MyTestLibrary') _

parameters {
    string(name: 'UEVersion', defaultValue: '', description: '')
}

def parallelBaseJobs = [:]
def parallelPlatformsJobs = [:]

def stageBaseJobs(jobParams) {
    return {
        stage("stage: ${jobParams.job}") {
            def triggeredJobs = build job: jobParams.job, parameters: jobParams.params, propagate: true, wait: true
            def buildResult = triggeredJobs.getResult()
            //println "Build result for ${jobParams.job}: ${buildResult}"
            println "=====${buildResult}====="
            if (buildResult != 'SUCCESS') {
                slackSend(channel: "#jenkins-notif-test", message: "Job ${env.JOB_NAME} is failed")
                //error "${jobParams.job} failed"
            }
        }
    }
}

def stagePlatformsJobs(jobParams) {
    return {
        stage("stage: ${jobParams.job}") {
            def triggeredJobs = build job: jobParams.job, parameters: jobParams.params, propagate: true, wait: true
            def buildResult = triggeredJobs.getResult()
            echo $buildResult
            if (buildResult != 'SUCCESS') {
                error "${jobParams.job} failed"
                //send notif to slack or email
            }
        }
    }
}

node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
            def listOfMaps = [
                [baseJobInMap: UE4_27BaseJobs, platformJobInMap: UE4_27PlatformsJobs, UEVersion: "4.27"],
                [baseJobInMap: UE5_0BaseJobs, platformJobInMap: UE5_0PlatformsJobs, UEVersion: "5.0"],
                [baseJobInMap: UE5_1BaseJobs, platformJobInMap: UE5_1PlatformsJobs, UEVersion: "5.1"],
                [baseJobInMap: UE5_2BaseJobs, platformJobInMap: UE5_2PlatformsJobs, UEVersion: "5.2"],
                ]

            listOfMaps.each { map -> 
                if (map.UEVersion == params.UEVersion) {
                    getExistingJobs(jobsToTrigger: map.baseJobInMap, jobTemplate: "testing/template")
                    getExistingJobs(jobsToTrigger: map.platformJobInMap, jobTemplate: "testing/template")

                    def countBaseJobs = map.baseJobInMap.size()
                    def countPlatformsJobs = map.platformJobInMap.size()
                    for (def i = 0; i < countBaseJobs; i++) {
                        def jobParams = map.baseJobInMap[i]
                        parallelBaseJobs[jobParams.job] = stageBaseJobs(jobParams)
                    }

                    for (def i = 0; i < countPlatformsJobs; i++) {
                        def jobParams = map.platformJobInMap[i]
                        parallelPlatformsJobs[jobParams.job] = stagePlatformsJobs(jobParams)
                    }
                }
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
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
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
