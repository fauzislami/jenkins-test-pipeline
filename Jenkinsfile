@Library('MyTestLibrary') _

node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
//             def firstJobs = varsFile.collect { it }
            getExistingJobs(jobsToTrigger: firstJob, jobTemplate: "testing/test-1")
        }
    }
}

def count = firstJob.size()
def parallelJobs = [:]

for (def i = 0; i < count; i++) {
    def jobParams = firstJob[i]
    parallelJobs[jobParams.job] = generateStage(jobParams)
}

def generateStage(jobParams) {
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
                        parallel parallelJobs
                    }                
                }
            }
        }
        stage('Second Job') {
            when {
                expression { currentBuild.result != 'FAILURE' }
            }
            steps {
                echo "Hello Again!"
                getExistingJobs(jobsToTrigger: secondJob, jobTemplate: "testing/test-1")
            }
        }
    }
}
