package com.example.dataclean.ksbaopro

import com.blankj.utilcode.util.FileIOUtils
import com.example.dataclean.entity.BuildBean
/**
 * @author : C4_雍和
 * 描述 :生成AndroidManifest.xml文件
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-4 下午5:16
 */
object AndroidManifestFileUtil {
    private const val filePath = "src/main/"
    /**
     * 老文件转换成新文件
     * @param buildBean BuildBean
     */
    fun generateFile(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.filePath + filePath + "AndroidManifest.xml",
            getFileContent(
                buildBean
            )
        )
    }

    /**
     * 把原来的文件转换成自己需要的
     * @param buildBean BuildBean
     * @return String
     */
    private fun getFileContent(buildBean: BuildBean): String {
        val readStr =
            FileIOUtils.readFile2String(buildBean.filePath + filePath + "AndroidManifest.xml")
        val startPosition = readStr.indexOf("<application")
        val endPosition = readStr.indexOf("</application>")
        val startStr = readStr.substring(0, startPosition - 1)
        val endStr = readStr.substring(endPosition, readStr.length)
        val middleStr = "    <application>\n" +
                "        <activity\n" +
                "            android:name=\"${buildBean.packageName}.view.${buildBean.activityName}\"\n" +
                "            android:launchMode=\"singleTask\" />"
        return startStr+middleStr+endStr
    }
}