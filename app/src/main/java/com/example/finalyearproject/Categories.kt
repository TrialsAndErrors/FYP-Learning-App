package com.example.finalyearproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.example.finalyearproject.ui.theme.FinalYearProjectTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths

class Categories : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cod : User = intent.getSerializableExtra("user") as User
        val role = cod.Role
        val build = Retro().build
        if (role == "Student"){
            setContent {
                var amount by remember { mutableStateOf(0) }
                var info = mutableListOf<Information>()
                val extra = build.getmodules()
                extra.enqueue(object: Callback<List<Information>>{
                    override fun onResponse(call: Call<List<Information>>, response: Response<List<Information>>) {
                        amount = response.body()!!.size
                        info = response.body() as MutableList<Information>
                    }
                    override fun onFailure(call: Call<List<Information>>, t: Throwable) {
                        Log.d("huh", t.toString())
                    }
                })
                FinalYearProjectTheme {
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        Column (modifier = Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(100.dp)) {
                            Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                Button(onClick = {
                                    startActivity(Intent(this@Categories, Login::class.java))
                                    finish()
                                }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Logout")}
                                Button(onClick = {
                                    startActivity(Intent(this@Categories, Settings::class.java).putExtra("user", cod))
                                    finish()
                                }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Settings") }
                            }
                            var i = 0
                            while (i < amount){
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                        val name = info[i].Name
                                        Button(onClick = {
                                            startActivity(Intent(this@Categories, Content::class.java).putExtra("user", cod).putExtra("module", name))
                                            finish()
                                        }) { Text(info[i].Name)}
                                    }
                                }
                                i++
                            }
                        }
                    }
                }
            }
        }
        else{
            setContent {
                var amount by remember { mutableStateOf(0) }
                var info = mutableListOf<Information>()
                val extra = build.getmodules()
                extra.enqueue(object: Callback<List<Information>>{
                    override fun onResponse(call: Call<List<Information>>, response: Response<List<Information>>) {
                        amount = response.body()!!.size
                        info = response.body() as MutableList<Information>
                    }
                    override fun onFailure(call: Call<List<Information>>, t: Throwable) {
                        Log.d("huh", t.toString())
                    }
                })
                FinalYearProjectTheme {
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        Column (modifier = Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(100.dp)) {
                            Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                Button(onClick = {
                                    startActivity(Intent(this@Categories, Login::class.java))
                                    finish()
                                }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Logout")}
                                Button(onClick = {
                                    startActivity(Intent(this@Categories, Stats::class.java).putExtra("user", cod).putExtra("module", "Mathematics and Logic"))
                                    finish()
                                }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Stats") }
                                Button(onClick = {
                                    startActivity(Intent(this@Categories, Settings::class.java).putExtra("user", cod))
                                    finish()
                                }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Settings") }
                                Button(onClick = {
                                    startActivity(Intent(this@Categories, AddModule::class.java).putExtra("user", cod))
                                    finish()
                                }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Add Module") }
                            }
                            var i = 0
                            while (i < amount){
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                        val name = info[i].Name
                                        Button(onClick = {
                                            startActivity(Intent(this@Categories, Content::class.java).putExtra("user", cod).putExtra("module", name))
                                            finish()
                                        }) { Text(info[i].Name)}
                                    }
                                }
                                i++
                            }
                        }
                    }
                }
            }
        }
    }
}
