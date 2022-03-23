package com.example.mycoronav.vo2

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "response")
data class Hospital(
    @Element(name = "body")
    val body: Body,
    @Element(name="header")
    val header: Header
)

