package com.example.dataclean

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-6-23 上午9:32
 */
class VideoPlayActivity : AppCompatActivity() {
    private lateinit var videoPlayer: StandardGSYVideoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        videoPlayer = findViewById<StandardGSYVideoPlayer>(R.id.detail_player)
        videoPlayer.setUp("https://ddyunbo.com/20190313/2566_52cb7e52/index.m3u8", false, "")
        videoPlayer.startPlayLogic()
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

}