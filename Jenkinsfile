@Library('Jenkins-Shared-Lib') _

pipeline {
    agent {
        kubernetes {
            yaml jenkinsAgent("registry.runicrealms.com")
        }
    }

    environment {
        ARTIFACT_NAME = 'velagones'
        PROJECT_NAME = 'Velagones'
        REGISTRY = 'registry.runicrealms.com'
        REGISTRY_PROJECT = 'library'
    }

    stages {
        stage('Send Discord Notification (Build Start)') {
            steps {
                discordNotifyStart(env.PROJECT_NAME, env.GIT_URL, env.GIT_BRANCH, env.GIT_COMMIT.take(7))
            }
        }
        stage('Determine Environment') {
            steps {
                script {
                    def branchName = env.GIT_BRANCH.replaceAll(/^origin\//, '').replaceAll(/^refs\/heads\//, '')

                    echo "Using normalized branch name: ${branchName}"

                    if (branchName == 'dev') {
                        env.RUN_MAIN_DEPLOY = 'false'
                    } else if (branchName == 'main') {
                        env.RUN_MAIN_DEPLOY = 'true'
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
                        gradle :common:crd2java
                        gradle :velocity:shadowJar
                        """
                        def jarPath = sh(script: "ls velocity/build/libs/velagones-velocity*-all.jar | head -n 1", returnStdout: true).trim()
                        orasPush(env.ARTIFACT_NAME, env.GIT_COMMIT.take(7), jarPath, env.REGISTRY, env.REGISTRY_PROJECT)
                    }
                }
            }
        }
        stage('Update Realm-Velocity Manifest') {
            steps {
                container('jenkins-agent') {
                    updateManifest('dev', 'Realm-Velocity', 'plugin-manifest.yaml', env.ARTIFACT_NAME, env.GIT_COMMIT.take(7), env.REGISTRY, env.REGISTRY_PROJECT)
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
            discordNotifySuccess(env.PROJECT_NAME, env.GIT_URL, env.GIT_BRANCH, env.GIT_COMMIT.take(7))
        }
        failure {
            discordNotifyFail(env.PROJECT_NAME, env.GIT_URL, env.GIT_BRANCH, env.GIT_COMMIT.take(7))
        }
    }
}
