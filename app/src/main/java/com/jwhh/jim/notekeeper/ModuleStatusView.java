package com.jwhh.jim.notekeeper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;


/**
 * TODO: document your custom view class.
 */
public class ModuleStatusView extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private  boolean [] mModuleStatus;
    private float mOutLineWidth;
    private float mShapeSize;
    private float mSpacing;
    private Rect[] mModuleRectangles;
    private int mOutlineColor;
    private Paint mPaintOutline;
    private int mFillColor;
    private Paint mPaintFill;
    private float mRadius;
    private int EDIT__MODE_MODULE_COUNT=7;
    private int mMaxHorizontalModules;

    public boolean[] getModuleStatus() {
        return mModuleStatus;
    }

    public void setModuleStatus(boolean[] mModuleStatus) {
        this.mModuleStatus = mModuleStatus;
    }

    public ModuleStatusView(Context context) {
        super(context);
        init(null, 0);
    }

    public ModuleStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ModuleStatusView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        setupModuleRectangles(w);
    }

    private void init(AttributeSet attrs, int defStyle) {

        if (isInEditMode()){
            setupEditModeValues();
        }

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ModuleStatusView, defStyle, 0);

        a.recycle();

        mOutLineWidth=6f;
        mShapeSize =144f;
        mSpacing =30f;
        mRadius= (mShapeSize-mOutLineWidth) / 2;



        mOutlineColor=Color.BLACK;
        mPaintOutline=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintOutline.setStyle(Paint.Style.STROKE);
        mPaintOutline.setStrokeWidth(mOutLineWidth);
        mPaintOutline.setColor(mOutlineColor);

        mFillColor=getContext().getResources().getColor(R.color.pluralsight_orange);
        mPaintFill=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintFill.setColor(mFillColor);




//        mExampleString = a.getString(
//                R.styleable.ModuleStatusView_exampleString);
//        mExampleColor = a.getColor(
//                R.styleable.ModuleStatusView_exampleColor,
//                mExampleColor);
//        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
//        // values that should fall on pixel boundaries.
//        mExampleDimension = a.getDimension(
//                R.styleable.ModuleStatusView_exampleDimension,
//                mExampleDimension);
//
//        if (a.hasValue(R.styleable.ModuleStatusView_exampleDrawable)) {
//            mExampleDrawable = a.getDrawable(
//                    R.styleable.ModuleStatusView_exampleDrawable);
//            mExampleDrawable.setCallback(this);
//        }



        // Set up a default TextPaint object
//        mTextPaint = new TextPaint();
//        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
//        invalidateTextPaintAndMeasurements();
    }

    private void setupEditModeValues() {
        boolean[] exampleModuleValues= new boolean[EDIT__MODE_MODULE_COUNT];
        int middle=EDIT__MODE_MODULE_COUNT/2;

        for (int i=0; i<middle; i++)
            exampleModuleValues[i]=true;
        setModuleStatus(exampleModuleValues);
    }

    private void setupModuleRectangles(int width) {

        int availableWidth=width-getPaddingLeft()-getPaddingRight();
        int horizontalModulesThatCanFit=(int)(availableWidth/(mShapeSize+mSpacing));
        int maxHorizontalModules=Math.min(horizontalModulesThatCanFit,mModuleStatus.length);


        mModuleRectangles =new Rect[mModuleStatus.length];
        for (int moduleIndex = 0; moduleIndex< mModuleRectangles.length; moduleIndex++){
            int column =moduleIndex%maxHorizontalModules;
            int row=moduleIndex/maxHorizontalModules;
            int x=getPaddingLeft()+ (int) (column*(mShapeSize + mSpacing));
            int y= getPaddingTop()+(int) (row*(mShapeSize + mSpacing));
            mModuleRectangles[moduleIndex]=new Rect(x,y, x+(int) mShapeSize, y+(int) mShapeSize);
        }
    }

//    private void invalidateTextPaintAndMeasurements() {

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth=0;
        int desiredHeight=0;

        int specWidth= MeasureSpec.getSize(widthMeasureSpec);
        int availableWidth= specWidth-getPaddingLeft()-getPaddingRight();
        int horizontalModulesThatCanFit= (int)(availableWidth/(mShapeSize+mSpacing));
        mMaxHorizontalModules= Math.min(horizontalModulesThatCanFit,mModuleStatus.length);



        desiredWidth=(int)((mMaxHorizontalModules*(mShapeSize+mSpacing))-mSpacing);
        desiredWidth+=getPaddingLeft()+getPaddingRight();

        //calculate height
        int rows =( (mModuleStatus.length-1)/mMaxHorizontalModules) +1;

        desiredHeight=(int)((rows*(mShapeSize+mSpacing))-mSpacing);
        desiredHeight+=getPaddingTop()+getPaddingBottom();

        int width=resolveSizeAndState(desiredWidth,widthMeasureSpec, 0);
        int height=resolveSizeAndState(desiredHeight,heightMeasureSpec,0);

        setMeasuredDimension(width,height);
    }
//        mTextPaint.setTextSize(mExampleDimension);
//        mTextPaint.setColor(mExampleColor);
//        mTextWidth = mTextPaint.measureText(mExampleString);
//
//        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        mTextHeight = fontMetrics.bottom;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int moduleIndex=0; moduleIndex<mModuleRectangles.length; moduleIndex++){
            float x=mModuleRectangles[moduleIndex].centerX();
            float y=mModuleRectangles[moduleIndex].centerY();



            if (mModuleStatus[moduleIndex]){

            }

            canvas.drawCircle(x,y,mRadius, mPaintFill);

            canvas.drawCircle(x,y,mRadius,mPaintOutline);


        }


//        // TODO: consider storing these as member variables to reduce
//        // allocations per draw cycle.
//        int paddingLeft = getPaddingLeft();
//        int paddingTop = getPaddingTop();
//        int paddingRight = getPaddingRight();
//        int paddingBottom = getPaddingBottom();
//
//        int contentWidth = getWidth() - paddingLeft - paddingRight;
//        int contentHeight = getHeight() - paddingTop - paddingBottom;
//
//        // Draw the text.
////        canvas.drawText(mExampleString,
////                paddingLeft + (contentWidth - mTextWidth) / 2,
////                paddingTop + (contentHeight + mTextHeight) / 2,
////                mTextPaint);
//
//        // Draw the example drawable on top of the text.
//        if (mExampleDrawable != null) {
//            mExampleDrawable.setBounds(paddingLeft, paddingTop,
//                    paddingLeft + contentWidth, paddingTop + contentHeight);
//            mExampleDrawable.draw(canvas);
//        }
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
//        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
//        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
//        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}