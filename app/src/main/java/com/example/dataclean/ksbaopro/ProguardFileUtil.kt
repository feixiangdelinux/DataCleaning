package com.example.dataclean.ksbaopro

import com.blankj.utilcode.util.FileIOUtils
import com.example.dataclean.entity.BuildBean
/**
 * @author : C4_雍和
 * 描述 :生成proguard-rules.pro文件
 * 主要功能 :混淆文件生成
 * 维护人员 : C4_雍和
 * date : 20-4-4 下午4:57
 */
object ProguardFileUtil {
    fun generateFile(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.filePath + "consumer-rules.pro",
            "-keep class ${buildBean.packageName}.model.entity.** { *; }"
        )
    }
}