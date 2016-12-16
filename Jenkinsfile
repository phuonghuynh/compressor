#! groovy

node('swarm') {
    stage('Test') {
        def dockerenv = docker.image('ubuntu:14.04')
        dockerenv.inside {
            withEnv(["PROCESSING_URL=https://api-staging.finix.io/"]){
                checkout scm
                sh "apt-get update && apt-get install -y libelf1 unzip software-properties-common wget openssh-client git curl python python-dev python-pip"
                sh 'python -V'
                sh 'python setup.py develop'
                sh 'python setup.py test'
            }
        }
    }
}
