package com.qigaikj.parttimejob.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

public class MovingViewAnimator {
    /**
     * 水平移动
     */
    public static final int HORIZONTAL_MOVE = 1;
    /**
     * 垂直移动
     */
    public static final int VERTICAL_MOVE = 2;
    /**
     * 对角线移动
     */
    public static final int DIAGONAL_MOVE = 3;
    /**
     * 自动移动
     */
    public static final int AUTO_MOVE = 0;
    /**
     * 不移动
     */
    public static final int NONE_MOVE = -1;
    /**
     * 动画作用视图的状态
     */
    private AnimatorSet mAnimatorSet;//动画集合
    private View mView;
    private boolean isRunning;//是否允许中
    private int currentLoop;//当前轮询位置
    private boolean infiniteRepetition = true; //无限重复
 
    private ArrayList<Float> pathDistances;
    private Interpolator mInterpolator;//插入器
 
    private int loopCount = -1;
    private int movementType;
    private float offsetWidth, offsetHeight;
    private int mSpeed = 50;
    private long mDelay = 0;
 
    public MovingViewAnimator(View imgView) {
        mView = imgView;
        isRunning = false;
        mAnimatorSet = new AnimatorSet();
        pathDistances = new ArrayList<>();
        mInterpolator = new AccelerateDecelerateInterpolator();
    }
 
    /**
     * 动画结束监听
     */
    private Animator.AnimatorListener animatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(final Animator animation) {
            //super.onAnimationEnd(animation);
            //运行在主线程
            mView.post((new Runnable() {
                public void run() {
                    if (isRunning) {
                        if (infiniteRepetition) {
                            mAnimatorSet.start();
                        } else {
                            currentLoop--;
                            if (currentLoop > 0) {
                                mAnimatorSet.start();
                            }
                        }
                    }
                }
            }));
        }
    };
    /**
     * 更新动画值.
     *
     * @param type
     * @param w
     * @param h
     */
    public void updateValues(int type, float w, float h) {
        this.movementType = type;
        this.offsetWidth = w;
        this.offsetHeight = h;
        init();
    }
    /**
     * 初始化
     */
    private void init() {
        setUpAnimator();
        setUpValues();
    }
    /**
     * 根据移动类型设置不同的动画
     */
    private void setUpAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        pathDistances.clear();
 
        switch (movementType) {
            case HORIZONTAL_MOVE:
                animatorSet.playSequentially(createHorizontalAnimator(0, offsetWidth),
                        createHorizontalAnimator(offsetWidth, 0));
                break;
            case VERTICAL_MOVE:
                animatorSet.playSequentially(createVerticalAnimator(0, offsetHeight),
                        createVerticalAnimator(offsetHeight, 0));
                break;
            case DIAGONAL_MOVE:
                animatorSet.playSequentially(createDiagonalAnimator(0, offsetWidth, 0,
                        offsetHeight),
                        createDiagonalAnimator(offsetWidth, 0, offsetHeight, 0));
                break;
            case AUTO_MOVE:
                animatorSet.playSequentially(
                        createVerticalAnimator(0, offsetHeight),
                        createDiagonalAnimator(0, offsetWidth, offsetHeight, 0),
                        createHorizontalAnimator(offsetWidth, 0),
                        createDiagonalAnimator(0, offsetWidth, 0, offsetHeight),
                        createHorizontalAnimator(offsetWidth, 0),
                        createVerticalAnimator(offsetHeight, 0));
        }
 
        if (mAnimatorSet != null) {
            mAnimatorSet.removeAllListeners();
            stop();
        }
        mAnimatorSet = animatorSet;
    }
    /**
     * 设置参数数据
     */
    private void setUpValues() {
        setSpeed(mSpeed);
        setStartDelay(mDelay);
        setRepetition(loopCount);
        setInterpolator(mInterpolator);
    }
    /**
     * 创建水平移动动画
     *
     * @param startValue
     * @param endValue
     * @return
     */
    private ObjectAnimator createHorizontalAnimator(float startValue, float endValue) {
        pathDistances.add(Math.abs(startValue - endValue));
        return createObjectAnimation("scrollX", startValue, endValue);
    }
    /**
     * 创建垂直移动动画
     *
     * @param startValue
     * @param endValue
     * @return
     */
    private ObjectAnimator createVerticalAnimator(float startValue, float endValue) {
        pathDistances.add(Math.abs(startValue - endValue));
        return createObjectAnimation("scrollY", startValue, endValue);
    }
    private ObjectAnimator createObjectAnimation(String prop, float startValue, float endValue) {
        return ObjectAnimator.ofInt(mView, prop, (int) startValue, (int) endValue);
    }
    /**
     * 创建对角线移动动画
     *
     * @param startW
     * @param endW
     * @param startH
     * @param endH
     * @return
     */
    private ObjectAnimator createDiagonalAnimator(float startW, float endW, float startH, float
            endH) {
        float diagonal = Pythagoras(Math.abs(startW - endW), Math.abs(startH - endH));
        pathDistances.add(diagonal);
        PropertyValuesHolder pvhX = createPropertyValuesHolder("scrollX", startW, endW);
        PropertyValuesHolder pvhY = createPropertyValuesHolder("scrollY", startH, endH);
        return ObjectAnimator.ofPropertyValuesHolder(mView, pvhX, pvhY);
    }
 
    private PropertyValuesHolder createPropertyValuesHolder(String prop, float startValue, float
            endValue) {
        return PropertyValuesHolder.ofInt(prop, (int) startValue, (int) endValue);
    }
 
    private static float Pythagoras(float a, float b) {
        return (float) Math.sqrt((a * a) + (b * b));
    }
    /**
     * 设置每个动画对应的持续时间
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        mSpeed = speed;
        List<Animator> listAnimator = mAnimatorSet.getChildAnimations();
        for (int i = 0; i < listAnimator.size(); i++) {
            Animator a = listAnimator.get(i);
            a.setDuration(parseSpeed(pathDistances.get(i)));
        }
    }
    /**
     * 将速度设置值转换成秒
     *
     * @param distance
     * @return
     */
    private long parseSpeed(float distance) {
        return (long) ((distance / (float) mSpeed) * 1000f);
    }
    /**
     * 设置动画播放之前的延时时间
     *
     * @param time
     */
    public void setStartDelay(long time) {
        mDelay = time;
        mAnimatorSet.setStartDelay(time);
    }
    /**
     * 设置重复模式
     *
     * @param repetition repetition < 0 循环播放
     *                   repetition > 0 循环repetition次
     */
    public void setRepetition(int repetition) {
        if (repetition < 0)
            infiniteRepetition = true;
        else {
            loopCount = repetition;
            currentLoop = loopCount;
            infiniteRepetition = false;
        }
    }
    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        mAnimatorSet.setInterpolator(interpolator);
    }
 
    /**
     * 停止
     */
    public void stop() {
        //Log.e("tag", "stop.");
        isRunning = false;
        mAnimatorSet.removeListener(animatorListener);
        mAnimatorSet.end();
        mView.clearAnimation();
    }
    public void start() {
        //Log.e("tag", "start.");
        if (movementType != NONE_MOVE) {
            isRunning = true;
            if (!infiniteRepetition)
                currentLoop = loopCount;
            setListener();
            mAnimatorSet.start();
        }
    }
 
    private void setListener() {
        mAnimatorSet.addListener(animatorListener);
    }
 
 
}
