package com.example.basiclogin.Models

class CuisineCategoryRecord (var id: Int, val code: String, val desc: String) {

    constructor() :  this(0,"","")

    override fun toString(): String {
        return "${code} : $desc"
    }
}