package net.xdclass.controller;

import com.alibaba.nacos.api.naming.pojo.AbstractHealthChecker;
import net.xdclass.domain.Video;
import net.xdclass.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @RequestMapping("find_by_id")
    public Object findById(int videoId, HttpServletRequest request){

        Video video = videoService.findById(videoId);
        //方便发现请求是哪台机器
        video.setServeInfo(request.getServerName() +":"+request.getServerPort());
        return video;
    }

}
