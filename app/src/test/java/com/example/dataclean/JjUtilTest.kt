package com.example.dataclean

import com.example.dataclean.entity.FinalVideoBean
import com.example.dataclean.entity.VideoBean
import com.example.dataclean.entity.VideoInfo
import com.example.dataclean.util.KtStringUtil
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2020/9/7 11:46
 */
class JjUtilTest {
    /**
     * A完整数据并去重复
     */
    @Test
    fun cleaningData() {
        val startTime = System.currentTimeMillis()
        //1加载json文件到内存中
        val fileStr = KtStringUtil.getStrInFile("E:\\jj.json")
//        val fileStr = KtStringUtil.getStrInFile("/home/ccg/jj.json")
        //2把json转换成list
        val listDatas = GsonBuilder().create()
            .fromJson<List<VideoBean>>(fileStr, object : TypeToken<List<VideoBean>>() {}.type)
        println("一共: " + listDatas.size)
        //3把不完整的数据填写完整
        for (videoUrlData in listDatas) {
            for (pictureUrlData in listDatas) {
                if (videoUrlData.id != pictureUrlData.id
                    && videoUrlData.name == pictureUrlData.name
                    && videoUrlData.getpUrl().isNullOrEmpty()
                    && videoUrlData.getvUrl().isNotEmpty()
                    && pictureUrlData.getvUrl().isNullOrEmpty()
                    && pictureUrlData.getpUrl().isNotEmpty()
                ) {
                    println(videoUrlData.getvUrl())
                    videoUrlData.name = pictureUrlData.name
                    videoUrlData.setpUrl(pictureUrlData.getpUrl())
                    pictureUrlData.tags = videoUrlData.tags
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
        KtStringUtil.saveAsFileWriter("E:\\jj1.json", GsonBuilder().create().toJson(list))
//        KtStringUtil.saveAsFileWriter("/home/ccg/jj1.json", GsonBuilder().create().toJson(list))
        val endTime = System.currentTimeMillis()
        println("耗时：  " + (endTime - startTime) / 1000 / 60 + " 分钟")
    }

    /**
     * B1把不能播放的数据删除并把最终结果保存到本地
     */
    @Test
    fun cleaningDataTwo() {
        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("E:\\jj1.json")
        val fileStr = KtStringUtil.getStrInFile("/home/ccg/jj1.json")
        val fileStrTwo = KtStringUtil.getStrInFile("/home/ccg/jjText.json")
//        //2把json转换成list
        val listDatasOne = GsonBuilder().create()
            .fromJson<ArrayList<VideoBean>>(
                fileStr,
                object : TypeToken<ArrayList<VideoBean>>() {}.type
            )
        val listDatasTwo = GsonBuilder().create()
            .fromJson<List<VideoInfo>>(fileStrTwo, object : TypeToken<List<VideoInfo>>() {}.type)
        for ((aa, av) in listDatasOne.withIndex()) {
            for ((bb, bv) in listDatasTwo.withIndex()) {
                if (av.getvUrl() == bv.getvUrl()) {
                    av.i = "1"
                }
            }
        }
        val it = listDatasOne.iterator()
        while (it.hasNext()) {
            val item = it.next()
            if (item.i != "1") {
                it.remove()
            }
        }
        println("最终的: " + listDatasOne.size)
//        KtStringUtil.saveAsFileWriter("E:\\jjok.json", GsonBuilder().create().toJson(listDatasOne))
        KtStringUtil.saveAsFileWriter(
            "/home/ccg/jjok.json",
            GsonBuilder().create().toJson(listDatasOne)
        )
    }

    /**
     * B2把最终结果分解成几份
     */
    @Test
    fun cleaningDataThree() {
        //1加载json文件到内存中
        val fileStr = KtStringUtil.getStrInFile("/home/ccg/jjok.json")
        val listDatasOne = GsonBuilder().create()
            .fromJson<ArrayList<VideoBean>>(
                fileStr,
                object : TypeToken<ArrayList<VideoBean>>() {}.type
            )
        val zongList = KtStringUtil.averageAssign(listDatasOne, 3)
        val aa = FinalVideoBean()
        aa.timeStamp = System.currentTimeMillis()
        aa.data = zongList!![0]
        KtStringUtil.saveAsFileWriter(
            "/home/ccg/jjok1.json",
            GsonBuilder().create().toJson(aa)
        )
        val bb = FinalVideoBean()
        bb.timeStamp = System.currentTimeMillis()
        bb.data = zongList[1]
        KtStringUtil.saveAsFileWriter(
            "/home/ccg/jjok2.json",
            GsonBuilder().create().toJson(bb)
        )
        val cc = FinalVideoBean()
        cc.timeStamp = System.currentTimeMillis()
        cc.data = zongList[2]
        KtStringUtil.saveAsFileWriter(
            "/home/ccg/jjok3.json",
            GsonBuilder().create().toJson(cc)
        )
    }
}