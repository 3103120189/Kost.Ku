package com.example.kostku.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Kost (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val desc: String
)
