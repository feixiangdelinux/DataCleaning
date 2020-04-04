package com.example.dataclean.ksbaopro

import com.blankj.utilcode.util.FileIOUtils
import com.example.dataclean.entity.BuildBean
/**
 * @author : C4_雍和
 * 描述 :生成ViewModel文件
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-4 下午9:24
 */
object ViewModelFileUtil {
    fun generateFile(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.packageRoute + "viewmodel/" + "${buildBean.viewModel}.kt",
            "package ${buildBean.packageName}.viewmodel\n" +
                    "\n" +
                    "import androidx.lifecycle.MutableLiveData\n" +
                    "import ${buildBean.packageName}.di.component.DaggerUtilComponent\n" +
                    "import ${buildBean.packageName}.model.repository.NetRepository\n" +
                    "import com.yingsoft.ksbao.baselib.entity.AbaseBean\n" +
                    "import com.yingsoft.ksbao.baselib.util.ACache\n" +
                    "import com.yingsoft.ksbao.baselib.util.FunctionPointUtil\n" +
                    "import io.reactivex.android.schedulers.AndroidSchedulers\n" +
                    "import io.reactivex.schedulers.Schedulers\n" +
                    "import com.yingsoft.ksbao.baselib.util.UserInfoCache\n" +
                    "import com.yingsoft.ksbao.baselib.viewmodel.BaseViewModelB\n" +
                    "import javax.inject.Inject\n" +
                    "\n" +
                    "class ${buildBean.viewModel} : BaseViewModelB() {\n" +
                    "    init { DaggerUtilComponent.builder().build().inject(this) }\n" +
                    "    @Inject\n" +
                    "    lateinit var aCache: ACache\n" +
                    "    @Inject\n" +
                    "    lateinit var userInfoCache: UserInfoCache\n" +
                    "    @Inject\n" +
                    "    lateinit var functionPointUtil: FunctionPointUtil\n" +
                    "    private val repository by lazy { NetRepository() }\n" +
                    "    var uiData: MutableLiveData<AbaseBean> = MutableLiveData()\n" +
                    "    val errMsg: MutableLiveData<String> = MutableLiveData()\n" +
                    "    fun initData() {\n" +
                    "        val map = mutableMapOf<String, String>()\n" +
                    "        map[\"key\"] = \"value\"\n" +
                    "        addDisposable(repository.getRequest(map)\n" +
                    "            .subscribeOn(Schedulers.io())\n" +
                    "            .observeOn(AndroidSchedulers.mainThread())\n" +
                    "            .doOnError {\n" +
                    "                errMsg.postValue(\"获取数据失败：  \${it.message}\")\n" +
                    "            }.subscribe {\n" +
                    "                uiData.postValue(it)\n" +
                    "            }\n" +
                    "        )\n" +
                    "    }\n" +
                    "}"
        )
    }
}