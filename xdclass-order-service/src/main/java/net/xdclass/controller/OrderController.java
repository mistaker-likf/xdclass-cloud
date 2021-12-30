package net.xdclass.controller;


import net.xdclass.domain.Video;
import net.xdclass.domain.VideoOrder;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/video_order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/save")
    public Object save(int videoId){

        //Video video = restTemplate.getForObject("http://localhost:9000/api/v1/video/find_by_id?videoId="+videoId, Video.class);

        //List<ServiceInstance> list = discoveryClient.getInstances("xdclass-video-service");
        //ServiceInstance serviceInstance = list.get(0);
        //轮询策略的负载均衡
        Video video = restTemplate.getForObject("http://xdclass-video-service/api/v1/video/find_by_id?videoId="+videoId,Video.class);
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setVideoId(video.getId());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());

        videoOrder.setServeInfo(video.getServeInfo());
        return videoOrder;

        /**
        存在的问题：
            服务之间的IP信息写死 (localhost:9000 如果服务器重启 IP地址可能重新分配。。。)
            服务之间无法提供负载均衡 (不支持多节点负载均衡调用)
            多个服务直接关系调用维护复杂 (生产环境涉及多个服务之间互相调用)
         **/
    }

}
