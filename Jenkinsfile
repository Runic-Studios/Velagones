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
                        env.RUN_MAIN_DEPLOY = 'false'
                        env.ARTIFACT_SUFFIX = '-dev'
                    } else if (branchName == 'main') {
                        env.RUN_MAIN_DEPLOY = 'true'
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
                        gradle crd2java
                        gradle shadowJar
                        """
                        def jarPath = sh(script: "ls build/libs/velagones-*-all.jar | head -n 1", returnStdout: true).trim()
                        orasPush('velagones', env.GIT_COMMIT.take(7) + env.ARTIFACT_SUFFIX, jarPath, "registry.runicrealms.com", "library")
                    }
                }
            }
        }
        stage('Update Realm-Velocity Manifest') {
            steps {
                container('jenkins-agent') {
                    updateManifest('dev', 'Realm-Velocity', 'plugin-manifest.yaml', 'velagones', env.GIT_COMMIT.take(7) + env.ARTIFACT_SUFFIX, "registry.runicrealms.com", "library")
                }
            }
        }
        stage('Create PR to Promote Realm-Velocity Dev to Main (Prod Only)') {
            when {
                expression { return env.RUN_MAIN_DEPLOY == 'true' }
            }
            steps {
                container('jenkins-agent') {
                    createPR('Velagones', 'Realm-Velocity', 'dev', 'main')
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
