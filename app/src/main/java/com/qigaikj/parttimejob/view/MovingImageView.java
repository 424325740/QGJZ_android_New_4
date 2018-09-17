package com.qigaikj.parttimejob.view;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MovingImageView extends ImageView {
 
    private float canvasWidth, canvasHeight;
    private float imageWidth, imageHeight;
    private float offsetWidth, offsetHeight;
    /**
     * 移动类型
     */
    private int movementType;
    /**
     * 最小相对偏移值，图片最起码可以位移图*0.2的距离
     */
    private float minRelativeOffset=0.2f;
    /**
     * 限定最大比值，canvasHeight/drawableHeight 或者 canvasWidth/drawableWidth
     */
    private float maxRelativeSize=3.0f;
    private int mSpeed = 100;//速度
    private long startDelay =100;//开始移动时延迟
    private int mRepetitions =-1;//重复
    private boolean loadOnCreate =true;//load完毕后是否移动
 
    private MovingViewAnimator mAnimator;//自定义动画封装类
 
    public MovingImageView(Context context) {
        super(context);
    }
 
    public MovingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    private void init() {
        super.setScaleType(ScaleType.MATRIX);
        mAnimator = new MovingViewAnimator(this);
    }
 
    /**
     * 更新canvas size
     *
     * @param w    new width.
     * @param h    new height.
     * @param oldW old width.
     * @param oldH old height.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        canvasWidth = (float) w - (float) (getPaddingLeft() + getPaddingRight());
        canvasHeight = (float) h - (float) (getPaddingTop() + getPaddingBottom());
        updateAll();
    }
 
    private void updateAll() {
        if (getDrawable() != null) {
            updateImageSize();
            updateOffsets();
            updateAnimatorValues();
        }
    }
    /**
     * 更新图片Size
     */
    private void updateImageSize() {
        imageWidth = getDrawable().getIntrinsicWidth();//获取图片高度
        imageHeight = getDrawable().getIntrinsicHeight();//获取图片宽度
    }
 
    /**
     * 更新偏移量，确定动画范围
     */
    private void updateOffsets() {
        float minSizeX = imageWidth * minRelativeOffset;
        float minSizeY = imageHeight * minRelativeOffset;
        offsetWidth = (imageWidth - canvasWidth - minSizeX) > 0 ? imageWidth - canvasWidth : 0;
        offsetHeight = (imageHeight - canvasHeight - minSizeY) > 0 ? imageHeight - canvasHeight : 0;
    }
 
    /**
     * 更新动画基本数据
     */
    private void updateAnimatorValues() {
        if (canvasHeight == 0 && canvasWidth == 0)
            return;
 
        float scale = calculateTypeAndScale();
        if (scale == 0)
            return;
 
        float w = (imageWidth * scale) - canvasWidth;
        float h = (imageHeight * scale) - canvasHeight;
 
        mAnimator.updateValues(movementType, w, h);
        mAnimator.setStartDelay(startDelay);
        mAnimator.setSpeed(mSpeed);
        mAnimator.setRepetition(mRepetitions);
 
        if (loadOnCreate) {
            startMoving();
        }
    }
 
    /**
     * 设置最佳的运动类型
     * 计算缩放比例
     *
     * @return image scale.
     */
    private float calculateTypeAndScale() {
        movementType = MovingViewAnimator.AUTO_MOVE;
        float scale = 1f;
        float scaleByImage = Math.max(imageWidth / canvasWidth, imageHeight / canvasHeight);
        Matrix matrix = new Matrix();
 
        if (offsetWidth == 0 && offsetHeight == 0) {//图片太小，无法动画，需要放大
            //画布宽度/图片宽度
            float sW = canvasWidth / imageWidth;
            //画布高度/图片高度
            float sH = canvasHeight / imageHeight;
 
            if (sW > sH) {
                scale = Math.min(sW, maxRelativeSize);//限定最大缩放值
                matrix.setTranslate((canvasWidth - imageWidth * scale) / 2f, 0);
                movementType = MovingViewAnimator.VERTICAL_MOVE;//垂直移动
 
            } else if (sW < sH) {
                scale = Math.min(sH, maxRelativeSize);//限定最大缩放值
                matrix.setTranslate(0, (canvasHeight - imageHeight * scale) / 2f);
                movementType = MovingViewAnimator.HORIZONTAL_MOVE;//水平移动
 
            } else {
                scale = Math.max(sW, maxRelativeSize);//限定最大缩放值
                movementType = (scale == sW) ? MovingViewAnimator.NONE_MOVE :
                        MovingViewAnimator.DIAGONAL_MOVE;//对角线移动
            }
        } else if (offsetWidth == 0) {//宽度太小，无法执行水平动画，放大宽度
            scale = canvasWidth / imageWidth;
            movementType = MovingViewAnimator.VERTICAL_MOVE;
 
        } else if (offsetHeight == 0) {//高度太小，无法执行垂直动画，放大高度
            scale = canvasHeight / imageHeight;//求出画布高度和图片高度的比值用于确定画布起始坐标
            movementType = MovingViewAnimator.HORIZONTAL_MOVE;
 
        } else if (scaleByImage > maxRelativeSize) {//图片太大，根据最大比值设定图片缩放值
            scale = maxRelativeSize / scaleByImage;
            if (imageWidth * scale < canvasWidth || imageHeight * scale < canvasHeight) {
                scale = Math.max(canvasWidth / imageWidth, canvasHeight / imageHeight);
            }
        }
 
        matrix.preScale(scale, scale);
        setImageMatrix(matrix);
        return scale;
    }
    /**
     * 开始移动
     * 默认不停的移动
     */
    public void startMoving() {
        mAnimator.start();
    }
}
