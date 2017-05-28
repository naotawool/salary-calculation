node {
    // Git から Clone
    stage 'Clone'
        checkout changelog: false, poll: false, scm: [$class: 'GitSCM', branches: [[name: '*/develop']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/naotawool/salary-calculation.git']]]

    // ビルド
    stage 'Build'
        timestamps {
            bat 'gradlew.bat clean build'
        }

    // 各種結果収集
    stage('Results') {
        parallel(
            "JUnit": {
                junit 'build/test-results/test/TEST-*.xml'
            },
            "FindBugs": {
                findbugs canComputeNew: false, pattern: 'build/findbugsReports/main.xml'
            },
            "Tasks": {
                openTasks canComputeNew: false, high: 'TODO', normal: 'FIXME', pattern: 'src/main/java/**/*.java'
            },
            "Coverage": {
                jacoco()
            }
        )
    }

    // 成果物の保存
    stage 'Archive artifacts'
        archiveArtifacts 'build/libs/salary-calculation-*.jar'

    // 人のチェック
    stage 'Permit?'
        input 'Ready to go?'

    stage 'Deploy'
        print 'Not Supported deploy :-('
}
