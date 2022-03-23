package com.example.mycoronav.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView

class CategoryScrollView : HorizontalScrollView{

    lateinit var layout: LinearLayout
    lateinit var selectTextView: TextView
    lateinit var category: String

    var onCategoryClick : ((String) -> Unit) ?= null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context){
        layout = LinearLayout(context)
        layout.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        layout.gravity = Gravity.BOTTOM
        addView(layout)
        this.isHorizontalScrollBarEnabled = false
    }
}