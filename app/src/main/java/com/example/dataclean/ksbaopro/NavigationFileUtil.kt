package com.example.dataclean.ksbaopro

import com.blankj.utilcode.util.FileIOUtils
import com.example.dataclean.entity.BuildBean
/**
 * @author : C4_雍和
 * 描述 :生成总线文件
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-4 下午9:58
 */
object NavigationFileUtil {
    fun generateFile(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.packageRoute + "utils/" + "NavigationUtils.kt",
            "package ${buildBean.packageName}.utils\n" +
                    "\n" +
                    "import com.billy.cc.core.component.CC\n" +
                    "\n" +
                    "object NavigationUtils {\n" +
                    "    /**\n" +
                    "     * 去往联网失败页面\n" +
                    "     */\n" +
                    "    fun goNetErrorActivity() {\n" +
                    "        CC.obtainBuilder(\"ComponentA\")\n" +
                    "            .setActionName(\"ErrorTwoActivity\")\n" +
                    "            .build()\n" +
                    "            .call()\n" +
                    "    }\n" +
                    "}"
        )
    }
}