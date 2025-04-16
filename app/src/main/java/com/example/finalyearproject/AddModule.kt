package com.example.finalyearproject

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.finalyearproject.ui.theme.FinalYearProjectTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddModule : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val error = mutableStateOf("")
        var message by error
        val build = Retro().build
        setContent {
            FinalYearProjectTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Top) {
                            Button(onClick = {
                                startActivity(Intent(this@AddModule, Categories::class.java).putExtra("user", intent.getSerializableExtra("user")))
                                finish()
                            }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Exit") }
                        }
                    }
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        var name by rememberSaveable {
                            mutableStateOf("")
                        }
                        var acro by rememberSaveable {
                            mutableStateOf("")
                        }
                        var sect by rememberSaveable {
                            mutableStateOf("")
                        }
                        var stage by rememberSaveable {
                            mutableStateOf("")
                        }
                        Text(text = message)
                        OutlinedTextField(
                            value = name,
                            onValueChange = {name = it},
                            label = { Text("Name") }
                        )
                        OutlinedTextField(
                            value = acro,
                            onValueChange = {acro = it},
                            label = { Text("Acronym") }
                        )
                        OutlinedTextField(
                            value = sect,
                            onValueChange = {sect = it},
                            label = { Text("Category") }
                        )
                        OutlinedTextField(
                            value = stage,
                            onValueChange = {stage = it},
                            label = { Text("Level") }
                        )
                        Button(onClick = {
                            if ((name.length in 1..100) && (acro.length in 1..20) && (sect.isNotEmpty()) && (stage.isNotEmpty())){
                                val insert = build.addmodule(name, acro.lowercase(), sect, stage)

                                insert.enqueue(object: Callback<String> {
                                    override fun onResponse(call: Call<String>, response: Response<String>) {
                                        val check = response.body().toString().replace("\"", "")
                                        if (check == "true"){
                                            Log.d("heh", response.message())
                                            message = "Content Added Successfully"
                                            startActivity(Intent(this@AddModule, Categories::class.java).putExtra("user", intent.getSerializableExtra("user")))
                                            finish()
                                        }
                                        else{
                                            message = "Content already exists"
                                        }
                                    }

                                    override fun onFailure(call: Call<String>, t: Throwable) {
                                        Log.d("drawing", "boredom")
                                    }
                                })
                            }
                            else if (name.length > 100 || acro.length > 20){
                                message = "Subject name/acronym is too long"
                            }
                            else if (name.isEmpty() || acro.isEmpty() || sect.isEmpty() || stage.isEmpty()) {
                                message = "Please enter all info"
                            }
                        }) { Text("Add") }
                    }
                }
            }
        }
    }
}