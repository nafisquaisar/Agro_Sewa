package com.example.nafis.agrosewa.DIffUserCallBack

import com.example.nafis.agrosewa.Data.communityPost

interface CommunityCallBack {
   fun onPostClcik(communityPost: communityPost, position :Int)
}