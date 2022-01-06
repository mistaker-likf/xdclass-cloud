package net.xdclass.controller;

import net.xdclass.domain.Video;
import net.xdclass.domain.VideoOrder;
import net.xdclass.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/video_order")
@RefreshScope
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private VideoService videoService;

    @Value("${video.title}")
    private String videoTitle;

    @RequestMapping("/save")
    public Object save(int videoId){

        //Video video = restTemplate.getForObject("http://localhost:9000/api/v1/video/find_by_id?videoId="+videoId, Video.class);

        //List<ServiceInstance> list = discoveryClient.getInstances("xdclass-video-service");
        //ServiceInstance serviceInstance = list.get(0);
        //轮询策略的负载均衡
        //Video video = restTemplate.getForObject("http://xdclass-video-service/api/v1/video/find_by_id?videoId="+videoId,Video.class);
        Video video = videoService.findById(videoId);
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setVideoId(video.getId());
        videoOrder.setVideoTitle(videoTitle);
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

    /**
     * 测试 feign 调用post方式传输对象
     * @param video
     * @return
     */
//    @PostMapping("save")
//    public Object save(@RequestBody Video video){
//        int rows = videoService.save(video);
//        Map<String, Integer> map = new HashMap<>();
//        map.put("rows", rows);
//        return map;
//    }

    int temp = 0;
    @RequestMapping("list")
    public Object list(HttpServletRequest request){
//        try{
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        temp++;
        if(temp%3==0) throw new RuntimeException();

        Map<String, String> map = new HashMap<>();
        map.put("title1", "AlibabaCloud微服务专题");
        map.put("title2", "小滴课堂面试专题第一季");

        map.put("port", request.getServerPort()+"");
        return map;
    }
}
