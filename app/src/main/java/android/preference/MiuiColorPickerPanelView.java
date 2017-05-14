package android.preference;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MiuiColorPickerPanelView extends View {
    private final static float BORDER_WIDTH_PX = 1;
    private float mDensity = 1f;

    private int mBorderColor = 0xff6E6E6E;
    private int mColor = 0xff000000;

    private Paint mBorderPaint;
    private Paint mColorPaint;

    private RectF mDrawingRect;
    private RectF mColorRect;

    private MiuiColorAlphaPatternDrawable mAlphaPattern;

    public MiuiColorPickerPanelView(Context context) {

        super(context, null);
    }

    public MiuiColorPickerPanelView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initialization();
    }

    public MiuiColorPickerPanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialization();
    }

    private void initialization() {
        mBorderPaint = new Paint();
        mColorPaint = new Paint();
        mDensity = getContext().getResources().getDisplayMetrics().density;
    }


    private void setUpColorRect() {
        final RectF dRect = mDrawingRect;

        float left = dRect.left + BORDER_WIDTH_PX;
        float top = dRect.top + BORDER_WIDTH_PX;
        float bottom = dRect.bottom - BORDER_WIDTH_PX;
        float right = dRect.right - BORDER_WIDTH_PX;

        mColorRect = new RectF(left, top, right, bottom);

        mAlphaPattern = new MiuiColorAlphaPatternDrawable((int) (5 * mDensity));

        mAlphaPattern.setBounds(
                Math.round(mColorRect.left),
                Math.round(mColorRect.top),
                Math.round(mColorRect.right),
                Math.round(mColorRect.bottom)
        );

    }


    @Override
    protected void onDraw(Canvas canvas) {

        final RectF rect = mColorRect;

        if (BORDER_WIDTH_PX > 0) {
            mBorderPaint.setColor(mBorderColor);
            canvas.drawRect(mDrawingRect, mBorderPaint);
        }

        if (mAlphaPattern != null) {
            mAlphaPattern.draw(canvas);
        }

        mColorPaint.setColor(mColor);

        canvas.drawRect(rect, mColorPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mDrawingRect = new RectF();
        mDrawingRect.left = getPaddingLeft();
        mDrawingRect.right = w - getPaddingRight();
        mDrawingRect.top = getPaddingTop();
        mDrawingRect.bottom = h - getPaddingBottom();

        setUpColorRect();

    }


    public void setColor(int color) {
        mColor = color;
        invalidate();
    }

    public int getColor() {
        return mColor;
    }

    public void setBorderColor(int color) {
        mBorderColor = color;
        invalidate();
    }

    public int getBorderColor() {
        return mBorderColor;
    }
}