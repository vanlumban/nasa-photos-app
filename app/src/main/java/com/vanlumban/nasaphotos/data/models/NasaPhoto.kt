package com.vanlumban.nasaphotos.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaPhoto(
    val date: String? = "",
    val title: String? = "",
    val explanation: String? = "",
    val url: String? = "",
    val copyright: String? = ""
)


