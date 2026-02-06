# gzsdk

IOH SDK Android 库（AAR），支持通过 JitPack 远程依赖。

## 其他项目如何依赖

### 方式一：JitPack（推荐，已绑定 Git 即可用）

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

### 方式二：本地 Maven 测试

在本仓库根目录执行（需 Java 11+）：

```bash
./gradlew :iohsdk:publishReleasePublicationToMavenLocal
```

其它项目在 `settings.gradle` 中增加本地 Maven 仓库后即可依赖：

```gradle
repositories {
    mavenLocal()
}
dependencies {
    implementation("com.ioh:iohsdk:1.0.0")
}
```

## 发布新版本

1. 若有新 AAR，替换 `app/src/main/java/com/ioh/gzsdk/iohsdk-release-1.0.0.aar`，或在 `iohsdk/build.gradle.kts` 中修改 `aarFile` 路径和 `version`。
2. 提交并推送到 Git。
3. 在 GitHub 上打新 tag（如 `v1.0.1`），其它项目即可使用 `com.github.你的用户名:gzsdk:1.0.1`。

## 项目结构

- `app/`：主应用模块（可选，仅作测试用）。
- `iohsdk/`：发布模块，负责将 AAR 以 Maven 形式发布（供 JitPack 或本地使用）。
