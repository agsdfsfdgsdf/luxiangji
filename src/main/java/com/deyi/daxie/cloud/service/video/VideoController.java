package com.deyi.daxie.cloud.service.video;

import com.deyi.daxie.cloud.service.video.hkwswin.DeviceEnums;
import com.deyi.daxie.cloud.service.video.hkwswin.Playback;
import com.deyi.daxie.cloud.service.video.hkwswin.PlaybackService;
import com.deyi.daxie.cloud.service.video.hkwswin.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


/**
 * @create 2020-12-24-17:55
 */
@Controller
@RequestMapping("/video")
class VideoController {

    @PostMapping("/play")
    public static void videoPlay() throws InterruptedException {
        ConGasStationMonitoring drvice = new ConGasStationMonitoring();
        drvice.setDeviceIp("10.5.68.76");
        drvice.setDeviceUsername("admin");
        drvice.setDevicePassword("deyixigu2023");
        drvice.setDeviceChannel(1);
        Playback playback = new Playback(3, 0L, drvice, Utils.getDvrTime("2023-08-22 10:00:00"), Utils.getDvrTime("2023-08-6 16:00:15"), DeviceEnums.playUrl.getValue() + "123.mp4");
        playback.start();
    }

}



