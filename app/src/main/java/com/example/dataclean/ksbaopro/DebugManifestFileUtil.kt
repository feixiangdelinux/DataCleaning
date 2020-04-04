package com.example.dataclean.ksbaopro

import com.blankj.utilcode.util.FileIOUtils
import com.example.dataclean.entity.BuildBean
/**
 * @author : C4_雍和
 * 描述 :生成Debug中的AndroidManifest
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-4 下午6:54
 */
object DebugManifestFileUtil {
    private const val filePath = "src/main/"
    private const val debugfilePath = "src/main/debug/"

    fun generateFile(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.filePath + debugfilePath + "AndroidManifest.xml",
            getFileContent(
                buildBean
            )
        )
    }


    private fun getFileContent(buildBean: BuildBean): String {
        val readStr =
            FileIOUtils.readFile2List(buildBean.filePath + filePath + "AndroidManifest.xml")
        var newStr = ""
        for (originalStr in readStr) {
            val tempStr = when {
                originalStr.contains("<application") -> {
                    "    <uses-permission android:name=\"android.permission.INTERNET\" />\n" +
                            "    <uses-permission android:name=\"android.permission.WRITE_EXTERNAL_STORAGE\" />\n" +
                            "    <uses-permission android:name=\"android.permission.READ_EXTERNAL_STORAGE\" />\n" +
                            "    <uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />\n" +
                            "    <uses-permission android:name=\"android.permission.ACCESS_WIFI_STATE\" />\n" +
                            "    <uses-permission android:name=\"android.permission.CHANGE_WIFI_STATE\" />\n" +
                            "    <uses-permission android:name=\"android.permission.READ_PHONE_STATE\" />\n" +
                            "    <uses-permission android:name=\"android.permission.REQUEST_INSTALL_PACKAGES\" />$originalStr\n  android:name=\"debug.ModuleApplication\""
                }
                originalStr.contains("<activity") -> {
                    "<activity android:name=\".view.${buildBean.activityName}\">"
                }
                originalStr.contains("</application>") -> {
                    "        <meta-data\n" +
                            "            android:name=\"design_width_in_dp\"\n" +
                            "            android:value=\"375\" />\n" +
                            "        <meta-data\n" +
                            "            android:name=\"design_height_in_dp\"\n" +
                            "            android:value=\"667\" />$originalStr"
                }
                else -> {
                    originalStr
                }
            }
            newStr = newStr + tempStr + "\n"
        }
        return newStr
    }
}