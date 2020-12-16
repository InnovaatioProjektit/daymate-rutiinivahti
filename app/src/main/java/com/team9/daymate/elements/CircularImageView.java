package com.team9.daymate.elements;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.math.MathUtils;

import com.team9.daymate.R;

/**
 * CircularImageView on interaktiivinen käyttöliittymän komponentti, joka piirtää pyöreän
 * kuvakomponentin jota ympäröi graafisesti muokattava kehä. Kehän edistysluku {@see CircularImageView#progressStep}
 * määritsee sen pituuden suhteessa kattolukuun {@see CircularImageView#progressMax}
 *
 * Käyttö layoutissa: app:circle_color
 *                    app:circle_backgroundColor
 *                    app:circle_progress
 *                    app:circle_min
 *                    app:circle_max
 *
 * author Alexander L
 * author Jaakko Buchelnikov
 * @version 1.0
 */
@SuppressLint("AppCompatCustomView")
public class CircularImageView extends AppCompatImageView {

    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;

    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final int DEFAULT_CIRCLE_BACKGROUND_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_IMAGE_ALPHA = 255;
    private static final boolean DEFAULT_BORDER_OVERLAY = false;

    private static final int DEFAULT_PROGRESS_MIN = 0;
    private static final int DEFAULT_PROGRESS_MAX = 100;


    private final RectF drawableRect = new RectF();
    private final RectF borderRect = new RectF();

    private final Matrix shaderMatrix = new Matrix();
    private final Paint bitmapPaint = new Paint();
    private final Paint borderPaint = new Paint();
    private final Paint backgroundPaint = new Paint();

    private int borderColor = DEFAULT_BORDER_COLOR;
    private int borderWidth = DEFAULT_BORDER_WIDTH;
    private int backgroundColor = DEFAULT_CIRCLE_BACKGROUND_COLOR;
    private int backgroundTint = DEFAULT_CIRCLE_BACKGROUND_COLOR;
    private int imageAlpha = DEFAULT_IMAGE_ALPHA;
    private int imageScale = 0;

    private Bitmap bitmap;
    private Canvas bitmapCanvas;

    private float drawableRadius;
    private float borderRadius;


    private float progressStep;
    private int progressMax;
    private int progressMin;
    private int progressAngleStart = -90;

    private ColorFilter colorFilter;

    private boolean Initialized;
    private boolean rebuildShader;
    private boolean drawableDirty;

    private boolean borderOverlay;
    private boolean disableTransformation;
    private Object routine;

    private void initialize(){
        super.setScaleType(SCALE_TYPE);

        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setDither(true);
        bitmapPaint.setFilterBitmap(true);
        bitmapPaint.setAlpha(imageAlpha);
        bitmapPaint.setColorFilter(colorFilter);

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);

        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setColor(backgroundColor);

        Initialized = true;

    }

    public CircularImageView(Context context){
        super(context);
        initialize();

    }

    public CircularImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView, defStyle, 0);

        borderWidth = a.getDimensionPixelSize(R.styleable.CircularImageView_circle_width, DEFAULT_BORDER_WIDTH);
        borderColor = a.getColor(R.styleable.CircularImageView_circle_color, DEFAULT_BORDER_COLOR);
        borderOverlay = a.getBoolean(R.styleable.CircularImageView_circle_overlay, DEFAULT_BORDER_OVERLAY);
        backgroundColor = a.getColor(R.styleable.CircularImageView_circle_backgroundColor, DEFAULT_CIRCLE_BACKGROUND_COLOR);
        backgroundTint = a.getColor(R.styleable.CircularImageView_circle_backgroundTint, DEFAULT_CIRCLE_BACKGROUND_COLOR);
        progressStep = a.getFloat(R.styleable.CircularImageView_circle_progress, DEFAULT_CIRCLE_BACKGROUND_COLOR);
        progressMax    = a.getInteger(R.styleable.CircularImageView_circle_max, DEFAULT_PROGRESS_MAX);
        progressMin   = a.getInteger(R.styleable.CircularImageView_circle_min, DEFAULT_PROGRESS_MIN);
        imageScale   = a.getInteger(R.styleable.CircularImageView_circle_iconScale, DEFAULT_PROGRESS_MIN);

        a.recycle();
        initialize();
    }


    /**
     * onDraw piirtää ympyrän muotoisen komponentin jonka kehän pituus on suhteessa edistysakseliin {@see CircularImageView#progressStep}
     * Default toiminta on kun kehän tausta perii kehän värin syvemmät piirteet. Kaikki värit ovat muokattavissa käyttäjätasolla.,
     * @author Alexander L
     * @param canvas Piirtopinta
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (disableTransformation) {
            super.onDraw(canvas);
            return;
        }

        if (backgroundColor != Color.TRANSPARENT) {
            canvas.drawCircle(drawableRect.centerX(), drawableRect.centerY(), drawableRadius, backgroundPaint);
        }

        if (bitmap != null) {
            if (drawableDirty && bitmapCanvas != null) {
                drawableDirty = false;
                Drawable drawable = getDrawable();
                drawable.setBounds(10, 10, bitmapCanvas.getWidth() - 10, bitmapCanvas.getHeight() - 10 );
                drawable.draw(bitmapCanvas);
            }

            if (rebuildShader) {
                rebuildShader = false;

                BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                bitmapShader.setLocalMatrix(shaderMatrix);

                bitmapPaint.setShader(bitmapShader);
            }

            canvas.drawCircle(drawableRect.centerX(), drawableRect.centerY(), drawableRadius, bitmapPaint);
        }

        if (borderWidth > 0) {
            if(backgroundTint != DEFAULT_CIRCLE_BACKGROUND_COLOR){
                borderPaint.setColor(backgroundTint);
            }else {
                borderPaint.setColor(lightenColor(borderColor, 0.3f));
            }

            canvas.drawOval(borderRect, borderPaint);
            float angle = 360 * progressStep / progressMax;
            borderPaint.setColor(borderColor);
            canvas.drawArc(borderRect, progressAngleStart, angle, false, borderPaint);
        }
    }

    /**
     * Vaalentaa väriä
     *
     * @author Jaakko Buchelnikov
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
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height);
        setMeasuredDimension(min, min);
        borderRect.set(0 + (float)borderWidth / 2, 0 + (float)borderWidth / 2, min - (float)borderWidth / 2, min - (float)borderWidth / 2);
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable dr) {
        drawableDirty = true;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateDimensions();
        invalidate();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        updateDimensions();
        invalidate();
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        updateDimensions();
        invalidate();
    }


    public float getProgress() {
        return progressStep;
    }

    public void setProgress(float progress){
        this.progressStep = progress;
        invalidate();
    }

    public int getProgressMin() {
        return progressMin;
    }

    public void setProgressMin(int min) {
        progressMin = min;
        drawableDirty = true;
        invalidate();
    }

    public int getProgressMax() {
        return progressMax;
    }

    public void setProgressMax(int max) {
        progressMax = max;
        drawableDirty = true;
        invalidate();
    }

    public void setProgressAngleStart(int angle){
        progressAngleStart = MathUtils.clamp(angle, -360, 360);
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(@ColorInt int borderColor) {
        if (borderColor == this.borderColor) {
            return;
        }

        this.borderColor = borderColor;
        borderPaint.setColor(borderColor);
        drawableDirty = true;
        invalidate();
    }

    public int getCircleBackgroundColor() {
        return backgroundColor;
    }

    public void setCircleBackgroundColor(@ColorInt int circleBackgroundColor) {
        if (circleBackgroundColor == backgroundColor) {
            return;
        }

        backgroundColor = circleBackgroundColor;
        backgroundPaint.setColor(circleBackgroundColor);
        drawableDirty = true;
        invalidate();
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        if (borderWidth == this.borderWidth) {
            return;
        }

        this.borderWidth = borderWidth;
        borderPaint.setStrokeWidth(borderWidth);
        updateDimensions();
        drawableDirty = true;
        invalidate();
    }

    public boolean isBorderOverlay() {
        return borderOverlay;
    }

    public void setBorderOverlay(boolean borderOverlay) {
        if (borderOverlay == this.borderOverlay) {
            return;
        }

        this.borderOverlay = borderOverlay;
        updateDimensions();
        drawableDirty = true;
        invalidate();
    }

    public boolean isDisableCircularTransformation() {
        return disableTransformation;
    }

    public void setDisableCircularTransformation(boolean disableCircularTransformation) {
        if (disableCircularTransformation == disableTransformation) {
            return;
        }

        disableTransformation = disableCircularTransformation;

        if (disableCircularTransformation) {
            bitmap = null;
            bitmapCanvas = null;
            bitmapPaint.setShader(null);
        } else {
            initializeBitmap();
        }

        invalidate();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        initializeBitmap();
        invalidate();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        initializeBitmap();
        invalidate();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        initializeBitmap();
        invalidate();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        initializeBitmap();
        invalidate();
    }

    @Override
    public void setImageAlpha(int alpha) {
        alpha &= 0xFF;

        if (alpha == imageAlpha) {
            return;
        }

        imageAlpha = alpha;
        if (Initialized) {
            bitmapPaint.setAlpha(alpha);
            invalidate();
        }
    }

    @Override
    public int getImageAlpha() {
        return imageAlpha;
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (cf == colorFilter) {
            return;
        }

        colorFilter = cf;
        if (Initialized) {
            bitmapPaint.setColorFilter(cf);
            invalidate();
        }
    }

    @Override
    public ColorFilter getColorFilter() {
        return colorFilter;
    }


    /**
     * Hakee resursseista piirretyn kuvan ja muuttaa se bittilistaksi
     *
     * @param drawable Resurssitiedosto
     * @return Bitmap Palauttaa binäärisen kuvadatan
     */
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(2, 2, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(10, 10, canvas.getWidth() - 10, canvas.getHeight() - 10 );
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initializeBitmap() {
        bitmap = getBitmapFromDrawable(getDrawable());

        if (bitmap != null && bitmap.isMutable()) {
            bitmapCanvas = new Canvas(bitmap);
        } else {
            bitmapCanvas = null;
        }

        if (!Initialized) {
            return;
        }

        if (bitmap != null) {
            updateShaderMatrix();
        } else {
            bitmapPaint.setShader(null);
        }
    }


    /**
     * @deprecated {@see CircularImageView#setMeasure} tekee saman koordinaattien kaappauksen
     * @author Alexander L
     */
    private void updateDimensions() {
        borderRect.set(calculateBounds());
        borderRadius = Math.min((borderRect.height() - borderWidth) / 2.0f, (borderRect.width() - borderWidth) / 2.0f);

        drawableRect.set(borderRect);
        if (!borderOverlay && borderWidth > 0) {
            drawableRect.inset(borderWidth - 1.0f, borderWidth - 1.0f);
        }
        drawableRadius = Math.min(drawableRect.height() / 2.0f, drawableRect.width() / 2.0f);

        updateShaderMatrix();
    }

    private RectF calculateBounds() {
        int availableWidth  = getWidth() - getPaddingLeft() - getPaddingRight();
        int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        int sideLength = Math.min(availableWidth, availableHeight);

        float left = getPaddingLeft() + (availableWidth - sideLength) / 2f;
        float top = getPaddingTop() + (availableHeight - sideLength) / 2f;

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


    /**
     * @author Alexander L
     * @param event Hiiriliikkeen tapahtuma
     * @return boolean kosketuksen tila ympyrän sisällä
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (disableTransformation) {
            return super.onTouchEvent(event);
        }

        return inTouchableArea(event.getX(), event.getY()) && super.onTouchEvent(event);
    }

    private boolean inTouchableArea(float x, float y) {
        if (borderRect.isEmpty()) {
            return true;
        }
        return Math.pow(x - borderRect.centerX(), 2) + Math.pow(y - borderRect.centerY(), 2) <= Math.pow(borderRadius, 2);
    }


    /**
     * Aseta elementin reunan edistystä vastaavaa palkki animaatiolla ympäri
     *
     * @author Alexander L
     * @param progress edistys jota piirretään animaatiolla
     */
    public void setProgressWithAnimation(float progress) {

        if(progressStep >= progressMax){
            drawableDirty = true;
            invalidate();
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }

    /**
     * TODO: Hacking
     */

    public void attachObject(Object routine){
        this.routine = routine;
    }

    public Object pullObject(){
        return this.routine;
    }

}