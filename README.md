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
- 手册按钮：主模组把女仆界面侧栏那个按钮改成统一发 `OpenPatchouliBookEvent`，装了 Patchouli 由它自己开，
  没装则由本模组监听同一事件打开自研书壳 —— 主模组因此不需要知道本模组存在。
- 油库里模型：主模组的 `InternalBedrockModelRegistry.addEntityModel` 把命名空间写死成它自己，
  本模组改成直接往公开的 `ENTITY_MODELS` 表里注册 `tlm_tech:` 的 id，并用一个 mixin 挂在
  `ClientReloadListenerRegistry.onRegisterClientReloadListeners` 开头，保证早于主模组冻结那张表。
- 书页的实体渲染：主模组保留通用部分（`client/renderer/EntityPreviewRenderer` 的 `state` / `drawCentered`，
  JEI 的祭坛配方预览也在用），本模组的 `EntityPageRenderer` 只负责书页语义并调用前者。

## 命名空间上的一个例外

手册物品的 id 是 `touhou_little_maid:memorizable_gensokyo_manual`，沿用主模组的命名空间，不是本模组的。
物品 id 会直接写进存档的 NBT，改动会让玩家已有的手册变成空气；其余东西（配置、书 id、渲染器、
数据包内容）都不进存档，所以只有这一条破例。物品定义、模型、贴图也就跟着落在
`assets/touhou_little_maid/` 下（物品定义文件的路径由物品 id 强制决定）。

书的内容仍然留在 `tlm_tech:` 命名空间：书的分类 id 是由书 id 的命名空间推导出来的
（`BookContentLoader` 里是 `namespace + ":" + 相对路径`，条目里写着 `"category": "tlm_tech:maid"`），
书 id 一动，50 个条目的分类引用就全部失效、整本书会变空，而书 id 并不存在于存档里，
改了没有任何兼容收益。
