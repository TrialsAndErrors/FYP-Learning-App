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
import androidx.compose.ui.unit.dp
import com.example.finalyearproject.ui.theme.FinalYearProjectTheme
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

class AddContent : ComponentActivity() {
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
                                startActivity(Intent(this@AddContent, Categories::class.java).putExtra("user", intent.getSerializableExtra("user")))
                                finish()
                            }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("exit")}
                        }
                    }
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        var name by rememberSaveable {
                            mutableStateOf("")
                        }
                        var visual by rememberSaveable {
                            mutableStateOf("")
                        }
                        var audiliter by rememberSaveable {
                            mutableStateOf("")
                        }
                        Text(text = message)
                        OutlinedTextField(
                            value = name,
                            onValueChange = {name = it},
                            label = { Text("Name") }
                        )
                        OutlinedTextField(
                            value = visual,
                            onValueChange = {visual = it},
                            label = { Text("Visual") }
                        )
                        OutlinedTextField(
                            value = audiliter,
                            onValueChange = {audiliter = it},
                            label = { Text("Auditory/Literary") }
                        )
                        Button(onClick = {
                            if ((name.isNotEmpty()) && (visual.isNotEmpty()) && (audiliter.isNotEmpty())){
                                val insert = build.addcontent(name, visual, audiliter)

                                insert.enqueue(object: Callback<String> {
                                    override fun onResponse(call: Call<String>, response: Response<String>) {
                                        val check = response.body().toString().replace("\"", "")
                                        if (check == "true"){
                                            Log.d("heh", response.message())
                                            message = "Content Added Successfully"
                                            startActivity(Intent(this@AddContent, Categories::class.java).putExtra("user", intent.getSerializableExtra("user")))
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
                            else {
                                message = "Please enter all info"
                            }
                        }) { Text("Add") }
                    }
                }
            }
        }
    }
}