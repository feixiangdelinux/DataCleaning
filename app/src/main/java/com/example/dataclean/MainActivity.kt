package com.example.dataclean

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.FileIOUtils
import com.example.dataclean.entity.VideoBean
import com.example.dataclean.util.DownTimer
import com.example.dataclean.util.DownTimerListener
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import timber.log.Timber
import timber.log.Timber.DebugTree


class MainActivity : AppCompatActivity() {
    private val context = this
    private var filePash = ""

    /**
     * 总的视频数据
     */
    var listDatas: MutableList<VideoBean> = ArrayList()

    /**
     * 当前正在检验的哪条视频，
     */
    var position = 0
    var allSize = 100
    private lateinit var videoPlayer: StandardGSYVideoPlayer
    private lateinit var mTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(DebugTree())
        filePash = context.externalCacheDir!!.absolutePath + "/buzz.txt"
        //1读取文件成字符串
        val str = FileIOUtils.readFile2String(filePash)
        //3字符串转json,json转list
        val listDatsfdsas = GsonBuilder().create()
            .fromJson<List<VideoBean>>(str, object : TypeToken<List<VideoBean>>() {}.type)
        listDatas.addAll(listDatsfdsas)
        allSize = listDatas.size
        //5得到数据开始播放
        videoPlayer = findViewById<StandardGSYVideoPlayer>(R.id.detail_player)
        mTextView = findViewById<TextView>(R.id.main_tv)
        videoCallBack()
        getVideoData()
        playVideo()
        findViewById<Button>(R.id.main_btn_stop).setOnClickListener {
            val json = GsonBuilder().create().toJson(listDatas)
            FileIOUtils.writeFileFromString(filePash, json)
            mTextView.text = "保存成功"
        }

    }

    override fun onPause() {
        super.onPause()
        videoPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        videoPlayer.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }


    private fun playVideo() {
        val vUrl = listDatas[position].getvUrl()
        Timber.e("250:  " + vUrl)
        videoPlayer.setUp(vUrl, false, "")
        videoPlayer.startPlayLogic()
    }

    /**
     * 获取一个未检验的视频数据
     * @return VideoBean
     */
    private fun getVideoData(): Int {
        for ((index, e) in listDatas.withIndex()) {
            if (e.i == "0") {
                position = index
                break
            }
        }
        return position
    }


    private fun videoCallBack() {
        videoPlayer.setVideoAllCallBack(object : VideoAllCallBack {
            override fun onClickResumeFullscreen(url: String?, vararg objects: Any?) {
            }

            override fun onEnterFullscreen(url: String?, vararg objects: Any?) {
            }

            override fun onClickResume(url: String?, vararg objects: Any?) {
            }

            override fun onClickSeekbarFullscreen(url: String?, vararg objects: Any?) {
            }

            override fun onStartPrepared(url: String?, vararg objects: Any?) {
            }

            override fun onClickStartIcon(url: String?, vararg objects: Any?) {
            }

            override fun onTouchScreenSeekLight(url: String?, vararg objects: Any?) {
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
            }

            override fun onClickStartThumb(url: String?, vararg objects: Any?) {
            }

            override fun onEnterSmallWidget(url: String?, vararg objects: Any?) {
            }

            override fun onClickStartError(url: String?, vararg objects: Any?) {
            }

            override fun onClickBlankFullscreen(url: String?, vararg objects: Any?) {
            }

            override fun onPrepared(url: String?, vararg objects: Any?) {
                mTextView.text = "当前第${position}个,一共${allSize}个，进度：${(position / allSize) * 100}%"
                listDatas[position].i = "1"
                getVideoData()
                DownTimer.getInstance().startDown(1000)
                DownTimer.getInstance().setListener(object : DownTimerListener {
                    override fun onTick(millisUntilFinished: Long) {
                    }

                    //倒计时结束的方法
                    override fun onFinish() {
                        playVideo()

                    }
                })
            }

            override fun onAutoComplete(url: String?, vararg objects: Any?) {
            }

            override fun onQuitSmallWidget(url: String?, vararg objects: Any?) {
            }

            override fun onTouchScreenSeekVolume(url: String?, vararg objects: Any?) {
            }

            override fun onClickBlank(url: String?, vararg objects: Any?) {
            }

            override fun onClickStop(url: String?, vararg objects: Any?) {
            }

            override fun onClickSeekbar(url: String?, vararg objects: Any?) {
            }

            override fun onPlayError(url: String?, vararg objects: Any?) {
                mTextView.text = "当前第${position}个,一共${allSize}个，进度：${(position / allSize) * 100}%"
                listDatas[position].i = "2"
                getVideoData()
                DownTimer.getInstance().startDown(1000)
                DownTimer.getInstance().setListener(object : DownTimerListener {
                    override fun onTick(millisUntilFinished: Long) {
                    }

                    //倒计时结束的方法
                    override fun onFinish() {
                        playVideo()

                    }
                })
            }

            override fun onClickStopFullscreen(url: String?, vararg objects: Any?) {
            }

            override fun onTouchScreenSeekPosition(url: String?, vararg objects: Any?) {
            }
        })
    }
}
