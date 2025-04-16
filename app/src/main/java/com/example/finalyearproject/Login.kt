package com.example.finalyearproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finalyearproject.ui.theme.FinalYearProjectTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val error = mutableStateOf("")
        var message by error
        val build = Retro().build
        setContent {
            FinalYearProjectTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
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
                        Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(50.dp)) {
                            Button(onClick = {
                                user = "Star"
                                pass = "Light"
//                                user = "My"
//                                pass = "Word"
                                if (user.isEmpty() || pass.isEmpty()) {
                                    message = "Please enter username/password"
                                }
                                else{
                                    val extra = build.login(user, pass)
                                    extra.enqueue(object: Callback<String>{
                                        override fun onResponse(call: Call<String>, response: Response<String>) {
                                            val check = response.body().toString().replace("\"", "")
                                            if (check == "true"){
                                                val push = build.getuser(user)
                                                push.enqueue(object: Callback<User>{
                                                    override fun onResponse(
                                                        call: Call<User>,
                                                        response: Response<User>
                                                    ) {
                                                        startActivity(Intent(this@Login, Categories::class.java).putExtra("user", response.body()))
                                                    }

                                                    override fun onFailure(call: Call<User>, t: Throwable) {
                                                        TODO("Not yet implemented")
                                                    }

                                                })
                                            }
                                            else{
                                                message = "Incorrect Username/Password"
                                            }
                                        }

                                        override fun onFailure(call: Call<String>, t: Throwable) {
                                            TODO("Not yet implemented")
                                        }

                                    })
                                }
                            }) { Text("Login")}
                            Button(onClick = {
                                startActivity(Intent(this@Login, Signup::class.java))})
                            { Text("Create an Account")}
//                            startActivity(Intent(this@Login, NaturalDeduction1::class.java))
                        }
                    }
                }
            }
        }
    }
}