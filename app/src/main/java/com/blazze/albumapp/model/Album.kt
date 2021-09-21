package com.blazze.albumapp.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Album(
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String?,
    @SerializedName("userId")
    @ColumnInfo(name = "user_id")
    var userId: Int?
)

