def retrieveLatestBuild(jobName) {
    def build = jenkins.model.Jenkins.instance.getItemByFullName(jobName).getLastBuild()
    return build
}

def printBuildResult(build) {
    echo "Job: ${build.project.name}"
    echo "Build Status: ${build.result}"
}

node {
    stage("Retrieve and Print Build Results") {
        checkout scm
        script {
            def varsFile = load 'UE4_27.groovy'

            for (job in BaseJobs) {
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
