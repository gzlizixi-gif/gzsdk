# gzsdk

IOH SDK Android 库（AAR），支持远程 Maven 依赖（格式：`com.ioh.clouddrive:iohsdk:v1.0.1`）或 JitPack。

## 其他项目如何依赖

### 推荐：依赖包名 `com.ioh.clouddrive:iohsdk:v1.0.1`（发布在 Git / GitHub Packages）

#### 一、本仓库：发布到 GitHub

1. **在 GitHub 创建 Personal Access Token**  
   打开 GitHub → Settings → Developer settings → Personal access tokens → Generate new token，勾选 **write:packages** 和 **read:packages**（若从私有仓库发布，还需 **repo**）。

2. **在本地配置 Token**  
   打开项目根目录的 `gradle.properties`，在 `GPR_TOKEN=` 处填写刚创建的 Token（`GITHUB_PACKAGES_REPO` 与 `GPR_USER` 已配置为 `gzlizixi-gif`）：
   ```properties
   GPR_TOKEN=刚创建的Token
   ```
   （不要提交 `GPR_TOKEN`，可把 `gradle.properties` 里这三行加入 `.gitignore`，或使用环境变量 `GPR_TOKEN`。）

3. **执行发布**
   ```bash
   .\gradlew :iohsdk:publishReleasePublicationToMavenRepository
   ```
   成功后，包会出现在 GitHub 仓库的 **Packages** 里，其他项目即可按下面方式依赖。

#### 二、其他项目：使用依赖

1. **在其它项目的 `settings.gradle` 或 `settings.gradle.kts` 里添加 GitHub Packages 仓库**（已按用户 `gzlizixi-gif`、仓库 `gzsdk` 配置）：
   ```kotlin
   dependencyResolutionManagement {
       repositories {
           google()
           mavenCentral()
           maven {
               url = uri("https://maven.pkg.github.com/gzlizixi-gif/gzsdk")
               credentials {
                   username = project.findProperty("gpr.user")?.toString() ?: System.getenv("GPR_USER") ?: ""
                   password = project.findProperty("gpr.key")?.toString() ?: System.getenv("GPR_TOKEN") ?: System.getenv("GITHUB_TOKEN") ?: ""
               }
           }
       }
   }
   ```
   若用 Groovy，写法为：
   ```groovy
   maven {
       url "https://maven.pkg.github.com/gzlizixi-gif/gzsdk"
       credentials {
           username = project.findProperty("gpr.user") ?: System.getenv("GPR_USER") ?: ""
           password = project.findProperty("gpr.key") ?: System.getenv("GPR_TOKEN") ?: System.getenv("GITHUB_TOKEN") ?: ""
       }
   }
   ```

2. **在 app 的 `build.gradle` / `build.gradle.kts` 中添加依赖：**
   ```gradle
   dependencies {
       implementation("com.ioh.clouddrive:iohsdk:v1.0.1")
   }
   ```

其他项目拉取包时也需要有权限（同一 GitHub 账号或已授权）；私有仓库时需在拉取方也配置 `gpr.user` / `gpr.key` 或 `GPR_USER` / `GPR_TOKEN`。

---

### 方式二：JitPack（已绑定 Git 即可用）

#### 如何打 tag 或创建 Release

任选其一即可，JitPack 会根据 tag 或 Release 的版本号构建。

**方法 A：用 Git 命令行打 tag（推荐）**

1. 在本地仓库根目录（`gzsdk`）确保代码已提交并推送到 GitHub：
   ```bash
   git add .
   git commit -m "准备发布 1.0.0"
   git push origin master
   ```
2. 打 tag 并推送：
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```
   版本号可自定，如 `v1.0.0`、`1.0.0`。其他项目依赖时用这个版本号，例如：`com.github.你的用户名:gzsdk:1.0.0`（带 `v` 的 tag 在 JitPack 里一般用去掉 `v` 的版本号，如 `1.0.0`，若不行可试 `v1.0.0`）。

**方法 B：在 GitHub 网页上打 tag / 创建 Release**

1. 打开你的仓库页面，例如 `https://github.com/你的用户名/gzsdk`。
2. 点击右侧 **Releases** → **Create a new release**。
3. **Choose a tag**：输入新 tag 名称（如 `v1.0.0`），选择「Create new tag」，并选择要基于的分支（如 `master`）。
4. **Release title**：可填 `v1.0.0` 或「gzsdk 1.0.0」。
5. 点击 **Publish release**。

发布后，JitPack 会自动检测到新 tag 并开始构建，可在 https://jitpack.io/#你的用户名/gzsdk 查看构建状态。

---

#### 其他项目如何引用

1. **在其它项目的根目录 `settings.gradle` / `settings.gradle.kts` 中添加 JitPack 仓库：**

   ```gradle
   dependencyResolutionManagement {
       repositories {
           // ...
           maven { url = uri("https://jitpack.io") }
       }
   }
   ```

2. **在 app 的 `build.gradle` / `build.gradle.kts` 中添加依赖：**

   ```gradle
   dependencies {
       implementation("com.github.你的GitHub用户名:gzsdk:1.0.0")  // 用 tag 或 Release 版本号
   }
   ```

   例如仓库地址是 `https://github.com/ioh/gzsdk`，则：

   ```gradle
   implementation("com.github.ioh:gzsdk:1.0.0")
   ```

首次使用某版本时，JitPack 会拉取代码并构建，可能需要几分钟；之后会使用缓存。

### 方式三：本地 Maven 测试

在本仓库根目录执行（需 Java 11+）：

```bash
.\gradlew :iohsdk:publishReleasePublicationToMavenLocal
```

其它项目在 `settings.gradle` 中增加本地 Maven 仓库后即可依赖：

```gradle
repositories {
    mavenLocal()
}
dependencies {
    implementation("com.ioh.clouddrive:iohsdk:v1.0.1")
}
```

## 发布新版本

1. 若有新 AAR，替换对应路径下的 AAR 文件，并在 `iohsdk/build.gradle.kts` 中修改 `aarFile` 路径和 `version`（如 `v1.0.2`）。
2. 提交并推送到 Git。
3. **Maven 坐标方式**：执行 `.\gradlew :iohsdk:publishReleasePublicationToMavenRepository` 发布到远程；其它项目使用 `com.ioh.clouddrive:iohsdk:v1.0.2`。
4. **JitPack 方式**：在 GitHub 上打新 tag（如 `v1.0.2`），其它项目使用 `com.github.你的用户名:gzsdk:1.0.2`。

## 项目结构

- `app/`：主应用模块（可选，仅作测试用）。
- `iohsdk/`：发布模块，负责将 AAR 以 Maven 形式发布（供 JitPack 或本地使用）。
