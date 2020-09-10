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
        val startTime = System.currentTimeMillis()
        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("/home/ccg/fqypzxxx.json")
        val fileStr = KtStringUtil.getStrInFile("E:\\fqypzxxx.json")
        //2把json转换成list
        val listDatas = GsonBuilder().create().fromJson<ArrayList<VideoBean>>(
            fileStr,
            object : TypeToken<ArrayList<VideoBean>>() {}.type
        )
        println("原始数据一共: " + listDatas.size)
        //3原始数据去重复
        val setOne = LinkedHashSet<VideoBean>()
        val listOne = ArrayList<VideoBean>()
        setOne.addAll(listDatas)
        listOne.addAll(setOne)
        listDatas.clear()
        println("原始数据去重后一共: " + listOne.size)
        //4对数据进行分组，一组视频地址，一组缩略图
        val listTwo = ArrayList<VideoBean>()
        val listThree = ArrayList<VideoBean>()
        for (videoUrlData in listOne) {
            if (videoUrlData.getvUrl().isNotEmpty()) {
                listTwo.add(videoUrlData)
            } else if (videoUrlData.getpUrl().isNotEmpty()) {
                listThree.add(videoUrlData)
            }
        }
        listOne.clear()
        println("视频地址一共: " + listTwo.size)
        //5把缩略图合并到视频地址中
        for (index in listTwo.indices) {
            for (pictureUrlData in listThree) {
                if (listTwo[index].id != pictureUrlData.id && listTwo[index].url == pictureUrlData.url) {
                    listTwo[index].setpUrl(pictureUrlData.getpUrl())
                    listTwo[index].tags = pictureUrlData.tags
                    listTwo[index].name = pictureUrlData.name
                    continue
                }
            }
            if (index % 10000 == 0) {
                println("当前遍历到第: $index")
            }
        }
        listThree.clear()
        //6对完整的数据进行去重操作
        val set = LinkedHashSet<VideoBean>()
        val list = ArrayList<VideoBean>()
        set.addAll(listTwo)
        list.addAll(set)
        listTwo.clear()
        //7去掉不完整的数据
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.getpUrl().isNullOrEmpty() || item.getvUrl().isNullOrEmpty()) {
                iterator.remove()
            }
        }
        //8把去重复的数据保存到文件中
        println("去重复后: " + list.size)
//        KtStringUtil.saveAsFileWriter("/home/ccg/fqypzxxx1.json", GsonBuilder().create().toJson(list))
        KtStringUtil.saveAsFileWriter("E:\\fqypzxxx1.json", GsonBuilder().create().toJson(list))
        val endTime = System.currentTimeMillis()
        println("耗时：  " + (endTime - startTime) / 1000 / 60 + " 分钟")
    }

}