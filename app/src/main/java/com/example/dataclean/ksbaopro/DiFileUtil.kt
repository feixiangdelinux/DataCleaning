package com.example.dataclean.ksbaopro

import com.blankj.utilcode.util.FileIOUtils
import com.example.dataclean.entity.BuildBean
/**
 * @author : C4_雍和
 * 描述 :生成di文件
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-4 下午8:47
 */
object DiFileUtil {
    fun generateFile(buildBean: BuildBean) {
        generateUtilModule(
            buildBean
        )
        generateNetRepositoryModule(
            buildBean
        )
        generateUtilComponent(
            buildBean
        )
        generateNetRepositoryComponent(
            buildBean
        )
    }

    private fun generateUtilModule(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.packageRoute + "di/module/" + "UtilModule.kt",
            "package ${buildBean.packageName}.di.module\n" +
                    "\n" +
                    "import com.yingsoft.ksbao.baselib.util.ACache\n" +
                    "import com.yingsoft.ksbao.baselib.util.FunctionPointUtil\n" +
                    "import com.yingsoft.ksbao.baselib.util.UserInfoCache\n" +
                    "import dagger.Module\n" +
                    "import dagger.Provides\n" +
                    "import io.reactivex.disposables.CompositeDisposable\n" +
                    "import javax.inject.Singleton\n" +
                    "\n" +
                    "@Module\n" +
                    "class UtilModule {\n" +
                    "    @Singleton\n" +
                    "    @Provides\n" +
                    "    fun provideFunctionPointUtil(): FunctionPointUtil {\n" +
                    "        return FunctionPointUtil.getInstance()\n" +
                    "    }\n" +
                    "\n" +
                    "    @Singleton\n" +
                    "    @Provides\n" +
                    "    fun provideUserInfoCache(): UserInfoCache {\n" +
                    "        return UserInfoCache.getInstance()\n" +
                    "    }\n" +
                    "\n" +
                    "    @Singleton\n" +
                    "    @Provides\n" +
                    "    fun provideACache(): ACache {\n" +
                    "        return ACache.get()\n" +
                    "    }\n" +
                    "\n" +
                    "    @Singleton\n" +
                    "    @Provides\n" +
                    "    fun provideCompositeDisposable(): CompositeDisposable {\n" +
                    "        return CompositeDisposable()\n" +
                    "    }\n" +
                    "}"
        )
    }

    private fun generateNetRepositoryModule(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.packageRoute + "di/module/" + "NetRepositoryModule.kt",
            "package ${buildBean.packageName}.di.module\n" +
                    "\n" +
                    "import ${buildBean.packageName}.model.repository.NetService\n" +
                    "import com.yingsoft.ksbao.baselib.network.RequestInterceptor\n" +
                    "import com.yingsoft.ksbao.baselib.network.ResponseInterceptor\n" +
                    "import com.yingsoft.ksbao.baselib.util.UserInfoCache\n" +
                    "import dagger.Module\n" +
                    "import dagger.Provides\n" +
                    "import okhttp3.OkHttpClient\n" +
                    "import retrofit2.Retrofit\n" +
                    "import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory\n" +
                    "import retrofit2.converter.gson.GsonConverterFactory\n" +
                    "import javax.inject.Singleton\n" +
                    "\n" +
                    "@Module\n" +
                    "class NetRepositoryModule {\n" +
                    "    @Singleton\n" +
                    "    @Provides\n" +
                    "    fun provideNetService(): NetService {\n" +
                    "        //创建过滤器\n" +
                    "        val interceptor = OkHttpClient.Builder()\n" +
                    "            .addInterceptor(RequestInterceptor())\n" +
                    "            .addInterceptor(ResponseInterceptor())\n" +
                    "            .build()\n" +
                    "        return Retrofit.Builder().baseUrl(\"https://your.api.url/\")\n" +
                    "            .client(interceptor)\n" +
                    "            .addConverterFactory(GsonConverterFactory.create())\n" +
                    "            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())\n" +
                    "            .build().create(NetService::class.java)\n" +
                    "    }\n" +
                    "\n" +
                    "    @Singleton\n" +
                    "    @Provides\n" +
                    "    fun provideUserInfoCache(): UserInfoCache {\n" +
                    "        return UserInfoCache.getInstance()\n" +
                    "    }\n" +
                    "}"
        )
    }

    private fun generateUtilComponent(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.packageRoute + "di/component/" + "UtilComponent.kt",
            "package ${buildBean.packageName}.di.component\n" +
                    "\n" +
                    "import ${buildBean.packageName}.di.module.UtilModule\n" +
                    "import ${buildBean.packageName}.view.${buildBean.activityName}\n" +
                    "import ${buildBean.packageName}.viewmodel.${buildBean.viewModel}\n" +
                    "import dagger.Component\n" +
                    "import javax.inject.Singleton\n" +
                    "\n" +
                    "@Singleton\n" +
                    "@Component(modules = [UtilModule::class])\n" +
                    "interface UtilComponent {\n" +
                    "    fun inject(value: ${buildBean.activityName})\n" +
                    "    fun inject(value: ${buildBean.viewModel})\n" +
                    "}"
        )
    }
    private fun generateNetRepositoryComponent(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.packageRoute + "di/component/" + "NetRepositoryComponent.kt",
            "package ${buildBean.packageName}.di.component\n" +
                    "\n" +
                    "import ${buildBean.packageName}.di.module.NetRepositoryModule\n" +
                    "import ${buildBean.packageName}.model.repository.NetRepository\n" +
                    "import dagger.Component\n" +
                    "import javax.inject.Singleton\n" +
                    "\n" +
                    "@Singleton\n" +
                    "@Component(modules = [NetRepositoryModule::class])\n" +
                    "interface NetRepositoryComponent {\n" +
                    "    fun inject(value: NetRepository)\n" +
                    "}"
        )
    }
}