package com.example.dataclean.ksbaopro

import com.blankj.utilcode.util.FileIOUtils
import com.example.dataclean.entity.BuildBean
/**
 * @author : C4_雍和
 * 描述 :生成ComponentUtil文件
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-4 下午6:03
 */
object ComponentFileUtil {
    fun generateFile(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.packageRoute+"ComponentUtil.kt",
            "package ${buildBean.packageName}\n" +
                    "\n" +
                    "import com.billy.cc.core.component.CC\n" +
                    "import com.billy.cc.core.component.CCResult\n" +
                    "import com.billy.cc.core.component.CCUtil\n" +
                    "import com.billy.cc.core.component.IComponent\n" +
                    "import ${buildBean.packageName}.view.${buildBean.activityName}\n" +
                    "\n" +
                    "class ComponentUtil : IComponent {\n" +
                    "    override fun getName(): String {\n" +
                    "        return \"Component${buildBean.suffix}\"\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun onCall(cc: CC): Boolean {\n" +
                    "        return when (cc.actionName) {\n" +
                    "            \"${buildBean.activityName}\" -> {\n" +
                    "                CCUtil.navigateTo(cc, ${buildBean.activityName}::class.java)\n" +
                    "                CC.sendCCResult(cc.callId, CCResult.success())\n" +
                    "                false\n" +
                    "            }\n" +
                    "            else -> {\n" +
                    "                CC.sendCCResult(cc.callId, CCResult.errorUnsupportedActionName())\n" +
                    "                false\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n"
        )
    }
}