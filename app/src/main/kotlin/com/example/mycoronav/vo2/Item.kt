package com.example.mycoronav.vo2

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class Item(
    @PropertyElement(name = "addr")
    var addr: String,
    @PropertyElement(name = "sidoCdNm")
    var sidoCdNm: String,
    @PropertyElement(name="yadmNm")
    var yadmNm: String,
)
