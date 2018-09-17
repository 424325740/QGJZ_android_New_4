package com.qigaikj.parttimejob.menuList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {
	private List<ImageView> imglist;
    public MyViewPagerAdapter(List<ImageView> imglist){
    	this.imglist=imglist;
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		int p=position%imglist.size();
		ImageView img=imglist.get(p);
		ViewParent vp=img.getParent();
		if(vp!=null){
			ViewGroup parent=(ViewGroup) vp;
			parent.removeView(img);
		}
		container.addView(imglist.get(p));
		return imglist.get(p);
	}
//	@Override
//	public void destroyItem(ViewGroup container, int position, Object object) {
////		// TODO Auto-generated method stub
////		int p=position%imglist.size();
////		container.removeView(imglist.get(p));
//
//	}


	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
	}
}
