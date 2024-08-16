package com.example.nafis.agrosewa.Data

import java.util.UUID

data class communityPost(
    val postid:String= UUID.randomUUID().toString(),
    val postuserid:String?=null,
    val postusername:String?=null,
    val postuserimg:String?=null,
    val posttitle:String?=null,
    val postdesc:String?=null,
    val posttime:String?=null,
    val postdate:String?=null,
    val postimg:String?=null,
    val postlike: Int = 0,
    val postdislike: Int = 0
)
