package com.example.dataclean.ksbaopro

import com.blankj.utilcode.util.FileIOUtils
import com.example.dataclean.entity.BuildBean


/**
 * @author : C4_雍和
 * 描述 :生成Activity文件
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-4 下午5:41
 */
object ActivityFileUtil {
    fun generateFile(buildBean: BuildBean) {
        FileIOUtils.writeFileFromString(
            buildBean.packageRoute + "view/" + "${buildBean.activityName}.kt",
            "package ${buildBean.packageName}.view\n" +
                    "\n" +
                    "import androidx.databinding.DataBindingUtil\n" +
                    "import ${buildBean.packageName}.R\n" +
                    "import ${buildBean.packageName}.databinding.${buildBean.activityBinding}\n" +
                    "import ${buildBean.packageName}.di.component.DaggerUtilComponent\n" +
                    "import ${buildBean.packageName}.utils.NavigationUtils\n" +
                    "import ${buildBean.packageName}.viewmodel.${buildBean.viewModel}\n" +
                    "import com.jess.arms.integration.AppManager\n" +
                    "import androidx.lifecycle.Observer\n" +
                    "import com.yingsoft.ksbao.baselib.util.*\n" +
                    "import com.sunchen.netbus.type.NetType\n" +
                    "import com.yingsoft.ksbao.baselib.view.BaseActivityC\n" +
                    "import javax.inject.Inject\n" +
                    "\n" +
                    "class ${buildBean.activityName} : BaseActivityC<${buildBean.viewModel}>() {\n" +
                    "    override fun providerVMClass(): Class<${buildBean.viewModel}>? = ${buildBean.viewModel}::class.java\n" +
                    "    @Inject\n" +
                    "    lateinit var userInfoCache: UserInfoCache\n" +
                    "    @Inject\n" +
                    "    lateinit var functionPointUtil: FunctionPointUtil\n" +
                    "    @Inject\n" +
                    "    lateinit var aCache: ACache\n" +
                    "    lateinit var binding: ${buildBean.activityBinding}\n" +
                    "    private val context = this\n" +
                    "    init { DaggerUtilComponent.builder().build().inject(this) }\n" +
                    "    override fun initView() {\n" +
                    "        binding = DataBindingUtil.setContentView(this, R.layout.${buildBean.layoutId})\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun initData() {\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun setListener() {\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun startObserve() {\n" +
                    "        viewModel.apply {\n" +
                    "            errMsg.observe(this@${buildBean.activityName}, Observer {\n" +
                    "                it?.run {\n" +
                    "                    showToast(this)\n" +
                    "                    showLog()\n" +
                    "                }\n" +
                    "            })\n" +
                    "        }\n" +
                    "    }" +
                    "\n" +
                    "    override fun brokenNetListener(netType: NetType) {\n" +
                    "        if (netType.name == \"NONE\") {\n" +
                    "            NavigationUtils.goNetErrorActivity()\n" +
                    "            AppManager.getAppManager().killAll()\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun getData() {\n" +
                    "        viewModel.initData()\n" +
                    "    }\n" +
                    "}"
        )
    }
}