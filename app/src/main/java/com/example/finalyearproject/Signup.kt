package com.example.finalyearproject

import android.content.Intent
import android.os.Build
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.finalyearproject.ui.theme.FinalYearProjectTheme
import android.util.Log
import androidx.compose.foundation.background
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class Signup : ComponentActivity() {
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
                        Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Button(onClick = {
                                finish()
                            }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Exit") }
                        }
                    }
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        var user by rememberSaveable {
                            mutableStateOf("")
                        }
                        var pass by rememberSaveable {
                            mutableStateOf("")
                        }
                        Text(text = message)
                        OutlinedTextField(
                            value = user,
                            onValueChange = {user = it},
                            label = { Text("Username") }
                        )
                        OutlinedTextField(
                            value = pass,
                            onValueChange = {pass = it},
                            label = { Text("Password") }
                        )
                        Button(onClick = {
                            if ((user.length in 1..20) && (pass.length in 1..20)){
                                val current = build.signup(user, pass, "Visual", "12.0", "1.0", "Student")
                                current.enqueue(object: Callback<String>{
                                    override fun onResponse(call: Call<String>, response: Response<String>) {
                                        val check = response.body().toString().replace("\"", "")
                                        if (check == "true"){
                                            message = "Account Created Successfully"
                                            finish()
                                        }
                                        else{
                                            message = "Username already exists"
                                        }
                                    }
                                    override fun onFailure(call: Call<String>, t: Throwable) {
                                        TODO("Not yet implemented")
                                    }
                                })
                            }
                            else if (user.length > 20 || pass.length > 20){
                                message = "Username/password length should be less than 20 characters"
                            }
                            else if (user.isEmpty() || pass.isEmpty()) {
                                message = "Please enter username/password"
                            }
                        }) { Text("Create") }
                    }
                }
            }
        }
    }
}
