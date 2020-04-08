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
        //删除原有Activity
        FileUtils.delete(buildBean.packageRoute + "${buildBean.activityName}.kt")
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