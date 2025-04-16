package com.example.finalyearproject

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.finalyearproject.ui.theme.FinalYearProjectTheme
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class Settings : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val build = Retro().build
        val cod : User = intent.getSerializableExtra("user") as User
        setContent {
            var style by remember { mutableStateOf("") }
            var font by remember { mutableStateOf(0f) }
            var speech by remember { mutableStateOf(0f) }
            style = cod.Style
            font = cod.Fontsize
            speech = cod.Speechrate
            FinalYearProjectTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val off = MaterialTheme.colorScheme.primary
                    var normal1 by remember { mutableStateOf(off) }
                    var normal2 by remember { mutableStateOf(off) }
                    var normal3 by remember { mutableStateOf(off) }
                    val on = androidx.compose.ui.graphics.Color.Cyan
                    val visual = "Visual"
                    val auditory = "Auditory"
                    val literary = "Literary"
                    if (style == visual){
                        normal1 = on
                        normal2 = off
                        normal3 = off
                    }
                    else if (style == auditory){
                        normal1 = off
                        normal2 = on
                        normal3 = off
                    }
                    else{
                        normal1 = off
                        normal2 = off
                        normal3 = on
                    }
                    Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.background).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Top) {
                        Button(onClick = {
                            startActivity(Intent(this@Settings, Categories::class.java).putExtra("user", cod))
                            finish()}) { Text("Exit")}
                        Button(onClick = {
                            val update = build.settingsupdate(cod.Username, style,
                                font.toString(), speech.toString())

                            update.enqueue(object: Callback<ResponseBody> {
                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                    if (response.isSuccessful){
                                        Log.d("heh", response.message())
                                        val push = build.getuser(cod.Username)
                                        push.enqueue(object: Callback<User>{
                                            override fun onResponse(
                                                call: Call<User>,
                                                response: Response<User>
                                            ) {
                                                startActivity(Intent(this@Settings, Categories::class.java).putExtra("user", response.body()))
                                                finish()
                                            }

                                            override fun onFailure(call: Call<User>, t: Throwable) {
                                                TODO("Not yet implemented")
                                            }
                                        })
                                    }
                                    else{
                                        Log.d("notquite", "thereyet")
                                    }
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    Log.d("drawing", "board")
                                }
                            })
                        }) { Text("Save")}
                    }
                    Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                            Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.background).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Top) {
                                Text(text = "Set learning style")
                            }
                            Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.background).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                Button(onClick = {
                                    style = visual
                                }, colors = ButtonDefaults.buttonColors(normal1)) { Text(visual)}
                                Button(onClick = {
                                    style = auditory
                                }, colors = ButtonDefaults.buttonColors(normal2)) { Text(auditory)}
                                Button(onClick = {
                                    style = literary
                                }, colors = ButtonDefaults.buttonColors(normal3)) { Text(literary)}
                            }
                        }
                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "Set size of text")
                            Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.background).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Top) {
                                Slider(value = font,
                                    onValueChange = { font = it },
                                    colors = SliderDefaults.colors(),
                                    steps = 3,
                                    valueRange = 4f..20f){
                                }
                            }
                            Text(text = font.toString())
                        }
                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "Set rate of speech")
                            Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.background).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Top) {
                                Slider(value = speech,
                                    onValueChange = { speech = it },
                                    colors = SliderDefaults.colors(),
                                    steps = 6,
                                    valueRange = 0.25f..2f){
                                }
                            }
                            Text(text = speech.toString()+"x")
                        }
                    }
                }
            }
        }
    }
}