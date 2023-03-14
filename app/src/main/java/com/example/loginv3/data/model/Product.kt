package com.example.loginv3.data.model

data class Product(
    var name:String = "",
    var price:Float = 0F,
    var description:String = "",
    var menuPosition:Int = 0,
    var active:Boolean = true
)