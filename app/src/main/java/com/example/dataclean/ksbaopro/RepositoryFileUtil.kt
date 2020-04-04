package com.example.dataclean.ksbaopro

import com.blankj.utilcode.util.FileIOUtils
import com.example.dataclean.entity.BuildBean
/**
 * @author : C4_雍和
 * 描述 :生成repository文件
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-4 下午9:12
 */
object RepositoryFileUtil {
    fun generateFile(buildBean: BuildBean) {
        generateNetRepository(
            buildBean
        )
        generateNetService(
            buildBean
        )
    }

    private fun generateNetRepository(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.packageRoute + "model/repository/" + "NetRepository.kt",
            "package ${buildBean.packageName}.model.repository\n" +
                    "\n" +
                    "import com.yingsoft.ksbao.baselib.entity.AbaseBean\n" +
                    "import ${buildBean.packageName}.di.component.DaggerNetRepositoryComponent\n" +
                    "import io.reactivex.Flowable\n" +
                    "import io.reactivex.schedulers.Schedulers\n" +
                    "import javax.inject.Inject\n" +
                    "\n" +
                    "class NetRepository {\n" +
                    "    init { DaggerNetRepositoryComponent.builder().build().inject(this) }\n" +
                    "\n" +
                    "    @Inject\n" +
                    "    lateinit var netService: NetService\n" +
                    "    private val rightCode = 200\n" +
                    "    \n" +
                    "    fun getRequest(map: Map<String, String>): Flowable<AbaseBean> {\n" +
                    "        return netService.getRequest(\"这里填url前缀\", map)\n" +
                    "            .subscribeOn(Schedulers.io())\n" +
                    "//            .filter { return@filter it.status == rightCode }\n" +
                    "    }\n" +
                    "}"
        )
    }

    private fun generateNetService(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.packageRoute + "model/repository/" + "NetService.kt",
            "package ${buildBean.packageName}.model.repository\n" +
                    "\n" +
                    "import com.yingsoft.ksbao.baselib.entity.AbaseBean\n" +
                    "import io.reactivex.Flowable\n" +
                    "import retrofit2.http.*\n" +
                    "\n" +
                    "interface NetService {\n" +
                    "    @GET\n" +
                    "    fun getRequest(@Url url: String, @QueryMap map: Map<String, String>): Flowable<AbaseBean>\n" +
                    "\n" +
                    "    @POST\n" +
                    "    fun postRequest(@Url url: String, @Body map: Map<String, String>): Flowable<AbaseBean>\n" +
                    "}"
        )
    }
}