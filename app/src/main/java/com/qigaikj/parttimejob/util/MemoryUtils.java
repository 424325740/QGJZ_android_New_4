package com.qigaikj.parttimejob.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Shsy on 2016/5/5.
 * 关于手机存储的一些工具类
 */
public class MemoryUtils {
    /**
     * 项目根目录名称
     */
    private static String PROGECTNAME = "LenongHelp";

    /**
     * 项目文件路径
     */
    public static class Path {
        /**
         * 项目根目录
         */
        public static final String JUXIN_ROOT_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + PROGECTNAME + "/";
        /**
         * 项目图片目录
         */
        public static final String JUXIN_IMAGE_DIRECTORY = JUXIN_ROOT_DIRECTORY + "image/";
        /**
         * 项目音频目录
         */
        public static final String JUXIN_AUDIO_DIRECTORY = JUXIN_ROOT_DIRECTORY + "audio/";
        /**
         * 项目LOG目录
         */
        public static final String JUXIN_LOG_DIRECTORY = JUXIN_ROOT_DIRECTORY + "log/";
        /**
         * 项目视频目录
         */
        public static final String JUXIN_VIDEO_DIRECTORY=JUXIN_ROOT_DIRECTORY+"video/";
    }

    /**
     * SD卡是否可用
     */
    public static boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * 初始化工作目录
     */
    public static void createDirectory(Context context) {
        if (MemoryUtils.existSDCard()) {// 内存卡可用
            if (!new File(Path.JUXIN_ROOT_DIRECTORY).exists()) {// 根目录
                new File(Path.JUXIN_ROOT_DIRECTORY).mkdir();
                Log.i("asdf", "创建根目录");
            }
            if (!new File(Path.JUXIN_IMAGE_DIRECTORY).exists()) {// 图片目录
                new File(Path.JUXIN_IMAGE_DIRECTORY).mkdir();
                Log.i("asdf", "图片目录");
            }
            if (!new File(Path.JUXIN_AUDIO_DIRECTORY).exists()) {// 音频目录
                new File(Path.JUXIN_AUDIO_DIRECTORY).mkdir();
                Log.i("asdf", "音频目录");
            }
        } else {
            Toast.makeText(context, "内存卡不可用", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获得SD卡总大小
     *
     * @return
     */
    public static String getSDTotalSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     *
     * @return
     */
    public static String getSDAvailableSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);
    }

    /**
     * 获得机身内存总大小
     *
     * @return
     */
    public static String getRomTotalSize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    /**
     * 获得机身可用内存
     *
     * @return
     */
    public static String getRomAvailableSize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);
    }
}
