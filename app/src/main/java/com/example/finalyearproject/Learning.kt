package com.example.finalyearproject

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalyearproject.ui.theme.FinalYearProjectTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

class Learning : ComponentActivity() {
    private var tts: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cod : User = intent.getSerializableExtra("user") as User
        val style = cod.Style
        val font = cod.Fontsize
        val speech = cod.Speechrate
        val build = Retro().build
        val module = intent.getStringExtra("module")
        val info = intent.getStringExtra("content")
        Log.d("wow", info.toString())
        if (style == "Visual"){
            setContent {
                var content by remember { mutableStateOf("") }
                val extra = build.getmaterial(module, info, style)
                extra.enqueue(object: Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Log.d("run", "walk")
                        Log.d("one", response.body().toString())
                        content = response.body().toString().replace("\"", "")
                    }
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("huh", "wut")
                        Log.d("man", "fuck")
                        Log.d("one", t.toString())
                    }
                })
                FinalYearProjectTheme {
                    Column (modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Row(modifier = Modifier.background(color = Color.White).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            Button(onClick = {finish()}) { Text("Exit") }
                        }
                        Column(modifier = Modifier.background(color = Color.White).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = info.toString())
                            Row(modifier = Modifier.background(color = Color.White).fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                                Text(content)
                            }
                        }
                    }
                }
            }
        }
        else if (style == "Auditory"){
            setContent {
                var content by remember { mutableStateOf("") }
                val extra = build.getmaterial(module, info, style)
                extra.enqueue(object: Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Log.d("run", "walk")
                        Log.d("one", response.body().toString())
                        content = response.body().toString().replace("\"", "")
                    }
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("huh", "wut")
                    }
                })
                val text = content
                tts = TextToSpeech(applicationContext) {
                    if (it == TextToSpeech.SUCCESS) {
                        tts?.language = Locale.ENGLISH
                        tts?.setSpeechRate(speech)
                        tts?.speak(text, TextToSpeech.QUEUE_ADD, null)
                    }
                }
                FinalYearProjectTheme {
                    Surface(modifier = Modifier.fillMaxSize(), color = Color.White){
                        Row(modifier = Modifier.background(color = Color.White).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            Button(onClick = {
                                tts?.stop()
                                tts?.shutdown()
                                finish()}) { Text("Exit") }
                        }
                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                            Row(modifier = Modifier.background(color = Color.White).fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                                Button(onClick = {
                                    tts?.stop()
                                    tts?.speak(text, TextToSpeech.QUEUE_ADD, null)
                                }) { Text("Replay from beginning") }
                            }
                        }
                    }
                }
            }
        }
        else{
            setContent {
                var content by remember { mutableStateOf("") }
                val extra = build.getmaterial(module, info, style)
                extra.enqueue(object: Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Log.d("run", "walk")
                        Log.d("one", response.body().toString())
                        content = response.body().toString().replace("\"", "")
                    }
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("huh", "wut")
                        Log.d("well", t.toString())
                    }
                })
                FinalYearProjectTheme {
                    Column (modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Row(modifier = Modifier.background(color = Color.White).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            Button(onClick = {finish()}) { Text("Exit") }
                        }
                        Column(modifier = Modifier.background(color = Color.White).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = info.toString(), fontSize = (font+2).sp)
                            Text(text = content,
                                Modifier.align(Alignment.Start), fontSize = font.sp)
                        }
                    }
                }
            }
        }
    }
}