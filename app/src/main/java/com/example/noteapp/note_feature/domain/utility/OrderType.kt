package com.example.noteapp.note_feature.domain.utility

sealed class OrderType{
    object Ascending : OrderType()
    object Descending : OrderType()
}
