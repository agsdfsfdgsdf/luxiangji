package com.deyi.daxie.cloud.service.video.hkwswin;

/**
 * @Author cxb
 * @Description: 设备参数枚举
 * @Date 16:56 2023/8/18
 * @Param
 * @return
 **/
public enum DeviceEnums {

    playUrl("playUrl", "D:\\sdk\\", "播放缓存地址"),
    downloadUrl("downloadUrl", "C:\\Users\\m1771\\IdeaProjects\\test\\src\\lib", "下载地址"),
    libsUrl("downloadUrl", "C:\\Users\\m1771\\IdeaProjects\\test\\src\\sdk\\库文件\\", "库文件地址"),
    projectPath("projectPath", "http://127.0.0.1:8080/", "项目地址");

   /* playUrl("playUrl", "\\usr\\local\\webserver\\daxievideo\\cache", "播放缓存地址"),
    downloadUrl("downloadUrl", "\\usr\\local\\webserver\\daxievideo\\download\\", "下载地址"),
    libsUrl("downloadUrl", "\\usr\\local\\webserver\\daxievideo\\sdk\\库文件\\", "库文件地址"),
    projectPath("projectPath", "http://127.0.0.1:8080/", "项目地址");*/
    private String key;
    private String value;
    private String remark;

    private DeviceEnums(String key, String value, String remark) {
        this.key = key;
        this.value = value;
        this.remark = remark;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }
}
