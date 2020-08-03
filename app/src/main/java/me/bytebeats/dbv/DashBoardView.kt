package me.bytebeats.dbv

import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/3 19:51
 */
class DashBoardView : View {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initAttrs(attrs)
    }

    var backColor: Int = DEFAULT_BACK_COLOR
    var rimStartColor: Int = DEFAULT_START_COLOR
    var rimMiddleColor: Int = DEFAULT_MIDDLE_COLOR
    var rimEndColor: Int = DEFAULT_END_COLOR
    var cursorColor: Int = DEFAULT_CURSOR_COLOR
    var cursorWidth: Float = DEFAULT_CURSOR_WIDTH
    var rimWidth: Float = DEFAULT_RIM_WIDTH
    var rimStartAngle: Int = DEFAULT_START_ANGLE
    var rimSweepAngle: Int = DEFAULT_SWEEP_ANGLE
    var cursorOffset: Float = DEFAULT_CURSOR_OFFSET
    var rimOffset: Float = DEFAULT_RIM_OFFSET
    var cursorSweepAngle: Int = DEFAULT_CURSOR_SWEEP_ANGLE
    var dbvTextColor: Int = DEFAULT_TEXT_COLOR
    var dbvTextSize: Float = DEFAULT_TEXT_SIZE

    var centerX: Float = 0F
    var centerY: Float = 0F
    var radius: Float = 0F

    private val backPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = backColor
            strokeWidth = 5F
            isDither = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
    }
    private val rimPaint by lazy {
        Paint().apply {
            isAntiAlias = true
//            color = rimStartColor
            isDither = false
            strokeWidth = rimWidth
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
//            radius = 5f
        }
    }
    private val cursorPaint by lazy {
        Paint().apply {
            isAntiAlias = true
//            color = cursorColor
            isDither = true
            strokeWidth = cursorWidth
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.SQUARE
            rotation = 135F
        }
    }
    private val rimSweepGradient by lazy {
        SweepGradient(
            centerX,
            centerY,
            intArrayOf(rimStartColor, rimMiddleColor, rimEndColor),
            floatArrayOf(0.0f, 0.375f, 0.75f)
        ).apply {
            rotation = rimStartAngle.toFloat()
        }
    }

    private val cursorSweepGradient by lazy {
        SweepGradient(
            centerX,
            centerY,
            cursorColor,
            cursorColor
        ).apply {
            rotation = rimStartAngle.toFloat()
        }
    }

    private fun initAttrs(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.DashBoardView, 0, 0).apply {
            try {
                backColor = getColor(R.styleable.DashBoardView_dbv_backColor, DEFAULT_BACK_COLOR)
                rimStartColor = getColor(R.styleable.DashBoardView_dbv_startColor, DEFAULT_START_COLOR)
                rimMiddleColor = getColor(R.styleable.DashBoardView_dbv_middleColor, DEFAULT_MIDDLE_COLOR)
                rimEndColor = getColor(R.styleable.DashBoardView_dbv_endColor, DEFAULT_END_COLOR)
                cursorColor = getColor(R.styleable.DashBoardView_dbv_cursorColor, DEFAULT_END_COLOR)
                cursorWidth = getDimension(R.styleable.DashBoardView_dbv_cursorWidth, DEFAULT_CURSOR_WIDTH)
                rimWidth = getDimension(R.styleable.DashBoardView_dbv_rimWidth, DEFAULT_RIM_WIDTH)
                rimStartAngle = getInt(R.styleable.DashBoardView_dbv_rimStartAngle, DEFAULT_START_ANGLE)
                rimSweepAngle = getInt(R.styleable.DashBoardView_dbv_rimSweepAngle, DEFAULT_SWEEP_ANGLE)
                cursorOffset = getDimension(R.styleable.DashBoardView_dbv_cursorOffset, DEFAULT_CURSOR_OFFSET)
                rimOffset = getDimension(R.styleable.DashBoardView_dbv_rimOffset, DEFAULT_RIM_OFFSET)
                cursorSweepAngle = getInt(R.styleable.DashBoardView_dbv_cursorSweepAngle, DEFAULT_CURSOR_SWEEP_ANGLE)
                dbvTextColor = getColor(R.styleable.DashBoardView_dbv_textColor, DEFAULT_TEXT_COLOR)
                dbvTextSize = getDimension(R.styleable.DashBoardView_dbv_textSize, DEFAULT_TEXT_SIZE)
            } finally {
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val requestedWidth = MeasureSpec.getSize(widthMeasureSpec)
        val requestedWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val desiredWith = DEFAULT_MIN_WIDTH
        val width = when (requestedWidthMode) {
            MeasureSpec.EXACTLY -> requestedWidth
            MeasureSpec.UNSPECIFIED -> desiredWith
            else -> requestedWidth.coerceAtMost(desiredWith)
        }

        val requestedHeight = MeasureSpec.getSize(heightMeasureSpec)
        val requestedHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        val desiredHeight = DEFAULT_MIN_HEIGHT
        val height = when (requestedHeightMode) {
            MeasureSpec.EXACTLY -> requestedHeight
            MeasureSpec.UNSPECIFIED -> desiredHeight
            else -> requestedHeight.coerceAtMost(desiredHeight)
        }
        setMeasuredDimension(width, height)
        compute()
    }

    private fun compute() {//计算加点坐标和半径
        centerX = (measuredWidth - paddingLeft - paddingBottom) / 2F + paddingLeft
        centerY = (measuredHeight - paddingTop - paddingBottom) / 2F + paddingTop
        radius = (measuredWidth - paddingLeft - paddingBottom).coerceAtMost(measuredHeight - paddingTop - paddingBottom) / 2F
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawCircle(centerX, centerY, radius, backPaint)
        rimPaint.shader = rimSweepGradient
        canvas?.drawArc(
            RectF(
                centerX - radius + rimDiff(),
                centerY - radius + rimDiff(),
                centerX + radius - rimDiff(),
                centerY + radius - rimDiff()
            ), 0.toFloat(), rimSweepAngle.toFloat(), false, rimPaint
        )
        cursorPaint.shader = cursorSweepGradient
        canvas?.drawArc(
            RectF(
                centerX - radius + cursorDiff(),
                centerY - radius + cursorDiff(),
                centerX + radius - cursorDiff(),
                centerY + radius - cursorDiff()
            ), 0.0f, cursorSweepAngle.toFloat(), false, cursorPaint
        )
    }

    private fun rimDiff(): Float = rimOffset + rimWidth

    private fun cursorDiff(): Float = cursorOffset + cursorWidth

    companion object {
        val DEFAULT_TEXT_COLOR = Color.parseColor("#A6A9B6")
        val DEFAULT_TEXT_SIZE = 28F
        val DEFAULT_START_COLOR = Color.parseColor("#34CF82")
        val DEFAULT_MIDDLE_COLOR = Color.parseColor("#F8CC00")
        val DEFAULT_END_COLOR = Color.parseColor("#FF3750")
        val DEFAULT_CURSOR_COLOR = Color.parseColor("#FF3750")
        const val DEFAULT_BACK_COLOR = Color.TRANSPARENT
        const val DEFAULT_CURSOR_WIDTH = 60F
        const val DEFAULT_CURSOR_OFFSET = 5F
        const val DEFAULT_RIM_WIDTH = 20F
        const val DEFAULT_RIM_OFFSET = 30F
        const val DEFAULT_START_ANGLE = 135
        const val DEFAULT_SWEEP_ANGLE = 270
        const val DEFAULT_MIN_WIDTH = 200
        const val DEFAULT_MIN_HEIGHT = 200
        const val DEFAULT_CURSOR_SWEEP_ANGLE = 5
    }
}