node {
    // Git から Clone
    stage 'Clone'
        checkout scm

    // ビルド
    stage 'Build'
        bat 'gradlew.bat clean build'

    // 各種結果収集
    stage('Results') {
        steps {
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
    }

    // 人のチェック
    stage 'Permit?'
        input 'Ready to go?'

    stage 'Deploy'
        print 'Not Supported deploy :-('
}
