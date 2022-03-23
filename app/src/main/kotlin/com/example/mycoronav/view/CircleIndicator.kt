package com.example.mycoronav.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout

class CircleIndicator : LinearLayout {
    private var mcontext: Context? = null
    private var circleSelected: Int = 0
    private var circleUnselected: Int = 0
    private var imageDot: MutableList<ImageView> = mutableListOf()

    //selected position
    private var unselectedPosition: Int = -1

    // 4.5dp -> to Pixel
    private val temp = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 4.5f, resources.displayMetrics
    )

    constructor(context: Context) : super(context) {
        this.mcontext = context
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        this.mcontext = context
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        this.mcontext = context
    }

    fun createDotPanel(count: Int, unselected: Int, selected: Int, position: Int) {
        this.removeAllViews()
        circleUnselected = unselected
        circleSelected = selected
        //give dots padding here
        for (i in 0 until count) {
            imageDot.add(ImageView(mcontext).apply {
                setPadding(temp.toInt(), 0, temp.toInt(), 0)
            })
            this.addView(imageDot[i])
        }
        //set Dots first
        for (i in imageDot.indices) {
            when (i) {
                position -> imageDot[i].setImageResource(circleSelected)
                else -> imageDot[i].setImageResource(circleUnselected)
            }
        }
    }

    fun selectDot(position: Int) {
        if(unselectedPosition == -1){
            imageDot[position].setImageResource(circleSelected)
            unselectedPosition = position
        }else{
            imageDot[unselectedPosition].setImageResource(circleUnselected)
            imageDot[position].setImageResource(circleSelected)
            unselectedPosition = position
        }
    }
}