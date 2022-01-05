package net.xdclass.service;

import net.xdclass.domain.Video;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceFallback implements VideoService{
    @Override
    public Video findById(int videoId) {

        Video video = new Video();
        video.setTitle("这个是Fallback里面的视频");
        return video;
    }

    @Override
    public int save(Video video) {
        return 0;
    }
}
