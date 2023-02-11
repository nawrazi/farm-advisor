package com.enterprise.agino.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes

interface OnScrollMapViewWrapperTouchListener {
    fun onTouch()
}

class ScrollMapViewWrapper(
    context: Context, attrs: AttributeSet?,
    @AttrRes defStyleAttr: Int, @StyleRes defStyleRes: Int
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    var mListener: OnScrollMapViewWrapperTouchListener? = null

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
        context: Context, attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) : this(context, attrs, defStyleAttr, 0)


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> mListener?.onTouch()
            MotionEvent.ACTION_UP -> mListener?.onTouch()
        }
        return super.dispatchTouchEvent(event)
    }
}