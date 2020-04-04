package com.example.dataclean.ksbaopro

import com.blankj.utilcode.util.FileIOUtils
import com.example.dataclean.entity.BuildBean
/**
 * @author : C4_雍和
 * 描述 :生成debug中的Application文件
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-4 下午7:09
 */
object DebugApplicationFileUtil {
    private const val debugfilePath = "src/main/debug/java/debug/"
    fun generateFile(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.filePath + debugfilePath + "ModuleApplication.kt",
            "package debug\n" +
                    "\n" +
                    "import android.app.Application\n" +
                    "import android.net.Uri\n" +
                    "import com.billy.cc.core.component.CC\n" +
                    "import com.google.gson.GsonBuilder\n" +
                    "import com.sunchen.netbus.NetStatusBus\n" +
                    "import com.yingsoft.ksbao.baselib.constants.Constants\n" +
                    "import com.yingsoft.ksbao.baselib.entity.UserLoginBean\n" +
                    "import com.yingsoft.ksbao.baselib.util.ObjectBox\n" +
                    "import com.yingsoft.ksbao.baselib.util.UserInfoCache\n" +
                    "import timber.log.Timber\n" +
                    "\n" +
                    "class ModuleApplication : Application() {\n" +
                    "    override fun onCreate() {\n" +
                    "        super.onCreate()\n" +
                    "        CC.enableVerboseLog(true)\n" +
                    "        CC.enableDebug(true)\n" +
                    "        CC.enableRemoteCC(true)\n" +
                    "        Timber.plant(Timber.DebugTree())\n" +
                    "        ObjectBox.init(this)\n" +
                    "        NetStatusBus.getInstance().init(this)\n" +
                    "        initUserInfo()\n" +
                    "    }\n" +
                    "\n" +
                    "    private fun initUserInfo() {\n" +
                    "        val contentResolver = com.blankj.utilcode.util.Utils.getApp().contentResolver\n" +
                    "        val cursor = contentResolver.query(Uri.parse(Constants.KAO_SHI_PROVIDER), null, null, null, null)\n" +
                    "        if (cursor != null)\n" +
                    "            if (cursor.moveToNext()) {\n" +
                    "                val result = cursor.getString(cursor.getColumnIndex(\"KaoShiBaoProvider\"))\n" +
                    "                val json = GsonBuilder().create().fromJson(result, UserLoginBean.UserLoginInfo::class.java)\n" +
                    "                UserInfoCache.getInstance().userInfo = json\n" +
                    "            }\n" +
                    "    }\n" +
                    "\n" +
                    "}\n"
        )
    }
}