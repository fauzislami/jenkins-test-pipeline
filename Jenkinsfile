@Library('MyTestLibrary') _

// def jobsToTrigger = [
//     [job: 'test-1', params: [string(name: 'city', value: 'bogor'), string(name: 'province', value: 'west java')]],
//     [job: 'test-2', params: [string(name: 'city', value: 'jogja',), string(name: 'province', value: 'jogja')]],
//     [job: 'test-3', params: [string(name: 'city', value: 'bandung'), string(name: 'province', value: 'west java')]],
//     [job: 'test-4', params: [string(name: 'city', value: 'jakarta'), string(name: 'province', value: 'jakarta')]],
//     [job: 'test-5', params: [string(name: 'city', value: 'semarang'), string(name: 'province', value: 'central java')]],
//     [job: 'test-6', params: [string(name: 'city', value: 'surabaya'), string(name: 'province', value: 'east java')]],
//     [job: 'test-7', params: [string(name: 'city', value: 'medan'), string(name: 'province', value: 'east sumatera')]]
// ]

node {
    stage("Load Variables") {
        checkout scm
        def varsFile = "listOfJobs.groovy"
        def content = readFile varsFile
        def a = content.jobsToTrigger
        getExistingJobs(jobsToTrigger: content.a)
    }
}

// getExistingJobs()

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
