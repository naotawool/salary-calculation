node {
    // Git から Clone
    stage 'Clone'
        checkout changelog: false, poll: false, scm: [$class: 'GitSCM', branches: [[name: '*/develop']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/naotawool/salary-calculation.git']]]

    // ビルド
    stage 'Build'
        bat 'gradlew.bat clean build'

    // 各種結果収集
    stage 'Results'
        junit 'build/test-results/test/TEST-*.xml'
        findbugs canComputeNew: false, pattern: 'build/findbugsReports/main.xml'
        openTasks canComputeNew: false, high: 'TODO', normal: 'FIXME', pattern: 'src/main/java/**/*.java'
        jacoco()

    // 人のチェック
    stage 'Permit?'
        input 'Ready to go?'

    stage 'Deploy'
        print 'Not Supported deploy :-('
}
