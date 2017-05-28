package iammert.com.huelib;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by mertsimsek on 28/05/2017.
 */

public class HueSeekBar extends View {

    /**
     * Default values
     */
    private static final int DEFAULT_COLOR_FILL = 0x40000000;
    private static final int DEFAULT_COLOR_EMPTY = 0x10000000;
    private static final int DEFAULT_COLOR_TOGGLE = 0x00000000;
    private static final int DEFAULT_MAX_PROGRESS = 100;
    private static final int DEFAULT_BOTTOM_SEEK_HEIGHT_PX = 50;
    private static final int DEFAULT_TOGGLE_WIDTH_PX = 50;

    /**
     * Colors
     */
    private int colorEmpty;
    private int colorFill;
    private int colorToggle;

    /**
     * Paints
     */
    private Paint paintFill;
    private Paint paintEmpty;
    private Paint paintToggle;

    /**
     * current progress in percent
     */
    private int currentProgress;
    private int maxProgress;

    /**
     * dimension values
     */
    private int height;
    private int width;
    private int bottomSeekHeight;
    private int toggleWidth;

    /**
     * Animator on vertical and its value
     */
    private float currentHeight;
    private ObjectAnimator animator;

    private boolean isMeasured;

    /**
     * Progress and animation listener
     */
    ProgressListener progressListener;
    VerticalAnimationListener verticalAnimationListener;

    public HueSeekBar(Context context) {
        super(context);
        init(context, null);
    }

    public HueSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HueSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressLint("NewApi")
    public HueSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * Initializes style values and objects
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HueSeekBar);
        colorEmpty = a.getColor(R.styleable.HueSeekBar_colorEmpty, DEFAULT_COLOR_EMPTY);
        colorFill = a.getColor(R.styleable.HueSeekBar_colorFill, DEFAULT_COLOR_FILL);
        colorToggle = a.getColor(R.styleable.HueSeekBar_colorToggle, DEFAULT_COLOR_TOGGLE);
        maxProgress = a.getInteger(R.styleable.HueSeekBar_maxProgress, DEFAULT_MAX_PROGRESS);
        toggleWidth = a.getDimensionPixelSize(R.styleable.HueSeekBar_bottomSeekToggleWidth, DEFAULT_TOGGLE_WIDTH_PX);
        currentProgress = a.getInteger(R.styleable.HueSeekBar_currentProgress, 0);
        bottomSeekHeight = a.getDimensionPixelSize(R.styleable.HueSeekBar_bottomSeekHeight, DEFAULT_BOTTOM_SEEK_HEIGHT_PX);
        a.recycle();

        paintEmpty = new Paint();
        paintEmpty.setAntiAlias(true);
        paintEmpty.setColor(colorEmpty);
        paintEmpty.setStyle(Paint.Style.FILL);

        paintFill = new Paint();
        paintFill.setAntiAlias(true);
        paintFill.setColor(colorFill);
        paintFill.setStyle(Paint.Style.FILL);

        paintToggle = new Paint();
        paintToggle.setAntiAlias(true);
        paintToggle.setColor(colorToggle);
        paintToggle.setStyle(Paint.Style.FILL);

        animator = ObjectAnimator.ofFloat(this, "currentHeight", bottomSeekHeight);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());

        currentHeight = bottomSeekHeight;
    }

    public void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public void setVerticalAnimationListener(VerticalAnimationListener verticalAnimationListener) {
        this.verticalAnimationListener = verticalAnimationListener;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        postInvalidate();
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress > maxProgress ? maxProgress : currentProgress;
        postInvalidate();
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public float getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(float currentHeight) {
        this.currentHeight = currentHeight;
        if (verticalAnimationListener != null) {
            verticalAnimationListener.onAnimProgressChanged((int) (maxProgress * (currentHeight - bottomSeekHeight) / (height - bottomSeekHeight)));
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        if (!isMeasured) {
            if (currentProgress < toggleWidth)
                currentProgress = maxProgress * (currentProgress + toggleWidth) / width;
            animator.setFloatValues(height);
            isMeasured = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Calculate horizontal position in pixel
        float positionProgress = (float) (width * currentProgress / maxProgress);

        //Draw empty progress
        canvas.drawRect(0, height - currentHeight, width, height, paintEmpty);

        //Draw fill progress
        canvas.drawRect(0, height - currentHeight, positionProgress - toggleWidth, height, paintFill);

        //Draw toggle
        canvas.drawRect(positionProgress - toggleWidth, height - currentHeight, positionProgress, height, paintToggle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (y < height - currentHeight)
            return true;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (x < toggleWidth)
                    return true;
                currentProgress = (int) (maxProgress * x / width);
                if (progressListener != null)
                    progressListener.onProgressChange((int) (((x - toggleWidth) * maxProgress) / (width - toggleWidth)));
                invalidate();
                return true;
            case MotionEvent.ACTION_DOWN:
                if (x < toggleWidth)
                    x += toggleWidth;
                currentProgress = (int) (maxProgress * x / width);
                animator.setFloatValues(height);
                animator.start();
                return true;
            default:
                animator.setFloatValues(bottomSeekHeight);
                animator.start();
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        progressListener = null;
        verticalAnimationListener = null;
        paintFill = null;
        paintEmpty = null;
        paintToggle = null;
        animator = null;
    }
}
