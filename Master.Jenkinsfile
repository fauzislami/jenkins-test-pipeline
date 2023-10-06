//def retrieveLatestBuild(jobName) {
//    def build = jenkins.model.Jenkins.instance.getItemByFullName(jobName).getLastBuild()
//    return build
//}

@Library('MyTestLibrary') _

pipeline {
    agent any

    stages {
       stage('Triggering Intermediate Jobs') {
            parallel {
             stage('Intermediate-ue4_27') {
                 steps {
                     //build job: 'testing/Intermediates/Intermediate-ue4_27', parameters: [string(name: 'UEVersion', value: '4.27')], wait: true
                    echo "test"
                 }
             }
             stage('Intermediate-ue5_0') {
                 steps {
                     //build job: 'testing/Intermediates/Intermediate-ue5_0', parameters: [string(name: 'UEVersion', value: '5.0')], wait: true
                    echo "test"
                 }
             }
             stage('Intermediate-ue5_1') {
                 steps {
                     //build job: 'testing/Intermediates/Intermediate-ue5_1', parameters: [string(name: 'UEVersion', value: '5.1')], wait: true
                    echo "test"
                 }
             }
             stage('Intermediate-ue5_2') {
                 steps {
                     //build job: 'testing/Intermediates/Intermediate-ue5_2', parameters: [string(name: 'UEVersion', value: '5.2')], wait: true
                    echo "test"
                 }
             }
          }
       }
    }
    post {
         always {
            script{
                //def groovyFiles = ["UE4_27.groovy", "UE5_0.groovy", "UE5_1.groovy", "UE5_2.groovy"]
                //slackNotif(groovyFiles)
                //loadVars(groovyFiles)
                def message = "The following intermediate jobs failed:\n" + failedJobs.join('\n')
                slackSend(channel: '#jenkins-notif-test', message: message, color: 'danger')
            }
         }
     }
}
