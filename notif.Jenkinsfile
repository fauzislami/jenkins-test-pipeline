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
            def varsFiles = ["UE4_27.groovy", "UE5_0.groovy", "UE5_1.groovy"]

            for (varsFile in varsFiles) {
                def varsFile = load varsFile

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
}
