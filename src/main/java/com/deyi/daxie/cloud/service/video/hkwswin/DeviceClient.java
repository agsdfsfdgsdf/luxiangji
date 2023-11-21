package com.deyi.daxie.cloud.service.video.hkwswin;

import com.deyi.daxie.cloud.service.video.ConGasStationMonitoring;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;

import javax.swing.*;

/**
 * @Author cxb
 * @Description: 用户注册
 * @Date 17:20 2023/8/18
 * @Param
 * @return
 **/
public class DeviceClient extends JFrame {
    /**
     * 函数:      主类构造函数
     * 函数描述:	初始化成员
     **/
    public DeviceClient() {
        lUserID = new NativeLong(-1);
        lPreviewHandle = new NativeLong(-1);
        m_lPort = new NativeLongByReference(new NativeLong(-1));
        m_iTreeNodeNum = 0;
    }

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    /**
     * 设备信息
     **/
    HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;
    /**
     * 已登录设备的IP地址
     **/
    String m_sDeviceIP;
    /**
     * 用户句柄
     **/
    NativeLong lUserID;
    /**
     * 预览句柄
     **/
    NativeLong lPreviewHandle;
    /**
     * 回调预览时播放库端口指针
     **/
    NativeLongByReference m_lPort;
    /**
     * 通道树节点数目
     **/
    int m_iTreeNodeNum;


    /**
     * @return boolean
     * @Author cxb
     * @Description: 注册
     * @Date 9:08 2019/7/12
     * @Param [drvice 设备信息]
     **/
    public long Login_V30(ConGasStationMonitoring drvice) {
        //注册
        m_sDeviceIP = drvice.getDeviceIp();
        m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP, (short) 8000, drvice.getDeviceUsername(), drvice.getDevicePassword(), m_strDeviceInfo);
        long userID = lUserID.longValue();
        if (userID == -1) {
            System.out.println("注册失败");
            return -1;
        } else {
            System.out.println("注册成功");
            return userID;

        }
    }
    //登陆
    /*private static void login() {
        //存储登陆设备集合
        List<ConGasStationMonitoring> list = new ArrayList<>();
        ConGasStationMonitoring device = new ConGasStationMonitoring();
        device.setDeviceIp("192.168.1.16"); //改成自己设备的ip 用户名和密码
        device.setDeviceUsername("admin");
        device.setDevicePassword("Admin123");
        ConGasStationMonitoring devic1 = new ConGasStationMonitoring();
        device.setDeviceIp("192.168.1.17"); //改成自己设备的ip 用户名和密码
        device.setDeviceUsername("admin");
        device.setDevicePassword("Admin123");
        list.add(device);
        list.add(devic1);

        //登录设备，每一台设备分别登录; 登录句柄是唯一的，可以区分设备
        HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo;//设备登录信息
        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo;//设备信息

        for (ConGasStationMonitoring d : list) {
            m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();//设备登录信息
            m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();//设备信息
            String m_sDeviceIP = d.getDeviceIp();//设备ip地址
            m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
            System.arraycopy(m_sDeviceIP.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, m_sDeviceIP.length());

            String m_sUsername = d.getDeviceUsername();//设备用户名
            m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
            System.arraycopy(m_sUsername.getBytes(), 0, m_strLoginInfo.sUserName, 0, m_sUsername.length());

            String m_sPassword = d.getDevicePassword();//设备密码
            m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
            System.arraycopy(m_sPassword.getBytes(), 0, m_strLoginInfo.sPassword, 0, m_sPassword.length());

            m_strLoginInfo.wPort = 8000; //SDK端口
           *//* m_strLoginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是*//*
            m_strLoginInfo.write();
            lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP, (short) 8000, drvice.getDeviceUsername(), drvice.getDevicePassword(), m_strDeviceInfo);
            //将登陆返回的用户句柄保存！（这里很重要 是原先官网没有的，这里保存句柄是为了预览使用）
            lUserIDList.add(lUserID);
            long userID = lUserID.longValue();
            if (userID == -1) {
                System.out.println("登录失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            } else {
                System.out.println("设备登录成功! " + "设备序列号:" + new String(m_strDeviceInfo.struDeviceV30.sSerialNumber).trim());
                m_strDeviceInfo.read();
            }
            //保存回调函数的音频数据
            VideoDemo.setFile(lUserID);

        }
    }*/


    /**
     * @return boolean
     * @Author cxb
     * @Description: 注销
     * @Date 9:08 2019/7/12
     * @Param [drvice 设备信息]
     **/
    public boolean Logout_V30(long userId) {
        //如果已经注册,注销
        if (lUserID.longValue() > -1 && hCNetSDK.NET_DVR_Logout_V30(lUserID)) {
            //cleanup SDK
            return hCNetSDK.NET_DVR_Cleanup();
        }
        return false;
    }


    /**
     * @return boolean
     * @Author cxb
     * @Description: 初始化
     * @Date 17:21 2023/8/18
     **/
    public boolean Init() {
        boolean initSuc = hCNetSDK.NET_DVR_Init();
        if (initSuc != true) {
            System.out.println("初始化失败");
            return false;
        } else {
            return true;
        }
    }

    /**
     * @return void
     * @Author cxb
     * @Description: 下载
     * @Date 9:10 2019/7/12
     * @Param [drvice, struStartTime, struStopTime]
     **/
    public void jButtonDownloadActionPerformed(ConGasStationMonitoring drvice, HCNetSDK.NET_DVR_TIME struStartTime, HCNetSDK.NET_DVR_TIME struStopTime, String strFileName) {
        PlaybackService dlgPlayTime = new PlaybackService(this, false, lUserID, m_sDeviceIP);
        dlgPlayTime.jButtonDownloadActionPerformed(drvice, struStartTime, struStopTime, strFileName);
    }

    /**
     * @return
     * @Author cxb
     * @Description: 播放
     * @Date 9:10 2019/7/12
     * @Param
     **/
    public void jButtonPlayActionPerformed(ConGasStationMonitoring drvice,
                                           HCNetSDK.NET_DVR_TIME struStartTime,
                                           HCNetSDK.NET_DVR_TIME struStopTime, String strFileName) {
        PlaybackService dlgPlayTime = new PlaybackService(this, false, lUserID, m_sDeviceIP);
        dlgPlayTime.jButtonPlayActionPerformed(drvice, struStartTime, struStopTime, strFileName);
    }

    /**
     * @return void
     * @Author cxb
     * @Description: 停止
     * @Date 9:10 2019/7/12
     * @Param [value]
     **/
    public void jButtonStopActionPerformed(long value) {
        NativeLong m_lPlayHandleint = new NativeLong(value);
        PlaybackService dlgPlayTime = new PlaybackService(this, false, lUserID, m_sDeviceIP);
        dlgPlayTime.StopPlay(m_lPlayHandleint);
    }

    /**
     * @return void
     * @Author cxb
     * @Description: 播放
     * @Date 9:22 2019/7/26
     * @Param [drvice]
     **/
    public void jButtonRealPlayActionPerformed(ConGasStationMonitoring drvice, String strFileName) {
        PlaybackService playbackService = new PlaybackService(this, false, lUserID, m_sDeviceIP);
        playbackService.jButtonRealPlayActionPerformed(drvice, strFileName);
    }


}

