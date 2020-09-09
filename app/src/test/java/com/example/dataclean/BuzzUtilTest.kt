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
 * 描述 :SistersUtilTest
 * 主要功能 :DmUtilTest
 * 维护人员 : C4_雍和
 * date : 20-6-12 上午9:11
 */
class BuzzUtilTest {
    /**
     * A完整数据并去重复/home/ccg
     */
    @Test
    fun cleaningData() {
        val startTime = System.currentTimeMillis()
        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("/home/ccg/buzz.json")
        val fileStr = KtStringUtil.getStrInFile("E:\\buzz.json")
        //2把json转换成list
        val listDatas = GsonBuilder().create()
            .fromJson<List<VideoBean>>(fileStr, object : TypeToken<List<VideoBean>>() {}.type)
        println("原始数据一共: " + listDatas.size)
        val setOne = LinkedHashSet<VideoBean>()
        val listOne = ArrayList<VideoBean>()
        setOne.addAll(listDatas)
        listOne.addAll(setOne)
        println("原始数据去重后一共: " + listOne.size)
        //3把不完整的数据填写完整
        for (videoUrlData in listOne) {
            for (pictureUrlData in listOne) {
                if (videoUrlData.id != pictureUrlData.id
                    && videoUrlData.name == pictureUrlData.name
                    && videoUrlData.getpUrl().isNullOrEmpty()
                    && videoUrlData.getvUrl().isNotEmpty()
                    && pictureUrlData.getvUrl().isNullOrEmpty()
                    && pictureUrlData.getpUrl().isNotEmpty()
                ) {
                    videoUrlData.setpUrl(pictureUrlData.getpUrl())
                    videoUrlData.tags = pictureUrlData.tags
                    pictureUrlData.setvUrl(videoUrlData.getvUrl())
                }
            }
        }
        //4对对象进行去重操作
        val set = LinkedHashSet<VideoBean>()
        val list = ArrayList<VideoBean>()
        set.addAll(listOne)
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
//        KtStringUtil.saveAsFileWriter("/home/ccg/buzz1.json", GsonBuilder().create().toJson(list))
        KtStringUtil.saveAsFileWriter("E:\\buzz1.json", GsonBuilder().create().toJson(list))
        val endTime = System.currentTimeMillis()
        println("耗时：  " + (endTime - startTime) / 1000 / 60 + " 分钟")
    }


    /**
     * B1把不能播放的数据删除并把最终结果保存到本地
     */
    @Test
    fun cleaningDataTwo() {
        //1加载json文件到内存中
        val fileStr = KtStringUtil.getStrInFile("/home/ccg/buzz1.json")
        val fileStrTwo = KtStringUtil.getStrInFile("/home/ccg/buzzText.json")
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
        KtStringUtil.saveAsFileWriter(
            "/home/ccg/buzzok.json",
            GsonBuilder().create().toJson(listDatasOne)
        )
    }




    /**
     * B1把不能播放的数据删除并把最终结果保存到本地
     */
    @Test
    fun cleaningDataFive() {
        //1加载json文件到内存中
        val fileStr = KtStringUtil.getStrInFile("/home/ccg/buzz.txt")
//        //2把json转换成list
        val listDatasOne = GsonBuilder().create()
            .fromJson<ArrayList<VideoBean>>(
                fileStr,
                object : TypeToken<ArrayList<VideoBean>>() {}.type
            )
        println("刚开始的: " + listDatasOne.size)
        val it = listDatasOne.iterator()
        while (it.hasNext()) {
            val item = it.next()
            if (item.i != "1") {
                it.remove()
            }
        }
        println("最终的: " + listDatasOne.size)
        KtStringUtil.saveAsFileWriter(
            "/home/ccg/buzzok.json",
            GsonBuilder().create().toJson(listDatasOne)
        )
    }




    /**
     * B2把最终结果分解成几份
     */
    @Test
    fun cleaningDataThree() {
        //1加载json文件到内存中
        val fileStr = KtStringUtil.getStrInFile("/home/ccg/buzzok.json")
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
            "/home/ccg/buzz1.json",
            GsonBuilder().create().toJson(aa)
        )
        val bb = FinalVideoBean()
        bb.timeStamp = System.currentTimeMillis()
        bb.data = zongList[1]
        KtStringUtil.saveAsFileWriter(
            "/home/ccg/buzz2.json",
            GsonBuilder().create().toJson(bb)
        )
        val cc = FinalVideoBean()
        cc.timeStamp = System.currentTimeMillis()
        cc.data = zongList[2]
        KtStringUtil.saveAsFileWriter(
            "/home/ccg/buzz3.json",
            GsonBuilder().create().toJson(cc)
        )
    }

    /**
     * B2把最终结果分解成几份
     */
    @Test
    fun cleaningDataFour() {
        //1加载json文件到内存中
        val fileStr = KtStringUtil.getStrInFile("/home/ccg/buzz.json")
        val listDatasOne = GsonBuilder().create()
            .fromJson<ArrayList<VideoBean>>(
                fileStr,
                object : TypeToken<ArrayList<VideoBean>>() {}.type
            )
        val zongList = KtStringUtil.averageAssign(listDatasOne, 10)
        for ((index, e) in zongList!!.withIndex()) {
            KtStringUtil.saveAsFileWriter(
                "/home/ccg/buzz$index.txt",
                GsonBuilder().create().toJson(e)
            )
        }
    }
}