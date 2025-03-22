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
                    script {
                        sh """
                        ./gradlew shadowJar
                        """
                        def jarPath = sh(script: "ls build/libs/velagones-*-all.jar | head -n 1", returnStdout: true).trim()
                        orasPush('velagones', env.GIT_COMMIT.take(7), env.ARTIFACT_SUFFIX, jarPath, "registry.runicrealms.com", "library")
                    }
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
