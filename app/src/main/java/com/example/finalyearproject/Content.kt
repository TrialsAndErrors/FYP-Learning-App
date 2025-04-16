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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Content : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cod : User = intent.getSerializableExtra("user") as User
        val role = cod.Role
        val vari = intent.getStringExtra("module")
        val build = Retro().build
        if (role == "Student"){
            setContent {
                var amount by remember { mutableStateOf(0) }
                var score by remember { mutableStateOf("") }
                var info = mutableListOf<Information>()
                val extra = build.getcontent(vari.toString())
                extra.enqueue(object: Callback<List<Information>> {
                    override fun onResponse(call: Call<List<Information>>, response: Response<List<Information>>) {
                        Log.d("run", "walk")
                        amount = response.body()!!.size
                        info = response.body() as MutableList<Information>
                        Log.d("oranges", info.size.toString())
                    }
                    override fun onFailure(call: Call<List<Information>>, t: Throwable) {
                        Log.d("huh", "wut")
                        Log.d("huh", vari.toString())
                    }
                })
                FinalYearProjectTheme {
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        Column (modifier = Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(100.dp)) {
                            Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                Button(onClick = {
                                    startActivity(Intent(this@Content, Categories::class.java).putExtra("user", cod))
                                    finish()
                                }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Back") }
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(vari.toString())
                                }
                            }
                            var i = 0
                            while (i < amount){
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                        val sword = info[i].Name
                                        Button(onClick = {
                                            startActivity(Intent(this@Content, Learning::class.java).putExtra("user", cod).putExtra("module", vari).putExtra("content", sword))
                                        }) { Text(info[i].Name) }
                                    }
                                }
                                i++
                                if (i == amount){
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                            val extra = build.scoreload(cod.Username, vari)
                                            extra.enqueue(object: Callback<String> {
                                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                                    Log.d("run", "walk")
                                                    score = response.body().toString().replace("\"", "")
                                                    Log.d("humid", response.body().toString())
                                                }
                                                override fun onFailure(call: Call<String>, t: Throwable) {
                                                    Log.d("huh", "wut")
                                                    Log.d("huh", vari.toString())
                                                }
                                            })
                                            if (score != "null"){
                                                Text("Recent Exercise Score: $score/5")
                                            }
                                            Button(onClick = {
                                                startActivity(Intent(this@Content, Exercise::class.java).putExtra("user", cod).putExtra("module", vari))
                                            }) { Text("Exercise") }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else{
            setContent {
                var amount by remember { mutableStateOf(0) }
                var score by remember { mutableStateOf("") }
                var info = mutableListOf<Information>()
                val extra = build.getcontent(vari.toString())
                extra.enqueue(object: Callback<List<Information>> {
                    override fun onResponse(call: Call<List<Information>>, response: Response<List<Information>>) {
                        Log.d("run", "walk")
                        amount = response.body()!!.size
                        info = response.body() as MutableList<Information>
                        Log.d("oranges", info.size.toString())
                    }
                    override fun onFailure(call: Call<List<Information>>, t: Throwable) {
                        Log.d("huh", "wut")
                        Log.d("huh", vari.toString())
                    }
                })
                FinalYearProjectTheme {
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        Column (modifier = Modifier.background(color = MaterialTheme.colorScheme.background).verticalScroll(
                            rememberScrollState()
                        ), verticalArrangement = Arrangement.spacedBy(100.dp)) {
                            Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                Button(onClick = {
                                    startActivity(Intent(this@Content, Categories::class.java).putExtra("user", cod))
                                    finish()
                                }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Back") }
                                Button(onClick = {
                                    startActivity(Intent(this@Content, AddContent::class.java).putExtra("user", cod))
                                    finish()
                                }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Add") }
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(vari.toString())
                                }
                            }
                            var i = 0
                            while (i < amount){
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                        val sword = info[i].Name
                                        Button(onClick = {
                                            startActivity(Intent(this@Content, Learning::class.java).putExtra("user", cod).putExtra("module", vari).putExtra("content", sword))
                                        }) { Text(info[i].Name) }
                                    }
                                }
                                i++
                                if (i == amount){
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                            val extra = build.scoreload(cod.Username, vari)
                                            extra.enqueue(object: Callback<String> {
                                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                                    Log.d("run", "walk")
                                                    score = response.body().toString().replace("\"", "")
                                                    Log.d("humid", response.body().toString())
                                                }
                                                override fun onFailure(call: Call<String>, t: Throwable) {
                                                    Log.d("huh", "wut")
                                                    Log.d("huh", vari.toString())
                                                }
                                            })
                                            if (score != "null"){
                                                Text("Recent Exercise Score: $score/5")
                                            }
                                            Button(onClick = {
                                                startActivity(Intent(this@Content, Exercise::class.java).putExtra("user", cod).putExtra("module", vari))
                                            }) { Text("Exercise") }
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