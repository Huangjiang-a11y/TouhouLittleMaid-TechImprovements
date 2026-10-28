# 车万女仆：技术性改进（Touhou Little Maid: Technical Improvements）

Touhou Little Maid 的**附属模组**：把 26.3 移植过程里做的那些技术性改进，从主模组里剥出来单独维护，
让主模组尽可能保持"官方移植该有的样子"，而这些增强/兼容作为可选的附属存在。

## 功能

| # | 内容 | 说明 |
|---|------|------|
| ① | 语音识别（STT）的 **WebSocket / OpenAI Realtime** 站点 | 复用主模组 AI 聊天，新增走 WebSocket 的实时识别站点，并适配自行断句的兼容服务 |
| ② | **自制手册书壳** | 不依赖 Patchouli 的 memorizable_gensokyo 复刻（原书 Patchouli 只到 26.1，26.3 不可用） |
| ③ | **四项原版纹理/模型替换** | 史莱姆模型（油库里）、经验球、不死图腾、经验瓶 —— 均可在配置里单独开关 |

## 依赖

- Minecraft 26.3 / Fabric Loader
- **Touhou Little Maid（主模组）** —— 本模组是其附属，单独安装无效

## 构建

本模组编译期需要主模组的 jar（放在 `libs/`，文件名见 `gradle.properties` 的 `tlm_jar_name`）。

本地构建：

```bash
# 1. 先构建主模组，或从主模组 Release 下载
cd <主模组仓库> && ./gradlew build
cp build/libs/touhoulittlemaid-fabric-<版本>.jar <本仓库>/libs/

# 2. 构建本模组
cd <本仓库> && ./gradlew build
```

产物：`build/libs/tlm-tech-improvements-<版本>.jar`

也可以直接走 GitHub Actions（`.github/workflows/build.yml` 会自己下载主模组 jar）。

## 实现要点

- 与主模组交互优先走它公开的 API / 事件（`api/` 包、`ILittleMaid` 扩展点）；
  主模组没有提供扩展点的地方，用 mixin 直接改它的类（26.3 是非混淆环境，名字天然对齐）。
- 附属模组自己的 mixin 配置文件与主模组分开：`tlm_tech.mixins.json`（通用）、`tlm_tech.client.mixins.json`（客户端）。
