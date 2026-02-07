plugins {
    id("maven-publish")
}

// 依赖坐标：com.ioh.clouddrive:iohsdk:v1.0.1
group = "com.ioh.clouddrive"
version = "v1.0.1"

val aarFile = file("${rootProject.projectDir}/app/src/main/java/com/ioh/gzsdk/iohsdk-release-1.0.1.aar")

// GitHub 仓库，用于发布与 POM：在 gradle.properties 中配置 GITHUB_PACKAGES_REPO=你的用户名/gzsdk
val githubRepo = project.findProperty("GITHUB_PACKAGES_REPO")?.toString() ?: System.getenv("GITHUB_PACKAGES_REPO") ?: "gzlizixi-gif/gzsdk"
val (githubOwner, githubRepoName) = githubRepo.split("/").let { it[0] to it.getOrElse(1) { "gzsdk" } }

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/$githubOwner/$githubRepoName")
            credentials {
                username = project.findProperty("GPR_USER")?.toString() ?: System.getenv("GPR_USER") ?: System.getenv("GITHUB_ACTOR") ?: ""
                password = project.findProperty("GPR_TOKEN")?.toString() ?: System.getenv("GPR_TOKEN") ?: System.getenv("GITHUB_TOKEN") ?: ""
            }
        }
    }
    publications {
        create<MavenPublication>("release") {
            groupId = project.group.toString()
            artifactId = "iohsdk"
            version = project.version.toString()
            artifact(aarFile) {
                extension = "aar"
            }
            pom {
                name.set("iohsdk")
                description.set("IOH SDK Android library")
                url.set("https://github.com/$githubOwner/$githubRepoName")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("ioh")
                        name.set("IOH")
                    }
                }
            }
        }
    }
}
