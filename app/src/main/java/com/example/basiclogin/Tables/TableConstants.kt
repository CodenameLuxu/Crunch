package com.example.basiclogin.Tables

object TableConstants {


    @JvmStatic val TBL_USRADM: String = "USRADM"
    @JvmStatic val USRADM_USRID: String = "usrid"
    @JvmStatic val USRADM_USRUSRNM: String = "usrusrnm"
    @JvmStatic val USRADM_usrpass: String = "usrpass"
    @JvmStatic val USRADM_usrusrcred: String = "usrusrcre"

//    Time period record table
    @JvmStatic val TBL_TMEPRD: String = "TMEPRD"
    @JvmStatic val TMEPRD_ID: String = "TPDENTID"
    @JvmStatic val TMEPRD_CODE: String = "TPDCODE"
    @JvmStatic val TMEPRD_DESC: String = "TPDDESC"

//    cuisine Category
    @JvmStatic val TBL_CRUCAT: String = "CRUCAT"
    @JvmStatic val CRUCAT_ID: String = "CSCENID"
    @JvmStatic val CRUCAT_CODE: String = "CSCCODE"
    @JvmStatic val CRUCAT_DESC: String = "CSCDESC"

    //    Dish item
    @JvmStatic val TBL_DSHITM: String = "DSHITM"
    @JvmStatic val DSHITM_ID: String = "DHIENID"
    @JvmStatic val DSHITM_DHIITMCD: String = "DHIITMCD"
    @JvmStatic val DSHITM_DHICRUCAT: String = "DHICATID"
    @JvmStatic val DSHITM_DHIITMNM: String = "DHIITMNM"
    @JvmStatic val DSHITM_VEGANOPT: String = "DHIVEGOTP"
    @JvmStatic val DSHITM_NUTFREOPT: String = "DHINUTFREOPT"
    @JvmStatic val DSHITM_MILKFREOPT: String = "DHIMILKFREOPT"
    @JvmStatic val DSHITM_SPICYOPT: String = "DHISPICOPT"

    // Eat History
    @JvmStatic val TBL_EATHIST: String = "EATHIST"
    @JvmStatic val EATHIST_ID : String = "EHTENTID"
    @JvmStatic val EATHIST_USERID : String = "EHTUSRID"
    @JvmStatic val EATHIST_DISHID : String = "EHTDSHID"
    @JvmStatic val EATHIST_TMEID : String = "EHTTPDID"
    @JvmStatic val EATHIST_DATE : String = "EHTDATE"


}