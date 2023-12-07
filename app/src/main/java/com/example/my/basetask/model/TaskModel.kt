package com.example.my.basetask.model

import java.io.Serializable

data class TaskModel(
    var id: Int,
    var title: String,
    var state: Boolean = false
) : Serializable
