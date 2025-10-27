pipeline {
    agent any

    // Jenkins 내 설정한 환경 변수와 파이프라인에서 사용할 환경 변수
    environment {
        MAIN_BRANCH = 'main'
        DEVELOP_BRANCH = 'dev'
        AWS_REGION = 'ap-northeast-2'
        // S3_BUCKET은 Setup 단계에서 동적으로 설정됩니다.
    }

    stages {
        stage('Setup') {
            steps {
                script {
                    // 태그를 기반으로 S3 버킷 이름을 동적으로 조회
                    def resourceArn = sh(
                        returnStdout: true,
                        script: """
                            aws resourcegroupstaggingapi get-resources \
                                --resource-type-filters s3 \
                                --tag-filters Key=Name,Values='App Artifacts Bucket' Key=Environment,Values='${env.DEVELOP_BRANCH}' \
                                --query 'ResourceTagMappingList[0].ResourceARN' \
                                --output text
                        """
                    ).trim()

                    if (resourceArn && resourceArn != 'None') {
                        env.S3_BUCKET = resourceArn.split(':')[-1]
                        echo "Successfully found S3 bucket: ${env.S3_BUCKET}"
                    } else {
                        error "Could not find S3 bucket for Environment: ${env.DEVELOP_BRANCH}"
                    }
                }
            }
        }

        // 깃 브랜치 체크
        stage('Checkout Git Branch') {

            // main 브랜치, develop 브랜치에 대한 변경인 경우에 대해 모두 git clone 진행
            when() {
                expression { env.GIT_BRANCH == MAIN_BRANCH || env.GIT_BRANCH == DEVELOP_BRANCH }
            }

            // Git Clone
            steps {
                echo 'Clean Workspace'
                cleanWs()
                echo 'Checkout Git Branch and Clone Start'
                checkout([$class: 'GitSCM',
                          branches: [[name: env.GIT_BRANCH]],
                          doGenerateSubmoduleConfigurations: false,
                          userRemoteConfigs: [[credentialsId: 'GITHUB_ACCESS_TOKEN', url: GITHUB_REPOSITORY]]
                          ])
            }

            post {
                failure {
                    echo 'Clone fail'
                    withCredentials([string(credentialsId: 'Discord_Jenkins_Bot', variable: 'DISCORD')]) {
                        discordSend title: "CLONE FAIL",
                        description: "깃허브에서 코드를 클론하는데 실패했습니다.",
                        footer: "'${env.JOB_NAME}'",
                        link: env.BUILD_URL,
                        result: currentBuild.currentResult,
                        webhookURL: "$DISCORD"
                    }
                }
                success {
                    echo 'Clone success'
                    withCredentials([string(credentialsId: 'Discord_Jenkins_Bot', variable: 'DISCORD')]) {
                        discordSend title: "CLONE SUCCESS", 
                        description: "깃허브에서 코드를 클론하는데 성공했습니다.", 
                        footer: "'${env.JOB_NAME}'", 
                        link: env.BUILD_URL,
                        result: currentBuild.currentResult, 
                        webhookURL: "$DISCORD"
                    }
                }
            }
        }

        // 각 마이크로서비스별로 병렬 처리하여 빌드
        stage('Build') {

            // main 브랜치, dev 브랜치 모두 빌드는 동일하게 진행
            when() {
                expression { env.GIT_BRANCH == MAIN_BRANCH || env.GIT_BRANCH == DEVELOP_BRANCH }
            }

            steps {
                // gradle build 스텝 정의
                echo 'Gradle Build Start'
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }
        
        // S3에 JAR 파일 업로드
        stage('Upload to S3') {
            when() {
                expression { env.GIT_BRANCH == MAIN_BRANCH || env.GIT_BRANCH == DEVELOP_BRANCH }
            }

            steps {
                echo "Uploading JAR to S3 bucket: ${env.S3_BUCKET}"
                sh 'aws s3 cp build/libs/*.jar s3://${S3_BUCKET}/'
            }

            post {
                success {
                    echo 'Upload to S3 success'
                    withCredentials([string(credentialsId: 'Discord_Jenkins_Bot', variable: 'DISCORD')]) {
                        discordSend title: "S3 UPLOAD SUCCESS",
                        description: "S3에 JAR 파일을 업로드하는데 성공했습니다.",
                        footer: "'${env.JOB_NAME}'",
                        link: env.BUILD_URL,
                        result: currentBuild.currentResult,
                        webhookURL: "$DISCORD"
                    }
                }
                failure {
                    echo 'Upload to S3 fail'
                    withCredentials([string(credentialsId: 'Discord_Jenkins_Bot', variable: 'DISCORD')]) {
                        discordSend title: "S3 UPLOAD FAIL",
                        description: "S3에 JAR 파일을 업로드하는데 실패했습니다.",
                        footer: "'${env.JOB_NAME}'",
                        link: env.BUILD_URL,
                        result: currentBuild.currentResult,
                        webhookURL: "$DISCORD"
                    }
                }
            }
        }
    }

    post {
        success {
            withCredentials([string(credentialsId: 'Discord_Jenkins_Bot', variable: 'DISCORD')]) {
                discordSend title: "BUILD SUCCESS", 
                description: "빌드를 성공했습니다.", 
                footer: "'${env.JOB_NAME}'", 
                link: env.BUILD_URL,
                result: currentBuild.currentResult, 
                webhookURL: "$DISCORD"
            }
        }
        failure {
            withCredentials([string(credentialsId: 'Discord_Jenkins_Bot', variable: 'DISCORD')]) {
                discordSend title: "BUILD FAIL", 
                description: "빌드를 실패했습니다.", 
                footer: "'${env.JOB_NAME}'", 
                link: env.BUILD_URL,
                result: currentBuild.currentResult, 
                webhookURL: "$DISCORD"
            }
        }
    }
}