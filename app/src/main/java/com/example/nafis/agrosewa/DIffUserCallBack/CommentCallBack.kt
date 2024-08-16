package com.example.nafis.agrosewa.DIffUserCallBack

import com.example.nafis.agrosewa.Data.Comment

interface CommentCallBack {
    fun commentOnClick(comment: Comment,position:Int)
}