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
            def varsFile4_27 = load 'UE4_27.groovy'
            //def varsFile5_0 = load 'UE5_0.groovy'
            //def varsFile5_1 = load 'UE5_1.groovy'
            //def varsFile5_2 = load 'UE5_2.groovy'

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
