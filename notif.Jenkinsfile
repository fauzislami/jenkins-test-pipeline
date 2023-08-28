def retrieveLatestBuild(jobName) {
    def build = jenkins.model.Jenkins.instance.getItemByFullName(jobName).getLastBuild()
    return build
}

def printBuildResult(build) {
    echo "Job: ${build.project.name}"
    echo "Build Status: ${build.result}"
}
node {
    stage("Load Variables") {
        checkout scm
        script {
            def varsFile = load 'listOfJobs.groovy'
        }
    }
}

pipeline {
    agent any
    
    stages {
        stage('Retrieve and Print Build Results') {
            steps {
                script {
                    for (job in baseJobs) {
                        def jobName = job.job
                        def build = retrieveLatestBuild(jobName)
                        if (build) {
                            printBuildResult(build)
                        } else {
                            echo "No builds found for job: ${jobName}"
                        }
                    }
                }
            }
        }
    }
}
