package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.note_feature.presentation.add_edit_note.AddEditNoteScreen
import com.example.noteapp.note_feature.presentation.notes.NotesScreen
import com.example.noteapp.note_feature.presentation.utility.Screens
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                Surface(
                    color = MaterialTheme.colors.primary
                ){
                    val navController = rememberNavController()
                    NavHost(navController = navController,
                        startDestination = Screens.NotesScreen.route)
                    {
                        composable(route = Screens.NotesScreen.route){
                            NotesScreen(navController=navController)
                        }
                        composable(route = Screens.AddEditNoteScreen.route
                        +"?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ){
                               type = NavType.IntType
                               defaultValue = -1
                            },
                            navArgument(
                                name = "noteColor"
                            ){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )){
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                noteColor = color,
                                navController = navController)
                        }
                    }
                }

            }
        }
    }
}