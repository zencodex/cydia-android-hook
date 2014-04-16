### CydiaHook
==========

利用Cydia Substrate SDK 开发，可以HOOK java层或NATIVE层 API接口。

### 本代码功能
写代码的初衷是因为有个app，需要保存数据到SD卡上，并且app里面有判断SD挂载的状态。没有SD卡，会造成程序逻辑有问题，很多功能无法正常使用，而我的手机没有装SD卡。

另外这种HOOK方式也有助于调试程序，不必因为修改ROM底层，而引起手机变砖，简便好用，目前Cydia Substrate在某些手机中可能会有不稳定的情况，我测试的机型是ZTE U969，还找不到相关ROM。

- HOOK android.os.Environment.getExternalStorageState()，强制返回，已挂在状态。
- 执行mount bind 挂载可以读写目录到 SD卡的路径，每个终端方案商的挂载点都可能不同，请根据自己的情况，修改成正确的挂载点路径。
- 可以根据自身需要，修改挂载实现函数，可以HOOK framework中的任何API。在Main.java里，比如IMEI，GPS模拟等等。

### 准备工作

- ROOT过的手机一部，没有ROOT的，请自行搜索方法。
- Eclipse 上安装 Android Substrate SDK，参考：<br>
http://www.cydiasubstrate.com/id/73e45fe5-4525-4de7-ac14-6016652cc1b8/
- 手机端去Google Play 下载安装 Cydia Substrate
- Eclipse 导入本代码，编译后安装到手机。
- 手机上运行Substrate，界面上点击“Link Substrate Files”，再点击 “Restart System（Soft）”
- 通过CydiaHook，查看挂载路径和状态。

参考官方的入门教程 VioletExample：<br>
http://www.cydiasubstrate.com/id/20cf4700-6379-4a14-9bc2-853fde8cc9d1/

=======
cydia-android-hook
==================

Cydia Substrate demo, hook getExternalStorageState, always return MOUNTED status.
