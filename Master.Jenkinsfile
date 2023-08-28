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
            def varsFile5_0 = load 'UE5_0.groovy'
            def varsFile5_1 = load 'UE5_1.groovy'
            def varsFile5_2 = load 'UE5_2.groovy'
        }
    }
}

pipeline {
    agent any

    stages {
       stage('Triggering Intermediate Jobs') {
            parallel {
             stage('Intermediate-ue4_27') {
                 steps {
                     build job: 'testing/Intermediates/Intermediate-ue4_27', parameters: [string(name: 'UEVersion', value: '4.27')], wait: true
                 }
             }
             stage('Intermediate-ue5_0') {
                 steps {
                     build job: 'testing/Intermediates/Intermediate-ue5_0', parameters: [string(name: 'UEVersion', value: '5.0')], wait: true
                 }
             }
             stage('Intermediate-ue5_1') {
                 steps {
                     build job: 'testing/Intermediates/Intermediate-ue5_1', parameters: [string(name: 'UEVersion', value: '5.1')], wait: true
                 }
             }
             stage('Intermediate-ue5_2') {
                 steps {
                     build job: 'testing/Intermediates/Intermediate-ue5_2', parameters: [string(name: 'UEVersion', value: '5.2')], wait: true
                 }
             }
          }
       }
    }
}
