package com.example.dataclean.ksbaopro

import com.blankj.utilcode.util.FileIOUtils
import com.example.dataclean.entity.BuildBean

/**
 * @author : C4_雍和
 * 描述 :build.gradle文件生成工具
 * 主要功能 :把老的build.gradle转换成新的
 * 维护人员 : C4_雍和
 * date : 20-4-4 下午4:17
 */
object BuildFileUtil {
    /**
     * 老文件转换成新文件
     * @param buildBean BuildBean
     */
    fun generateFile(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.filePath + "build.gradle",
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
        val readStr = FileIOUtils.readFile2List(buildBean.filePath + "build.gradle")
        var newStr = ""
        for (originalStr in readStr) {
            val tempStr = when {
                originalStr.contains("com.android.application") -> {
                    "apply from: rootProject.file('cc-settings-2.gradle')"
                }
                originalStr.contains("kotlin-android-extensions") -> {
                    "$originalStr\napply plugin: 'kotlin-kapt'"
                }
                originalStr.contains("buildToolsVersion") -> {
                    "$originalStr\nresourcePrefix \"${buildBean.suffix.toLowerCase()}_\""
                }
                originalStr.contains("applicationId") -> {
                    "      if (project.ext.runAsApp) {\n" +
                            "            applicationId '${buildBean.packageName}'\n" +
                            "        }"
                }
                originalStr.contains("testInstrumentationRunner") -> {
                    "$originalStr\nconsumerProguardFiles 'consumer-rules.pro'"
                }
                originalStr.contains("buildTypes") -> {
                    "    compileOptions {\n" +
                            "        targetCompatibility JavaVersion.VERSION_1_8\n" +
                            "        sourceCompatibility JavaVersion.VERSION_1_8\n" +
                            "    }\n" +
                            "    dataBinding {\n" +
                            "        enabled = true\n" +
                            "    }\n$originalStr"
                }
                originalStr.contains("androidx.test.espresso") -> {
                    "$originalStr\n    implementation project(path: ':libcommon')\n" +
                            "    api 'com.google.dagger:dagger:2.25.2'\n" +
                            "    kapt 'com.google.dagger:dagger-compiler:2.25.2'\n" +
                            "    api 'com.google.dagger:dagger-android:2.25.2'\n" +
                            "    kapt 'com.google.dagger:dagger-android-processor:2.25.2'"
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