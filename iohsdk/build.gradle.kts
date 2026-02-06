plugins {
    id("maven-publish")
}

group = "com.ioh"
version = "1.0.1"

val aarFile = file("${rootProject.projectDir}/app/src/main/java/com/ioh/gzsdk/iohsdk-release-1.0.1.aar")

publishing {
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
