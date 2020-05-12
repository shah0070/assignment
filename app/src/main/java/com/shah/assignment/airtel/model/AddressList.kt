package com.shah.assignment.airtel.model

import com.google.gson.annotations.SerializedName

data class AddressList(

        @SerializedName("id") val id: String?,
        @SerializedName("city") val city: String?,
        @SerializedName("addressString") val addressString: String?,
        @SerializedName("latitude") val latitude: Double?,
        @SerializedName("longitude") val longitude: Double?,
        @SerializedName("label") val label: String?
)