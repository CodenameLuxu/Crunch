package com.example.basiclogin.Models

class EatHistoryRecord (var id: Int
                        , var userid: Int
                        , var dishid: Int
                        , var timeperiodid: Int
                        , var date : String) {

    constructor() :  this(0,0,0,0,"20200724")
}