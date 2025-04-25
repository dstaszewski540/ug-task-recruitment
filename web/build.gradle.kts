plugins {
  id("com.github.node-gradle.node") version "7.1.0"
}

node {
  download = false
}

tasks {
  register("build") {
    dependsOn("npm_run_build")
    copy {
      from("dist/browser")
      into("../build/resources/main/static")
      exclude("**/*.html")
    }
    copy {
      from("dist/browser/index.html")
      into("../build/resources/main/templates")
    }
  }
  register("clean") {
    delete("dist")
  }
}
