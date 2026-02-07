plugins {
    id("maven-publish")
}

group = "com.ioh.clouddrive"
version = "v1.0.2"

val aarFile = file("${rootProject.projectDir}/app/src/main/java/com/ioh/gzsdk/iohsdk-release-1.0.1.aar")

publishing {
    repositories {
        // 远程 Maven 仓库：在 gradle.properties 或环境变量中配置
        // MAVEN_REPO_URL、MAVEN_REPO_USER、MAVEN_REPO_PASSWORD
        maven {
            url = uri(project.findProperty("MAVEN_REPO_URL")?.toString() ?: System.getenv("MAVEN_REPO_URL") ?: "https://maven.pkg.github.com/YOUR_ORG/gzsdk")
            credentials {
                username = project.findProperty("MAVEN_REPO_USER")?.toString() ?: System.getenv("MAVEN_REPO_USER") ?: ""
                password = project.findProperty("MAVEN_REPO_PASSWORD")?.toString() ?: System.getenv("MAVEN_REPO_PASSWORD") ?: System.getenv("GITHUB_TOKEN") ?: ""
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
                url.set("https://github.com/YOUR_USERNAME/gzsdk")
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
