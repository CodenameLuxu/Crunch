package com.example.basiclogin.Models

class DishItemRecord (var id: Int, val code: String, val name: String, val crucat: Int
                      , val veganOpt : Boolean, val nutFreeOpt : Boolean, val milkFreeOpt : Boolean, val spicyOpt : Boolean) {
    constructor() :  this(0,"","",0,false,false,false,false)

}