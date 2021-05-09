package com.example.taobaounion.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.taobaounion.R

class CircleView @JvmOverloads
constructor(context: Context, attributes: AttributeSet? = null, def: Int = 0) : View(context, attributes, def) {

    private val mColor: Int
    private val mDuration: Long
    private val mSpace: Int
    private val mStroke: Float
    private val mRadius: Float
    private var mRect: RectF = RectF()
    private var mCirclePaint: Paint
    private var mAngle: Float = 0f


    init {
        val ta = context.obtainStyledAttributes(attributes, R.styleable.CircleView)
        mColor = ta.getColor(R.styleable.CircleView_circleColor, Color.parseColor("#EEA9B8"))
        val duration = ta.getInt(R.styleable.CircleView_durationTime, 10)
        mDuration = (duration * 1000 * 60).toLong()
        mSpace = ta.getInt(R.styleable.CircleView_spaceTime, 1000)
        mStroke = ta.getDimension(R.styleable.CircleView_stroke, 4f)
        mRadius = ta.getDimension(R.styleable.CircleView_radius, 50f)
        ta.recycle()
        mCirclePaint = Paint()
        mCirclePaint.run {
            color = mColor
            strokeWidth = mStroke
            style = Paint.Style.STROKE
        }
    }

    fun changeAngle(hasTime: Long) {
        if (mAngle == 360f || hasTime <= 0) {
            return
        }
        mAngle = (mDuration - hasTime) * 1f / mDuration * 360f
        invalidate()
        postDelayed({
            changeAngle(hasTime - 1000)
        }, 1000)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = measuredHeight
        mRect.run {
            top = height / 2 - mRadius - mStroke
            bottom = height / 2 + mRadius + mStroke
            left = width / 2 - mRadius - mStroke
            right = width / 2 + mRadius + mStroke
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawArc(mRect, -90f, -(360 - mAngle), false, mCirclePaint)
    }
}