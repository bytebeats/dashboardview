package me.bytebeats.dbv

import android.animation.ValueAnimator
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
    var outerRimWidth: Float = DEFAULT_OUTER_STROKE_WIDTH
        set(value) {
            field = value
            backPaint.strokeWidth = value
            invalidate()
        }
    var rimStartColor: Int = DEFAULT_START_COLOR
    var rimMiddleColor: Int = DEFAULT_MIDDLE_COLOR
    var rimEndColor: Int = DEFAULT_END_COLOR
    var cursorColor: Int = DEFAULT_CURSOR_COLOR
    var cursorWidth: Float = DEFAULT_CURSOR_WIDTH
        set(value) {
            field = value
            cursorPaint.strokeWidth = value
            invalidate()
        }
    var rimWidth: Float = DEFAULT_RIM_WIDTH
        set(value) {
            field = value
            rimPaint.strokeWidth = value
            invalidate()
        }
    var rimStartAngle: Int = DEFAULT_START_ANGLE
    var rimSweepAngle: Int = DEFAULT_SWEEP_ANGLE
    var evalutedRimSweepAngle: Float = 0.0F
    var cursorOffset: Float = DEFAULT_CURSOR_OFFSET
    var rimOffset: Float = DEFAULT_RIM_OFFSET
    var cursorSweepAngle: Int = DEFAULT_CURSOR_SWEEP_ANGLE
    var evaluatedCursorSweepAngle: Float = 0.0F
    var dbvDesc: String = ""
        set(value) {
            field = value
            invalidate()
        }
    var dbvDescColor: Int = DEFAULT_DESC_COLOR
        set(value) {
            field = value
            descPaint.color = value
            invalidate()
        }
    var dbvDescSize: Float = DEFAULT_DESC_SIZE
        set(value) {
            field = value
            descPaint.textSize = value
            invalidate()
        }
    var dbvTitle: String = ""
        set(value) {
            field = value
            invalidate()
        }
    var dbvTitleColor: Int = DEFAULT_TITLE_COLOR
        set(value) {
            field = value
            titlePaint.color = value
            invalidate()
        }
    var dbvTitleSize: Float = DEFAULT_TITLE_SIZE
        set(value) {
            field = value
            titlePaint.textSize = value
            invalidate()
        }
    var dbvTextPadding: Float = 0.0F
        set(value) {
            field = value
            invalidate()
        }
    var progress: Int = 0
        set(value) {
            field = if (value <= 0) {
                0
            } else if (value >= DEFAULT_MAX_PROGRESS) {
                DEFAULT_MAX_PROGRESS
            } else {
                (value % DEFAULT_MAX_PROGRESS + DEFAULT_MAX_PROGRESS) % DEFAULT_MAX_PROGRESS
            }
            invalidate()
        }

    private var centerX: Float = 0F
    private var centerY: Float = 0F
    private var radius: Float = 0F

    private val backPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = backColor
            strokeWidth = outerRimWidth
            isDither = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
    }
    private val rimPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            isDither = false
            strokeWidth = rimWidth
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.MITER
            strokeCap = Paint.Cap.ROUND
        }
    }
    private val cursorPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            isDither = true
            strokeWidth = cursorWidth
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.MITER
            strokeCap = Paint.Cap.BUTT
        }
    }
    private val descPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            isDither = true
            style = Paint.Style.FILL
            textSize = dbvDescSize
            color = dbvDescColor
        }
    }
    private val titlePaint by lazy {
        Paint().apply {
            isAntiAlias = true
            isDither = true
            style = Paint.Style.FILL
            textSize = dbvTitleSize
            color = dbvTitleColor
        }
    }
    private val rimSweepGradient by lazy {
        SweepGradient(
            centerX,
            centerY,
            intArrayOf(rimStartColor, rimMiddleColor, rimEndColor, rimStartColor),
            floatArrayOf(0.0f, 0.375f, 0.75f, 1.0f)
        )
    }

    private val cursorSweepGradient by lazy {
        SweepGradient(
            centerX,
            centerY,
            cursorColor,
            cursorColor
        )
    }
    var animDuration: Int = DEFAULT_ANIMATION_DURATION
        set(value) {
            field = value
            rimValAnimator?.duration = value.toLong()
            cursorValAnimator?.duration = value.toLong()
        }
    var rimValAnimator: ValueAnimator? = null
    var cursorValAnimator: ValueAnimator? = null
    val rimAnimUpdateListener by lazy {
        ValueAnimator.AnimatorUpdateListener {
            computeRimSweepValue(it.animatedValue as Float)
            invalidate()
        }
    }
    val cursorAnimUpdateListener by lazy {
        ValueAnimator.AnimatorUpdateListener {
            computeCursorSweepValue(it.animatedValue as Float)
            invalidate()
        }
    }

    private fun initAttrs(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.DashBoardView, 0, 0).apply {
            try {
                backColor = getColor(
                    R.styleable.DashBoardView_dbv_backColor,
                    DEFAULT_BACK_COLOR
                )
                outerRimWidth = getDimension(
                    R.styleable.DashBoardView_dbv_outerRimWidth,
                    DEFAULT_OUTER_STROKE_WIDTH
                )
                rimStartColor = getColor(
                    R.styleable.DashBoardView_dbv_startColor,
                    DEFAULT_START_COLOR
                )
                rimMiddleColor = getColor(
                    R.styleable.DashBoardView_dbv_middleColor,
                    DEFAULT_MIDDLE_COLOR
                )
                rimEndColor = getColor(
                    R.styleable.DashBoardView_dbv_endColor,
                    DEFAULT_END_COLOR
                )
                cursorColor = getColor(
                    R.styleable.DashBoardView_dbv_cursorColor,
                    DEFAULT_CURSOR_COLOR
                )
                cursorWidth = getDimension(
                    R.styleable.DashBoardView_dbv_cursorWidth,
                    DEFAULT_CURSOR_WIDTH
                )
                rimWidth = getDimension(
                    R.styleable.DashBoardView_dbv_rimWidth,
                    DEFAULT_RIM_WIDTH
                )
                rimStartAngle = getInt(
                    R.styleable.DashBoardView_dbv_rimStartAngle,
                    DEFAULT_START_ANGLE
                )
                rimSweepAngle = getInt(
                    R.styleable.DashBoardView_dbv_rimSweepAngle,
                    DEFAULT_SWEEP_ANGLE
                )
                cursorOffset = getDimension(
                    R.styleable.DashBoardView_dbv_cursorOffset,
                    DEFAULT_CURSOR_OFFSET
                )
                rimOffset = getDimension(
                    R.styleable.DashBoardView_dbv_rimOffset,
                    DEFAULT_RIM_OFFSET
                )
                cursorSweepAngle = getInt(
                    R.styleable.DashBoardView_dbv_cursorSweepAngle,
                    DEFAULT_CURSOR_SWEEP_ANGLE
                )
                dbvDescColor = getColor(
                    R.styleable.DashBoardView_dbv_descColor,
                    DEFAULT_DESC_COLOR
                )
                dbvDescSize = getDimension(
                    R.styleable.DashBoardView_dbv_descSize,
                    DEFAULT_DESC_SIZE
                )
                dbvDesc = getString(R.styleable.DashBoardView_dbv_desc) ?: ""
                dbvTitleColor = getColor(R.styleable.DashBoardView_dbv_titleColor, DEFAULT_TITLE_COLOR)
                dbvTitleSize = getDimension(
                    R.styleable.DashBoardView_dbv_titleSize,
                    DEFAULT_TITLE_SIZE
                )
                dbvTitle = getString(R.styleable.DashBoardView_dbv_title) ?: ""
                progress = getInt(R.styleable.DashBoardView_dbv_progress, 0)
                dbvTextPadding = getDimension(R.styleable.DashBoardView_dbv_textPadding, 0.0F)
                animDuration = getInt(R.styleable.DashBoardView_dbv_animDuration, DEFAULT_ANIMATION_DURATION)
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
        computeSrcPoint()
    }

    private fun computeSrcPoint() {//计算加点坐标和半径
        centerX = (measuredWidth - paddingLeft - paddingBottom) / 2F + paddingLeft
        centerY = (measuredHeight - paddingTop - paddingBottom) / 2F + paddingTop
        radius =
            (measuredWidth - paddingLeft - paddingBottom).coerceAtMost(measuredHeight - paddingTop - paddingBottom) / 2F - outerRimWidth
    }

    private fun computeRimSweepValue(sweepAngle: Float) {
        evalutedRimSweepAngle = sweepAngle
    }

    private fun computeCursorSweepValue(sweepAngle: Float) {
        evaluatedCursorSweepAngle = sweepAngle
    }

    fun startAnims() {
        startRimAnim()
        startCursorAnim()
    }

    fun startRimAnim() {
        if (rimValAnimator == null) {
            rimValAnimator = ValueAnimator.ofObject(SweepEvaluator(), 98F, rimSweepAngle)
            rimValAnimator!!.duration = animDuration.toLong()
            rimValAnimator!!.addUpdateListener(rimAnimUpdateListener)
        }
        rimValAnimator?.start()
    }

    fun startCursorAnim() {
        if (cursorValAnimator == null) {
            cursorValAnimator = ValueAnimator.ofObject(SweepEvaluator(), 165F, getCursorStartAngle())
            cursorValAnimator!!.duration = animDuration.toLong()
            cursorValAnimator!!.addUpdateListener(cursorAnimUpdateListener)
        }
        cursorValAnimator?.start()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawCircle(centerX, centerY, radius, backPaint)
        rimPaint.shader = rimSweepGradient
        if (rimValAnimator == null) {
            canvas?.drawArc(
                RectF(
                    centerX - radius + rimDiff(),
                    centerY - radius + rimDiff(),
                    centerX + radius - rimDiff(),
                    centerY + radius - rimDiff()
                ), rimStartAngle.toFloat(), rimSweepAngle.toFloat(), false, rimPaint
            )

        } else {
            canvas?.drawArc(
                RectF(
                    centerX - radius + rimDiff(),
                    centerY - radius + rimDiff(),
                    centerX + radius - rimDiff(),
                    centerY + radius - rimDiff()
                ), rimStartAngle.toFloat(), evalutedRimSweepAngle, false, rimPaint
            )

        }
        cursorPaint.shader = cursorSweepGradient
        if (cursorValAnimator == null) {
            canvas?.drawArc(
                RectF(
                    centerX - radius + cursorDiff(),
                    centerY - radius + cursorDiff(),
                    centerX + radius - cursorDiff(),
                    centerY + radius - cursorDiff()
                ), getCursorStartAngle(), cursorSweepAngle.toFloat(), false, cursorPaint
            )
        } else {
            canvas?.drawArc(
                RectF(
                    centerX - radius + cursorDiff(), centerY - radius + cursorDiff(),
                    centerX + radius - cursorDiff(),
                    centerY + radius - cursorDiff()
                ), evaluatedCursorSweepAngle, cursorSweepAngle.toFloat(), false, cursorPaint
            )
        }
        val descSizes = measureText(dbvDesc, descPaint)
        canvas?.drawText(dbvDesc, centerX - descSizes[0] / 2, centerY - descSizes[1] - dbvTextPadding / 2, descPaint)
        val titleSizes = measureText(dbvTitle, titlePaint)
        canvas?.drawText(
            dbvTitle, centerX - titleSizes[0] / 2,
            centerY + titleSizes[1] + dbvTextPadding / 2, titlePaint
        )
    }

    private fun measureText(text: String, paint: Paint): FloatArray {
        val txtBounds = Rect()
        descPaint.getTextBounds(text, 0, text.length, txtBounds)
        return floatArrayOf(paint.measureText(text), txtBounds.height().toFloat())
    }

    private fun getCursorStartAngle(): Float {
        return 135F + 270F * progress / DEFAULT_MAX_PROGRESS
    }

    private fun rimDiff(): Float = rimOffset + rimWidth

    private fun cursorDiff(): Float = cursorOffset + cursorWidth

    companion object {
        val DEFAULT_DESC_COLOR = Color.parseColor("#A6A9B6")
        const val DEFAULT_DESC_SIZE = 28F
        val DEFAULT_TITLE_COLOR = Color.parseColor("#07B360")
        const val DEFAULT_TITLE_SIZE = 35F
        val DEFAULT_START_COLOR = Color.parseColor("#FF3750")
        val DEFAULT_MIDDLE_COLOR = Color.parseColor("#34CF82")
        val DEFAULT_END_COLOR = Color.parseColor("#F8CC00")
        val DEFAULT_CURSOR_COLOR = Color.parseColor("#F8CC00")
        const val DEFAULT_BACK_COLOR = Color.TRANSPARENT
        const val DEFAULT_CURSOR_WIDTH = 80F
        const val DEFAULT_CURSOR_OFFSET = 0F
        const val DEFAULT_RIM_WIDTH = 20F
        const val DEFAULT_RIM_OFFSET = 60F
        const val DEFAULT_START_ANGLE = 135
        const val DEFAULT_SWEEP_ANGLE = 270
        const val DEFAULT_MIN_WIDTH = 160
        const val DEFAULT_MIN_HEIGHT = 160
        const val DEFAULT_CURSOR_SWEEP_ANGLE = 3
        const val DEFAULT_MAX_PROGRESS = 100
        const val DEFAULT_OUTER_STROKE_WIDTH = 5F
        const val DEFAULT_ANIMATION_DURATION = 3 * 1000
    }
}