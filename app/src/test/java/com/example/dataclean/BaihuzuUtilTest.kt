package com.example.dataclean

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
 * date : 2020/9/25 13:46
 */
class BaihuzuUtilTest {
    val name = "baihuzu"
    val isLinux = false
    /**
     * A完整数据并去重复/home/ccg
     */
    @Test
    fun cleaningData() {
        val startTime = System.currentTimeMillis()
        //1加载json文件到内存中
        val fileStr = if (isLinux) {
            KtStringUtil.getStrInFile("/home/ccg/$name.json")
        } else {
            KtStringUtil.getStrInFile("E:\\$name.json")
        }
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
        val listVideo = ArrayList<VideoBean>()
        val listThree = ArrayList<VideoBean>()
        for (videoUrlData in listOne) {
            if (videoUrlData.getvUrl().isNotEmpty()) {
                listVideo.add(videoUrlData)
            } else if (videoUrlData.getpUrl().isNotEmpty()) {
                listThree.add(videoUrlData)
            }
        }
        listOne.clear()
        println("视频地址一共: " + listVideo.size)
        //5把缩略图合并到视频地址中
        for (index in listVideo.indices) {
            for (pictureUrlData in listThree) {
                if (listVideo[index].id != pictureUrlData.id && listVideo[index].url == pictureUrlData.url) {
                    listVideo[index].name = pictureUrlData.name
                    listVideo[index].tags = pictureUrlData.tags
                    listVideo[index].setpUrl(pictureUrlData.getpUrl())
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
        set.addAll(listVideo)
        list.addAll(set)
        listVideo.clear()
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


        if (isLinux) {
            KtStringUtil.saveAsFileWriter(
                "/home/ccg/" + name + "1.json",
                GsonBuilder().create().toJson(list)
            )
        } else {
            KtStringUtil.saveAsFileWriter(
                "E:\\" + name + "1.json",
                GsonBuilder().create().toJson(list)
            )
        }
        val endTime = System.currentTimeMillis()
        println("耗时：  " + (endTime - startTime) / 1000 / 60 + " 分钟")
    }

    /**
     * B1把不能播放的数据删除并把最终结果保存到本地
     */
    @Test
    fun cleaningDataTwo() {
        //1加载json文件到内存中
        val fileStr = if (isLinux) {
            KtStringUtil.getStrInFile("/home/ccg/aqdtv1.json")
        } else {
            KtStringUtil.getStrInFile("E:\\" + name + "1.json")
        }
        val fileStrTwo = if (isLinux) {
            KtStringUtil.getStrInFile("/home/ccg/" + name + "Text.json")
        } else {
            KtStringUtil.getStrInFile("E:\\" + name + "Text.json")

        }
        //2把json转换成list
        val listDatasOne = GsonBuilder().create()
            .fromJson<ArrayList<VideoBean>>(
                fileStr,
                object : TypeToken<ArrayList<VideoBean>>() {}.type
            )
        val listDatasTwo = GsonBuilder().create()
            .fromJson<List<VideoInfo>>(fileStrTwo, object : TypeToken<List<VideoInfo>>() {}.type)
        for (videoUrlData in listDatasOne) {
            for (pictureUrlData in listDatasTwo) {
                if (videoUrlData.getvUrl() == pictureUrlData.getvUrl()) {
                    videoUrlData.i = "1"
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
        if (isLinux) {
            KtStringUtil.saveAsFileWriter(
                "/home/ccg/" + name + "ok.json",
                GsonBuilder().create().toJson(listDatasOne)
            )
        } else {
            KtStringUtil.saveAsFileWriter(
                "E:\\" + name + "ok.json",
                GsonBuilder().create().toJson(listDatasOne)
            )
        }
    }

    /**
     * B2把最终结果分解成几份
     */
    @Test
    fun cleaningDataThree() {
        //1加载json文件到内存中
        val fileStr = if (isLinux) {
            KtStringUtil.getStrInFile("/home/ccg/" + name + "ok.json")
        } else {
            KtStringUtil.getStrInFile("E:\\" + name + "ok.json")
        }
        val listDatasOne = GsonBuilder().create().fromJson<ArrayList<VideoBean>>(
            fileStr,
            object : TypeToken<ArrayList<VideoBean>>() {}.type
        )
        val videoTag: MutableList<String> = ArrayList()
        val videoUrl: MutableList<String> = ArrayList()
        for (i in listDatasOne) {
            if (!videoTag.contains(i.tags)) {
                videoTag.add(i.tags)
            }
        }
        for (i in videoTag.indices) {
            val tempList: MutableList<VideoBean> = ArrayList()
            for (j in listDatasOne) {
                if (videoTag[i] == j.tags) {
                    tempList.add(j)
                }
            }
            val ssss = VideoListBean()
            ssss.videoTag = videoTag[i]
            ssss.data = tempList
            val videoU = if (isLinux) {
                "/home/ccg/$i.json"
            } else {
                "E:\\新建文件夹\\$i.json"
            }
            KtStringUtil.saveAsFileWriter(videoU, GsonBuilder().create().toJson(ssss))
            videoUrl.add("https://siyou.nos-eastchina1.126.net/21/$name/$i.json")
        }
        val secon = SecondListBean()
        secon.videoTag = videoTag
        secon.videoUrl = videoUrl
        if (isLinux) {
            KtStringUtil.saveAsFileWriter(
                "/home/ccg/index.json",
                GsonBuilder().create().toJson(secon)
            )
        } else {
            KtStringUtil.saveAsFileWriter(
                "E:\\新建文件夹\\index.json",
                GsonBuilder().create().toJson(secon)
            )
        }
        println("完成    $name")
    }
}