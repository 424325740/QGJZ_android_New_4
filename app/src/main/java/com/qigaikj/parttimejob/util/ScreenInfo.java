package com.qigaikj.parttimejob.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * 屏幕相关信息
 * @author androidloveme
 *
 */
public class ScreenInfo {
	/**
	 * 获取DisplayMetrics对象，已获取屏幕宽、高、密度等信息
	 * @param activity
	 * @return
	 */
	public static DisplayMetrics getScreenInfo(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm;
	}

}
