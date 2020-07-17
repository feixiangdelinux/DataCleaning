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
 * date : 20-7-17 下午3:46
 */
class OumeidUtilTest {
    /**
     * B1把不能播放的数据删除并把最终结果保存到本地
     */
    @Test
    fun cleaningDataTwo() {
        //1加载json文件到内存中
        val fileStr = KtStringUtil.getStrInFile("/home/ccg/oumeid.json")
        val fileStrTwo = KtStringUtil.getStrInFile("/home/ccg/oumeidtext.json")
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
            "/home/ccg/oumeidok.json",
            GsonBuilder().create().toJson(listDatasOne)
        )
    }

    /**
     * B2把最终结果分解成几份
     */
    @Test
    fun cleaningDataThree() {
        //1加载json文件到内存中
        val fileStr = KtStringUtil.getStrInFile("/home/ccg/oumeidok.json")
        val listDatasOne = GsonBuilder().create()
            .fromJson<ArrayList<VideoBean>>(
                fileStr,
                object : TypeToken<ArrayList<VideoBean>>() {}.type
            )
        val zongList = KtStringUtil.averageAssign(listDatasOne, 2)
        val aa = FinalVideoBean()
        aa.timeStamp = System.currentTimeMillis()
        aa.data = zongList!![0]
        KtStringUtil.saveAsFileWriter(
            "/home/ccg/oumeid1.json",
            GsonBuilder().create().toJson(aa)
        )
        val bb = FinalVideoBean()
        bb.timeStamp = System.currentTimeMillis()
        bb.data = zongList[1]
        KtStringUtil.saveAsFileWriter(
            "/home/ccg/oumeid2.json",
            GsonBuilder().create().toJson(bb)
        )
    }

}