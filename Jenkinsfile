@Library('MyTestLibrary') _

node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
            def jobs = varsFile.collect { it }
//             getExistingJobs(jobsToTrigger: jobs)
            getExistingJobs(jobsToTrigger: jobs, "testing/test-1")
        }
    }
}

def count = jobsToTrigger.size()
def parallelJobs = [:]

for (def i = 0; i < count; i++) {
    def jobParams = jobsToTrigger[i]
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
        stage('Hello') {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    script {
                        parallel parallelJobs
                    }                
                }
            }
        }
        stage('Hello-Again') {
            when {
                expression { currentBuild.result != 'FAILURE' }
            }
            steps {
                echo "Hello Again!"
            }
        }
    }
}
