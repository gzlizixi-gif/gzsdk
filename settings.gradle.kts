pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
val gradleProps = java.util.Properties().apply {
    file("gradle.properties").takeIf { it.exists() }?.reader()?.use { load(it) }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // 解析 com.ioh.clouddrive:iohsdk（本地发布或 GitHub Packages）
        mavenLocal()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/" + (gradleProps.getProperty("GITHUB_PACKAGES_REPO") ?: System.getenv("GITHUB_PACKAGES_REPO") ?: "gzlizixi-gif/gzsdk"))
            credentials {
                username = gradleProps.getProperty("GPR_USER") ?: System.getenv("GPR_USER") ?: ""
                password = gradleProps.getProperty("GPR_TOKEN") ?: System.getenv("GPR_TOKEN") ?: System.getenv("GITHUB_TOKEN") ?: ""
            }
        }
    }
}

rootProject.name = "gzsdk"
include(":app")
include(":iohsdk")
 