package com.qigaikj.parttimejob.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;


/**
 * Created by Administrator on 2017/11/3/003.
 * 直播fragment
 */

public class FragmentThree extends Fragment {

//    private RatingBar mRatingBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_hree, container, false);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                imageView.setImageAlpha((int)(rating * 255 /5));
            }
        });
//        initView(view);
        return view;
    }
//    private void initView(View view) {
//        mRatingBar= (RatingBar) view.findViewById(R.id.rat_test);
//        //设置是否可点击，在需要评分的地方要设置为可点击
//        mRatingBar.setmClickable(true);
//        //设置星星总数
//        mRatingBar.setStarCount(5);
//        //设置星星的宽度
//        mRatingBar.setStarImageWidth(40f);
//        //设置星星的高度
//        mRatingBar.setStarImageHeight(40f);
//        //设置星星之间的距离
//        mRatingBar.setImagePadding(5f);
//        //设置空星星
//        mRatingBar.setStarEmptyDrawable(getResources()
//                .getDrawable(R.mipmap.pic_xingxing_nor));
//        //设置填充的星星
//        mRatingBar.setStarFillDrawable(getResources()
//                .getDrawable(R.mipmap.pic_xingxing_pre));
//        //设置显示的星星个数
//        mRatingBar.setStar(4.5f);
//        //设置评分的监听
//        mRatingBar.setOnRatingChangeListener(
//                new RatingBar.OnRatingChangeListener() {
//                    @Override
//                    public void onRatingChange(float RatingCount) {
//                        Toast.makeText(getActivity(), "你给出了"+
//                                        (int)(RatingCount/5*10)+"分",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}
