package com.example.mydocdemo.view.shared

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver.*


class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val fontPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var staticLayout: StaticLayout
    private val initialFontSize = 30f
    private var xPosition: Float = DEFAULT_VALUE
    private var yPosition: Float = DEFAULT_VALUE
    private var paintUpdated: Boolean = true
    private var lastMeasuredTextWidth: Float = 0f

    private var oldX: Float = Float.MIN_VALUE
    private var oldY: Float = Float.MIN_VALUE
    private var startX: Float = Float.MIN_VALUE
    private var startY: Float = Float.MIN_VALUE

    private val textWidth: Float
        get() = run {
            measureText()
            return lastMeasuredTextWidth
        }

    private val topTextPosition
        get() = yPosition

    private val bottomTextPosition
        get() = yPosition + staticLayout.height

    private val leftTextPosition
        get() = xPosition

    private val rightTextPosition
        get() = xPosition + textWidth

    private var shouldBeCentred = true
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    var text: String = ""
        set(value) {
            field = value
            if (width == 0) {
                viewTreeObserver.addOnPreDrawListener(object : OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        initializeStaticLayout()
                        viewTreeObserver.removeOnPreDrawListener(this)
                        return true
                    }
                })
            } else {
                initializeStaticLayout()
                invalidate()
            }
        }

    var currentFontSize: Float = initialFontSize
        set(value) {
            if (value <= 0) {
                return
            }

            field = value
            fontPaint.textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                value, resources.displayMetrics
            )
            paintUpdated = true

            initializeStaticLayout()
            invalidate()
        }

    var isBold: Boolean
        get() {
            return fontPaint.typeface.isBold
        }
        set(value) {
            if (value != fontPaint.typeface.isBold) {
                fontPaint.typeface = getCurrentTypeface(value, fontPaint.typeface.isItalic)
                paintUpdated = true

                initializeStaticLayout()
                invalidate()
            }
        }

    var isItalic: Boolean
        get() {
            return fontPaint.typeface.isItalic
        }
        set(value) {
            if (value != fontPaint.typeface.isItalic) {
                fontPaint.typeface = getCurrentTypeface(fontPaint.typeface.isBold, value)
                paintUpdated = true

                initializeStaticLayout()
                invalidate()
            }
        }

    init {
        fontPaint.textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            initialFontSize,
            resources.displayMetrics
        )
        fontPaint.color = Color.BLACK
        fontPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        fontPaint.style = Paint.Style.FILL_AND_STROKE

        setOnTouchListener { _, event ->
            val touchX = event.x
            val touchY = event.y

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    oldX = touchX
                    startX = touchX

                    oldY = touchY
                    startY = touchY
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = touchX - oldX
                    val dy = touchY - oldY

                    getNextX(dx)
                    getNextY(dy)

                    oldX = touchX
                    oldY = touchY

                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    if (startX == touchX && startY == touchY) {
                        shouldBeCentred = true
                        invalidate()
                    }
                }
            }

            return@setOnTouchListener true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!this::staticLayout.isInitialized) {
            return
        }

        if (shouldBeCentred) {
            xPosition = if (staticLayout.lineCount > 1) paddingStart.toFloat() else (width - textWidth) / 2f

            yPosition = (height - staticLayout.height) / 2f
            shouldBeCentred = false
        }

        canvas.save()
        canvas.translate(xPosition, yPosition)
        staticLayout.draw(canvas)
        canvas.restore()
    }

    private fun getNextX(dx: Float = 0f) {
        when {
            staticLayout.lineCount > 1 -> {
                xPosition = paddingStart.toFloat()
            }
            leftTextPosition - dx - paddingStart < 0f -> {
                xPosition = paddingStart.toFloat()
            }
            rightTextPosition - dx + paddingEnd > width -> {
                xPosition = width - textWidth - paddingEnd.toFloat()
            }
            else -> {
                xPosition -= dx
            }
        }
    }

    private fun getNextY(dy: Float = 0f) {
        when {
            topTextPosition - dy - paddingTop < 0f
                    || staticLayout.height + paddingTop + paddingBottom > height -> {
                yPosition = paddingTop.toFloat()
            }
            bottomTextPosition - dy + paddingBottom > height -> {
                yPosition = height - staticLayout.height.toFloat() - paddingBottom.toFloat()
            }
            else -> {
                yPosition -= dy
            }
        }
    }

    private fun getCurrentTypeface(bold: Boolean, italic: Boolean): Typeface {
        return when {
            bold && italic -> Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
            bold && !italic -> Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            !bold && italic -> Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
            else -> Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        }
    }

    private fun measureText() {
        if (paintUpdated) {
            paintUpdated = false
            lastMeasuredTextWidth = fontPaint.measureText(text)
        }
    }

    private fun initializeStaticLayout() {
        staticLayout = StaticLayout(
            text,
            fontPaint,
            width - paddingStart - paddingEnd,
            Layout.Alignment.ALIGN_NORMAL,
            1f,
            0f,
            false
        )

        getNextX()
        getNextY()
    }

    companion object {
        const val DEFAULT_VALUE: Float = -1f
    }
}