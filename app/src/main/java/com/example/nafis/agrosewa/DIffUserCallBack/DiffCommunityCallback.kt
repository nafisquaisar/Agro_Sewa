package com.example.nafis.agrosewa.DIffUserCallBack

import androidx.recyclerview.widget.DiffUtil
import com.example.nafis.agrosewa.Data.communityPost

object DiffCommunityCallback: DiffUtil.ItemCallback<communityPost>() {
    override fun areItemsTheSame(oldItem: communityPost, newItem: communityPost): Boolean {
       return oldItem.postdate==newItem.postdate
    }

    override fun areContentsTheSame(oldItem: communityPost, newItem: communityPost): Boolean {
        return  oldItem.posttitle==newItem.posttitle &&
                oldItem.postdesc==newItem.postdesc &&
                oldItem.postdate==newItem.postdate &&
                oldItem.posttime==newItem.posttime&&
                oldItem.postimg==newItem.postimg &&
                oldItem.postlike==newItem.postlike &&
                oldItem.postdislike==newItem.postdislike &&
                oldItem.postusername==newItem.postusername &&
                oldItem.postuserid==newItem.postuserid &&
                oldItem.postuserimg==newItem.postuserimg
    }
}