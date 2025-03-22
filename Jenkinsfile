@Library('Jenkins-Shared-Lib') _

pipeline {
    agent {
        kubernetes {
            yaml jenkinsAgent("registry.runicrealms.com")
        }
    }

    stages {
        stage('Send Discord Notification (Build Start)') {
            steps {
                discordNotifyStart('Velagones', env.GIT_URL, env.GIT_BRANCH, env.GIT_COMMIT.take(7))
            }
        }
        stage('Determine Environment') {
            steps {
                script {
                    def branchName = env.GIT_BRANCH.replaceAll(/^origin\//, '').replaceAll(/^refs\/heads\//, '')

                    echo "Using normalized branch name: ${branchName}"

                    if (branchName == 'dev') {
                        env.ARTIFACT_SUFFIX = '-dev'
                    } else if (branchName == 'main') {
                        env.ARTIFACT_SUFFIX = ''
                    } else {
                        error "Unsupported branch: ${branchName}"
                    }
                }
            }
        }
        stage('Build and Push Artifact') {
            steps {
                container('jenkins-agent') {
                    sh """
                    ./gradlew shadowJar
                    """
                    def file = (new FileNameFinder().getFileNames('build/libs', 'velagones-*-all.jar'))[0]
                    orasPush('velagones', env.GIT_COMMIT.take(7), env.ARTIFACT_SUFFIX, file, "registry.runicrealms.com", "library")
                }
            }
        }
    }
    post {
        success {
            discordNotifySuccess('Velagones', env.GIT_URL, env.GIT_BRANCH, env.GIT_COMMIT.take(7))
        }
        failure {
            discordNotifyFail('Velagones', env.GIT_URL, env.GIT_BRANCH, env.GIT_COMMIT.take(7))
        }
    }
}
