package com.example.dataclean

import com.example.dataclean.entity.VideoBean
import com.example.dataclean.util.KtStringUtil
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-7-13 下午3:29
 */
class SistersUtilTest {

    /**
     * A完整数据并去重复/home/ccg
     */
    @Test
    fun cleaningData() {
        //1加载json文件到内存中
        val fileStr = KtStringUtil.getStrInFile("/home/ccg/sisters.json")
        //2把json转换成list
        val listDatas = GsonBuilder().create()
            .fromJson<List<VideoBean>>(fileStr, object : TypeToken<List<VideoBean>>() {}.type)
        println("一共: " + listDatas.size)
        //3把不完整的数据填写完整
        for ((aa, av) in listDatas.withIndex()) {
            for ((bb, bv) in listDatas.withIndex()) {
                if (av.id != bv.id
                    && av.name == bv.name
                    && av.getpUrl().isNullOrEmpty()
                    && av.getvUrl().isNotEmpty()
                    && bv.getvUrl().isNullOrEmpty()
                    && bv.getpUrl().isNotEmpty()
                ) {
                    av.setpUrl(bv.getpUrl())
                    av.tags = bv.tags
                    bv.setvUrl(av.getvUrl())
                }
            }
        }
        //4对对象进行去重操作
        val set = LinkedHashSet<VideoBean>()
        val list = ArrayList<VideoBean>()
        set.addAll(listDatas)
        list.addAll(set)
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.getpUrl().isNullOrEmpty() || item.getvUrl().isNullOrEmpty()) {
                iterator.remove()
            }
        }
        //5把去重复的数据保存到文件中
        println("去重复后: " + list.size)
        KtStringUtil.saveAsFileWriter("/home/ccg/sisters1.json", GsonBuilder().create().toJson(list))
    }
}