package com.example.dataclean

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test


class KtStringUtilTest {
    /**
     * 1完整数据并去重复
     */
    @Test
    fun cleaningData() {
        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("E:\\jj.json")
        val fileStr = KtStringUtil.getStrInFile("/home/linux/jj.json")
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
//        KtStringUtil.saveAsFileWriter("E:\\jj1.json", GsonBuilder().create().toJson(list))
        KtStringUtil.saveAsFileWriter("/home/linux/jj1.json", GsonBuilder().create().toJson(list))
    }

    /**
     * 1把不能播放的数据删除并把最终结果保存到本地
     */
    @Test
    fun cleaningDataTwo() {
        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("E:\\jj1.json")
        val fileStr = KtStringUtil.getStrInFile("/home/linux/jj1.json")
        val fileStrTwo = KtStringUtil.getStrInFile("/home/linux/jjText.json")
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
        KtStringUtil.saveAsFileWriter("/home/linux/jjok.json", GsonBuilder().create().toJson(listDatasOne))
    }

    /**
     * 把最终结果分解成几份
     */
    @Test
    fun cleaningDataThree() {
        //1加载json文件到内存中
        val fileStr = KtStringUtil.getStrInFile("E:\\jjok.json")
        val listDatasOne = GsonBuilder().create()
            .fromJson<ArrayList<VideoBean>>(
                fileStr,
                object : TypeToken<ArrayList<VideoBean>>() {}.type
            )
        val zongList = KtStringUtil.splitList(listDatasOne, 2)
            KtStringUtil.saveAsFileWriter("E:\\jjok1.json", GsonBuilder().create().toJson(zongList[0]))
            KtStringUtil.saveAsFileWriter("E:\\jjok2.json", GsonBuilder().create().toJson(zongList[1]))
    }
}