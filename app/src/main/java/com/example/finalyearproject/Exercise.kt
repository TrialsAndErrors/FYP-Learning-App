package com.example.finalyearproject

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.unit.dp
import com.example.finalyearproject.ui.theme.FinalYearProjectTheme
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.random.Random

class Exercise : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val build = Retro().build
        val cod : User = intent.getSerializableExtra("user") as User
        val vari = intent.getStringExtra("module")
        setContent {
            var amount by remember { mutableStateOf(0) }
            var score by remember { mutableStateOf(0) }
            var questions = mutableListOf<Questions>()
            val extra = build.getquestions(vari)
            extra.enqueue(object: Callback<List<Questions>> {
                override fun onResponse(call: Call<List<Questions>>, response: Response<List<Questions>>) {
                    Log.d("run", "walk")
                    Log.d("hah", response.body()!!.size.toString())
                    val rand = Random
                    var i = 0
                    while (i < 5){
                        val adder = response.body()!![rand.nextInt(20)]
                        if (questions.contains(adder)){
                            continue
                        }
                        questions.add(adder)
                        i++
                    }
                    amount = questions.size
                    Log.d("fine", amount.toString())
                }
                override fun onFailure(call: Call<List<Questions>>, t: Throwable) {
                    Log.d("huh", "wut")
                    Log.d("well", t.toString())
                }
            })
            FinalYearProjectTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column (modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).background(color = MaterialTheme.colorScheme.background), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(50.dp)) {
                        Text("Practice Questions")
                        Text(score.toString())
                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(20.dp)) {
                            var i = 0
                            while (i < amount){
                                Text(questions[i].Question)
                                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                                    val off = MaterialTheme.colorScheme.primary
                                    val on = androidx.compose.ui.graphics.Color.Cyan
                                    var q1 by remember { mutableStateOf("") }
                                    var normal1a by remember { mutableStateOf(off) }
                                    var normal1b by remember { mutableStateOf(off) }
                                    var state by remember { mutableStateOf(true) }
                                    val answer = questions[i].Answer
                                    Button(onClick = {
                                        q1 = "True"
                                        if (normal1a == off){
                                            if (q1 == answer){
                                                score++
                                                state = false
                                            }
                                            else if (score > 0 && !state){
                                                score--
                                                state = true
                                            }
                                        }
                                        normal1a = on
                                        normal1b = off

                                    }, colors = ButtonDefaults.buttonColors(normal1a)) { Text("True") }
                                    Button(onClick = {
                                        q1 = "False"
                                        if (normal1b == off){
                                            if (q1 == answer){
                                                score++
                                                state = false
                                            }
                                            else if (score > 0 && !state){
                                                score--
                                                state = true
                                            }
                                        }
                                        normal1a = off
                                        normal1b = on
                                    }, colors = ButtonDefaults.buttonColors(normal1b)) { Text("False") }
                                }
                                i++
                                if (i == amount){
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                            Button(onClick = {
                                                val extra = build.scoreupdate(cod.Username, vari, cod.Style, score.toString())
                                                extra.enqueue(object: Callback<ResponseBody> {
                                                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                                        Log.d("run", "walk")
                                                    }
                                                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                                        Log.d("huh", "wut")
                                                        Log.d("well", t.toString())
                                                    }
                                                })
                                                startActivity(Intent(this@Exercise, Content::class.java).putExtra("user", cod).putExtra("module", vari))
                                                finish()
                                            }) { Text("Submit") }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}