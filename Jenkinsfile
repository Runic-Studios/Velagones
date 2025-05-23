@Library('Jenkins-Shared-Lib') _

pipeline {
    agent {
        kubernetes {
            yaml jenkinsAgent(["agent-java-21": "registry.runicrealms.com/jenkins/agent-java-21:latest"])
        }
    }

    environment {
        ARTIFACT_VELOCITY_NAME = 'velagones-velocity'
        ARTIFACT_PAPER_NAME = 'velagones-paper'
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
        stage('Build and Push Artifacts') {
            steps {
                container('agent-java-21') {
                    script {
                        sh """
                        ./gradlew :velocity:shadowJar :paper:shadowJar --no-daemon
                        """
                        orasPush(env.ARTIFACT_VELOCITY_NAME, env.GIT_COMMIT.take(7), "velocity/build/libs/velagones-velocity-all.jar", env.REGISTRY, env.REGISTRY_PROJECT)
                        orasPush(env.ARTIFACT_PAPER_NAME, env.GIT_COMMIT.take(7), "paper/build/libs/velagones-paper-all.jar", env.REGISTRY, env.REGISTRY_PROJECT)
                    }
                }
            }
        }
        stage('Update Realm-Velocity and Realm-Paper Manifests') {
            steps {
                container('agent-java-21') {
                    updateManifest('dev', 'Realm-Velocity', 'artifact-manifest.yaml', env.ARTIFACT_VELOCITY_NAME, env.GIT_COMMIT.take(7), 'artifacts.velagones.tag')
                    updateManifest('dev', 'Realm-Paper', 'artifact-manifest.yaml', env.ARTIFACT_PAPER_NAME, env.GIT_COMMIT.take(7), 'artifacts.velagones.tag')
                }
            }
        }
        stage('Publish to Maven Nexus (Prod Only)') {
            when {
                expression { return env.RUN_MAIN_DEPLOY == 'true' }
            }
            steps {
                container('agent-java-21') {
                    nexusPublish()
                }
            }
        }
        stage('Create PRs to Promote Realm-Velocity and Realm-Paper Dev to Main (Prod Only)') {
            when {
                expression { return env.RUN_MAIN_DEPLOY == 'true' }
            }
            steps {
                container('agent-java-21') {
                    createPR('Velagones', 'Realm-Velocity', 'dev', 'main')
                    createPR('Velagones', 'Realm-Paper', 'dev', 'main')
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
