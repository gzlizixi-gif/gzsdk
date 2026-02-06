# gzsdk

IOH SDK Android 库（AAR），支持通过 JitPack 远程依赖。

## 其他项目如何依赖

### 方式一：JitPack（推荐，已绑定 Git 即可用）

1. **在 GitHub 上打 tag 发布**  
   在仓库中创建 Release 或打 tag（如 `v1.0.0`），JitPack 会自动根据该 tag 构建并发布。

2. **在其它项目的根目录 `settings.gradle` / `settings.gradle.kts` 中添加 JitPack 仓库：**

   ```gradle
   dependencyResolutionManagement {
       repositories {
           // ...
           maven { url = uri("https://jitpack.io") }
       }
   }
   ```

3. **在 app 的 `build.gradle` / `build.gradle.kts` 中添加依赖：**

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
