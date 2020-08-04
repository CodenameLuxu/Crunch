package com.example.basiclogin.Models

class DishItemRecord (var id: Int, var code: String, var name: String, var crucat: Int
                      , var veganOpt : Boolean, var nutFreeOpt : Boolean, var milkFreeOpt : Boolean, var spicyOpt : Boolean) {
    constructor() :  this(0,"","",0,false,false,false,false)

}