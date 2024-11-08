pipeline {
  agent any
  tools {
    maven 'maven-3.9.2'
  }
  stages {
    stage ('Build') {
      steps {
        bat 'mvn clean -Dmaven.test.skip package'
      }
    }
    stage ('Deploy') {
      steps {
        script {
         deploy adapters: [tomcat9(credentialsId: 'tomcatId', path:'', url: 'http://192.168.2.222:5051')], contextPath:'', onFailure: false, war:'**/target/mysales.war' 
        }
      }
    }
  }
}
