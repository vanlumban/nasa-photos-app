package com.vanlumban.nasaphotos.data.models

import com.google.gson.annotations.SerializedName

data class NasaPhoto(
    val date: String,
    val title: String,
    @SerializedName("url")
    val imageUrl: String,
    @SerializedName("copyright")
    val author: String?
)

