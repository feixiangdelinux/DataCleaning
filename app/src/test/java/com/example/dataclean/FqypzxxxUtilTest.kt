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
 * date : 2020/9/7 9:59
 */
class FqypzxxxUtilTest {
    /**
     * A完整数据并去重复/home/ccg
     *
     */
    @Test
    fun cleaningData() {
        //1加载json文件到内存中
        val fileStr = KtStringUtil.getStrInFile("E:\\fqypzxxx.json")
        //2把json转换成list
        val listDatas = GsonBuilder().create()
            .fromJson<List<VideoBean>>(fileStr, object : TypeToken<List<VideoBean>>() {}.type)
        println("一共: " + listDatas.size)
        //3把不完整的数据填写完整
        for (videoUrlData in listDatas) {
            for (pictureUrlData in listDatas) {
                if (videoUrlData.id != pictureUrlData.id
                    && videoUrlData.url == pictureUrlData.url
                    && videoUrlData.getpUrl().isNullOrEmpty()
                    && videoUrlData.getvUrl().isNotEmpty()
                    && pictureUrlData.getvUrl().isNullOrEmpty()
                    && pictureUrlData.getpUrl().isNotEmpty()
                ) {
                    videoUrlData.name = pictureUrlData.name
                    videoUrlData.tags = pictureUrlData.tags
                    videoUrlData.setpUrl(pictureUrlData.getpUrl())
                    pictureUrlData.setvUrl(videoUrlData.getvUrl())
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
        KtStringUtil.saveAsFileWriter("E:\\fqypzxxx1.json", GsonBuilder().create().toJson(list))
    }

}