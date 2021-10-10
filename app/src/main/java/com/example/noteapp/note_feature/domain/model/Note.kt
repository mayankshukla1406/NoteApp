package com.example.noteapp.note_feature.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.ui.theme.*
import java.lang.Exception


@Entity
data class Note(

    val title : String,
    val content : String,
    val timestamp : Long,
    val color : Int,
    @PrimaryKey val id : Int? = null
){
    companion object{
        val noteColorsList = listOf(RedOrange, LightGreen,
            BabyBlue, RedPink, Violet, LightBlue)
    }
}
class InvalidAddNoteException(message:String):Exception(message)
