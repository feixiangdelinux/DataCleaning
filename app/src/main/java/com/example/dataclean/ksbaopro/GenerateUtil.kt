package com.example.dataclean.ksbaopro

import com.blankj.utilcode.util.FileUtils
import com.example.dataclean.entity.BuildBean


/**
 * @author : C4_雍和
 * 描述 :文件生成工具
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-4 下午11:44
 */
object GenerateUtil {
    fun generateAndroidKsbaoPro(buildBean: BuildBean) {
        //生成debug的AndroidManifest.xml文件
        DebugManifestFileUtil.generateFile(buildBean)
        //生成debug中的Application文件
        DebugApplicationFileUtil.generateFile(buildBean)
        //生成proguard-rules.pro文件
        ProguardFileUtil.generateFile(buildBean)
        //生成README.md文件
        ReadmeFileUtil.generateFile(buildBean)
        //生成build.gradle文件
        BuildFileUtil.generateFile(buildBean)
        //生成AndroidManifest.xml文件
        AndroidManifestFileUtil.generateFile(buildBean)
        //生成ComponentUtil文件
        ComponentFileUtil.generateFile(buildBean)
        //生成di文件
        DiFileUtil.generateFile(buildBean)
        //生成repository文件
        RepositoryFileUtil.generateFile(buildBean)
        //生成ViewModel文件
        ViewModelFileUtil.generateFile(buildBean)
        //生成Activity文件
        ActivityFileUtil.generateFile(buildBean)
        //生成总线文件
        NavigationFileUtil.generateFile(buildBean)
        //生成数据模型文件夹
        FileUtils.createOrExistsDir(buildBean.packageRoute + "model/entity/")
        //删除无用资源
        deleteUselessFile(buildBean)
    }

    private fun deleteUselessFile(buildBean: BuildBean) {
        //删除原有Activity
        FileUtils.delete(buildBean.packageRoute + "${buildBean.activityName}.kt")
        //删除原有app图标
        val aaa = "src/main/res/"
        FileUtils.deleteAllInDir(buildBean.filePath + aaa + "mipmap-xxhdpi")
        FileUtils.deleteAllInDir(buildBean.filePath + aaa + "drawable")
        FileUtils.deleteAllInDir(buildBean.filePath + aaa + "drawable-v24")
        FileUtils.deleteAllInDir(buildBean.filePath + aaa + "mipmap-anydpi-v26")
        FileUtils.deleteAllInDir(buildBean.filePath + aaa + "mipmap-hdpi")
        FileUtils.deleteAllInDir(buildBean.filePath + aaa + "mipmap-mdpi")
        FileUtils.deleteAllInDir(buildBean.filePath + aaa + "mipmap-xhdpi")
        FileUtils.deleteAllInDir(buildBean.filePath + aaa + "mipmap-xxxhdpi")
        //导入考试宝典图标
        val oneStr = buildBean.filePath.substring(0, buildBean.filePath.lastIndexOf("/"))
        val twoStr = oneStr.substring(0, oneStr.lastIndexOf("/"))
        val finalStr = "$twoStr/app/"
        FileUtils.copy(
            finalStr + aaa + "mipmap-xxhdpi/" + "ic_launcher.png",
            buildBean.filePath + aaa + "mipmap-xxhdpi/" + "ic_launcher.png"
        )
        FileUtils.copy(
            finalStr + aaa + "mipmap-xxhdpi/" + "ic_launcher_round.png",
            buildBean.filePath + aaa + "mipmap-xxhdpi/" + "ic_launcher_round.png"
        )
    }

    fun generateSimpleFile(buildBean: BuildBean) {
        //生成debug的AndroidManifest.xml文件
        DebugManifestFileUtil.generateFile(buildBean)
        //生成debug中的Application文件
        DebugApplicationFileUtil.generateFile(buildBean)
        //生成proguard-rules.pro文件
        ProguardFileUtil.generateFile(buildBean)
        //生成README.md文件
        ReadmeFileUtil.generateFile(buildBean)
        //生成build.gradle文件
        BuildFileUtil.generateFile(buildBean)
        //生成ComponentUtil文件
        ComponentSimpleFileUtil.generateFile(buildBean)
        //生成AndroidManifest.xml文件
        AndroidManifestSimpleFileUtil.generateFile(buildBean)
    }

    fun generateMVVM(buildBean: BuildBean) {
        //生成ViewModel文件
        ViewModelFileUtil.generateFile(buildBean)
        //生成Activity文件
        ActivityFileUtil.generateFile(buildBean)
    }
}