@Library('MyTestLibrary') _

node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
            getExistingJobs(jobToTrigger: [UE4_27BaseJobs, UE5_0BaseJobs, UE5_1BaseJobs, UE5_2BaseJobs], jobTemplate: "testing/intermediate-template")
        }
    }
}

def countIntermediatePipelines = intermediatePipelines.size()
def parallelIntermediatePipelines = [:]

for (def i = 0; i < countIntermediatePipelines; i++) {
    def jobParams = intermediatePipelines[i]
    parallelIntermediatePipelines[jobParams.job] = stageIntermediatePipelines(jobParams)
}

def stageIntermediatePipelines(jobParams) {
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
        stage('Intermediate Pipelines') {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    script {
                        parallel parallelIntermediatePipelines
                    }                
                }
            }
        }
    }
}
