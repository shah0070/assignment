package com.shah.assignment.airtel.model

import com.google.gson.annotations.SerializedName

data class ModelData(

        @SerializedName("autoCompleteRequestString") val autoCompleteRequestString: String?,
        @SerializedName("focusWord") val focusWord: String?,
        @SerializedName("addressList") val addressList: List<AddressList>?
)
