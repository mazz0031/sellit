eventCompileStart = { kind ->
    executeNpmInstall()
    executeGruntTasks()
}

//private void executeNpmInstall() {
//    Process proc = (new ProcessBuilder(
//            "C:\\Program Files\\nodejs\\npm.cmd",
//            "install"
//    )).start()
//    //def npmInstall = 'npm install'
//    println "| npm install..."
//    //def proc = npmInstall.execute()
//    proc.waitFor()
//    if (proc.exitValue() != 0) {
//        println "Error installing npm dependencies : ${proc.err.text}"
//        println "Output: ${proc.in.text}"
//    }
//}

private void executeNpmInstall() {

    def npmInstall
    if (System.properties['os.name'].toLowerCase().contains('windows')){
        npmInstall = "cmd.exe /C npm install"
    }
    else {
        npmInstall = "npm install"
    }

    println "| npm install..."
    def proc = npmInstall.execute()
    proc.waitFor()
    def x = proc.exitValue()
    if (proc.exitValue() != 0) {
        println "Error installing npm dependencies"
        println "Output: ${proc.in.text}"
    }
}


//private void executeGruntTasks() {
//    Process proc = (new ProcessBuilder(
//            "C:\\MSSE\\Repos\\sellit\\node_modules\\.bin\\grunt.cmd"
//    )).start()
//    println "| Load js dependencies from cache..."
//    proc.waitFor() // execute default task to load dependencies from local cache.
//    if (proc.exitValue() != 0) {
//        println "| Error occured while loading dependencies from local cache : ${proc.err.text}"
//        println "| Try loading dependencies from web..."
//        proc = (new ProcessBuilder(
//                "C:\\MSSE\\Repos\\sellit\\node_modules\\.bin\\grunt.cmd"
//        )).start()
//        proc.waitFor()                               // Wait for the command to finish
//        println "Output: ${proc.in.text}"
//    }
//}

private void executeGruntTasks() {
    def gruntScript
    if (System.properties['os.name'].toLowerCase().contains('windows')){
        gruntScript = "cmd.exe /C grunt"
    }
    else {
        gruntScript = "node_modules/.bin/grunt"
    }
    println "| Load js dependencies from cache..."
    def proc = gruntScript.execute()  // execute default task to load dependencies from local cache.
    proc.waitFor()
    if (proc.exitValue() != 0) {
        println "| Error occured while loading dependencies from local cache : ${proc.err.text}"
        println "| Try loading dependencies from web..."
        proc = gruntScript.execute()
        proc.waitFor()                               // Wait for the command to finish
        println "Output: ${proc.in.text}"
    }
}