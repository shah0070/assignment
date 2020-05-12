package com.shah.assignment.airtel.model

import com.google.gson.annotations.SerializedName

data class AddressBaseModel(

        @SerializedName("requestId") val requestId: String?,
        @SerializedName("data") val data: ModelData?
)