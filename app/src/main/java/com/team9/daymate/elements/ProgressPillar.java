package com.team9.daymate.elements;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;

import com.team9.daymate.R;

public class ProgressPillar extends View {

    private static final int DEFAULT_PROGRESS_MIN = 0;
    private static final int DEFAULT_PROGRESS_MAX = 100;
    private static final int DEFAULT_FOREGROUND = Color.BLUE;
    private static final int DEFAULT_BACKGROUND = Color.DKGRAY;
    private static final int DEFAULT_COLORALPHA = 255;
    private static final float DEFAULT_THICKNESS = 4;


    private float strokeWidth;
    private float progress;
    private int progressMax;
    private int progressMin;



    private int foregroundColor = DEFAULT_FOREGROUND;
    private int backgroundColor = DEFAULT_BACKGROUND;
    private int colorAlpha = DEFAULT_COLORALPHA;


    private final RectF drawableRect = new RectF();
    private final RectF pillarRect = new RectF();

    private final Paint foregroundPaint = new Paint();
    private final Paint backgroundPaint = new Paint();
    private final Matrix shaderMatrix = new Matrix();

    private Bitmap bitmap;
    private Canvas bitmapCanvas;

    private float drawableRadius;
    private float borderRadius;

    private boolean initialized;
    private boolean drawableDirty;
    private boolean rebuildShader;



    public ProgressPillar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressPillar, defStyle, 0);

        try {
            progress = a.getFloat(R.styleable.ProgressPillar_progress, 0f);
            progressMax = a.getInt(R.styleable.ProgressPillar_max, DEFAULT_PROGRESS_MAX);
            progressMin = a.getInt(R.styleable.ProgressPillar_min, DEFAULT_PROGRESS_MIN);
            foregroundColor = a.getColor(R.styleable.ProgressPillar_color, DEFAULT_FOREGROUND);
            backgroundColor = a.getColor(R.styleable.ProgressPillar_backgroundColor, DEFAULT_BACKGROUND);
            strokeWidth = a.getFloat(R.styleable.ProgressPillar_pillarWidth, DEFAULT_THICKNESS);
        }finally{
            a.recycle();
        }

        initialize();

    }

    public ProgressPillar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public ProgressPillar(Context context){ super(context); initialize(); }

    private void initialize(){

        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);

        foregroundPaint.setAntiAlias(true);
        foregroundPaint.setColor(foregroundColor);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(strokeWidth);
        foregroundPaint.setAlpha(colorAlpha);

        initialized = true;
    }

    @SuppressLint("CanvasSize")
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        if (rebuildShader) {
            rebuildShader = false;

            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            bitmapShader.setLocalMatrix(shaderMatrix);

            foregroundPaint.setShader(bitmapShader);
        }

        float radius  = Math.min((drawableRect.height() - strokeWidth) / 2.0f, (drawableRect.width() - strokeWidth) / 2.0f);
        float radius2 = Math.min(drawableRect.height() / 2.0f, drawableRect.width() / 2.0f);

        if(strokeWidth > 0){
            drawableDirty = false;
            float height = (canvas.getHeight() * (progressMax - progress)/progressMax);


            canvas.drawRect(new RectF(0, canvas.getHeight(),0,0), backgroundPaint);
            canvas.drawLine(drawableRect.left, height, drawableRect.right, drawableRect.bottom, foregroundPaint);

        }

    }

    private void updateDimensions() {
        drawableRect.set(calculateBounds());

        if ( strokeWidth > 0) {
           //drawableRect.inset(strokeWidth- 1.0f, strokeWidth - 1.0f);
        }

        //updateShaderMatrix();
    }

    private RectF calculateBounds() {
        int availableWidth  = getWidth() - getPaddingLeft() - getPaddingRight();
        int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        int sideLength = Math.min(availableWidth, availableHeight);

        float left = getPaddingLeft() + (availableWidth - sideLength);
        float top = getPaddingTop() + (availableHeight - sideLength) ;

        return new RectF(left, top, left + sideLength, top + sideLength);
    }

    private void updateShaderMatrix() {
        if (bitmap == null) {
            return;
        }

        float scale;
        float dx = 0;
        float dy = 0;

        shaderMatrix.set(null);

        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();

        if (bitmapWidth * drawableRect.height() > drawableRect.width() * bitmapHeight) {
            scale = drawableRect.height() / (float) bitmapHeight;
            dx = (drawableRect.width() - bitmapWidth * scale) * 0.5f;
        } else {
            scale = drawableRect.width() / (float) bitmapWidth;
            dy = (drawableRect.height() - bitmapHeight * scale) * 0.5f;
        }

        shaderMatrix.setScale(scale, scale);
        shaderMatrix.postTranslate((int) (dx + 0.5f) + drawableRect.left, (int) (dy + 0.5f) + drawableRect.top);

        rebuildShader = true;
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable dr) {
        drawableDirty = true;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        invalidate();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        invalidate();
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height);
        setMeasuredDimension((int) strokeWidth / 2, min);
        drawableRect.set(0, 0, 0 , min);


        updateShaderMatrix();
    }


    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        backgroundPaint.setStrokeWidth(strokeWidth);
        foregroundPaint.setStrokeWidth(strokeWidth);
        invalidate();
        requestLayout(); // recalculate raja-arvot
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public int getProgressMax() {
        return progressMax;
    }

    public void setProgressMax(int max) {
        this.progressMax = max;
        invalidate();
    }

    public int getForegroundColor() {
        return foregroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setColor(int color) {
        this.foregroundColor = color;
        backgroundPaint.setColor(adjustAlpha(color, 0.3f));
        foregroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public void setForegroundColor(int color) {
        this.foregroundColor = color;
        foregroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public void setBackgroundPaintColor(int color) {
        this.backgroundColor = color;
        backgroundPaint.setColor(adjustAlpha(color, 0.3f));
        invalidate();
        requestLayout();
    }


    /**
     * Vaalentaa väriä
     *
     * @param color  Vaannettava väri
     * @param factor 0 kautta 4
     * @return Vaalennettu väri
     */
    public int lightenColor(int color, float factor) {
        float r = Color.red(color) * factor;
        float g = Color.green(color) * factor;
        float b = Color.blue(color) * factor;
        int ir = Math.min(255, (int) r);
        int ig = Math.min(255, (int) g);
        int ib = Math.min(255, (int) b);
        int ia = Color.alpha(color);
        return (Color.argb(ia, ir, ig, ib));
    }

    /**
     * Lisää värin läpinäkyvyyttä annetulla tekijällä
     * Mitä enemmän tekijä on lähempänä nollaa, sitä enemmän väri muuttuu läpinäkyväksi
     *
     * @param color  Kohdeväri
     * @param factor 1.0f kautta 0.0f
     * @return int - Läpinäkyvä väri
     */
    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
    /**
     * Aseta elementin reunan edistys animaatiolla
     *
     * @param progress edistys jota piirretään animaatiolla
     */
    public void setProgressWithAnimation(float progress) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }






}


