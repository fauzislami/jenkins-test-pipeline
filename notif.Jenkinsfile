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
            def varsFile = load 'listOfJobs.groovy'
            def listOfMaps = [
                [baseJobInMap: UE4_27BaseJobs, platformJobInMap: UE4_27PlatformsJobs, UEVersion: "4.27"],
                [baseJobInMap: UE5_0BaseJobs, platformJobInMap: UE5_0PlatformsJobs, UEVersion: "5.0"],
                [baseJobInMap: UE5_1BaseJobs, platformJobInMap: UE5_1PlatformsJobs, UEVersion: "5.1"],
                [baseJobInMap: UE5_2BaseJobs, platformJobInMap: UE5_2PlatformsJobs, UEVersion: "5.2"],
                ]

            listOfMaps.each { map -> 
                if (map.UEVersion == params.UEVersion) {
                    for (job in map.baseJobInMap) {
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
