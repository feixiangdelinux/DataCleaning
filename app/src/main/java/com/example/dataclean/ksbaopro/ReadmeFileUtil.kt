package com.example.dataclean.ksbaopro

import com.blankj.utilcode.util.FileIOUtils
import com.example.dataclean.entity.BuildBean
/**
 * @author : C4_雍和
 * 描述 :生成README.md文件
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-4 下午5:03
 */
object ReadmeFileUtil {
    fun generateFile(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.filePath + "README.md",
            "#### 模块介绍\n" +
                    "\n" +
                    "\n" +
                    "#### 使用说明\n" +
                    "\n" +
                    "\n" +
                    "#### 参与贡献"
        )
    }
}