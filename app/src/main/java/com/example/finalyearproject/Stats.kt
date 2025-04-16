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
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.finalyearproject.ui.theme.FinalYearProjectTheme
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.collections.ArrayList


class Stats : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val build = Retro().build
        val cod : User = intent.getSerializableExtra("user") as User
        val vari = intent.getStringExtra("module")
        Log.d("polo", vari.toString())
        setContent {
            var amount by remember { mutableStateOf(0) }
            var score1 by remember { mutableStateOf(0f) }
            var score2 by remember { mutableStateOf(0f) }
            var score3 by remember { mutableStateOf(0f) }
            var visual = mutableListOf<Information>()
            var auditory = mutableListOf<Information>()
            var literary = mutableListOf<Information>()
            var extra = build.getdata("Visual")
            extra.enqueue(object: Callback<List<Information>> {
                override fun onResponse(call: Call<List<Information>>, response: Response<List<Information>>) {
                    amount = response.body()!!.size
                    visual = response.body() as MutableList<Information>
                }
                override fun onFailure(call: Call<List<Information>>, t: Throwable) {
                    Log.d("huh", t.toString())
                }
            })
            extra = build.getdata("Auditory")
            extra.enqueue(object: Callback<List<Information>> {
                override fun onResponse(call: Call<List<Information>>, response: Response<List<Information>>) {
                    amount = response.body()!!.size
                    auditory = response.body() as MutableList<Information>
                }
                override fun onFailure(call: Call<List<Information>>, t: Throwable) {
                    Log.d("Failure", t.toString())
                }
            })
            extra = build.getdata("Literary")
            extra.enqueue(object: Callback<List<Information>> {
                override fun onResponse(call: Call<List<Information>>, response: Response<List<Information>>) {
                    amount = response.body()!!.size
                    literary = response.body() as MutableList<Information>
                }
                override fun onFailure(call: Call<List<Information>>, t: Throwable) {
                    Log.d("Failure", t.toString())
                }
            })
            var results = build.average("Visual")
            results.enqueue(object: Callback<Float> {
                override fun onResponse(call: Call<Float>, response: Response<Float>) {
                    score1 = response.body()!!
                }
                override fun onFailure(call: Call<Float>, t: Throwable) {
                    Log.d("Failure", t.toString())
                }
            })
            results = build.average("Auditory")
            results.enqueue(object: Callback<Float> {
                override fun onResponse(call: Call<Float>, response: Response<Float>) {
                    score2 = response.body()!!
                }
                override fun onFailure(call: Call<Float>, t: Throwable) {
                    Log.d("Failure", t.toString())
                }
            })
            results = build.average("Literary")
            results.enqueue(object: Callback<Float> {
                override fun onResponse(call: Call<Float>, response: Response<Float>) {
                    score3 = response.body()!!
                }
                override fun onFailure(call: Call<Float>, t: Throwable) {
                    Log.d("Failure", t.toString())
                }
            })
            FinalYearProjectTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column (modifier = Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(50.dp)) {
                        Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            Button(onClick = {
                                startActivity(Intent(this@Stats, Categories::class.java).putExtra("user", cod))
                                finish()
                            }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Back") }
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Exercise Score Distribution")
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                            Text("Scores", color = Color.Red)
                            Text("0", color = Color.Red)
                            Text("1", color = Color.Red)
                            Text("2", color = Color.Red)
                            Text("3", color = Color.Red)
                            Text("4", color = Color.Red)
                            Text("5", color = Color.Red)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                            Text("Visual")
                            var i = 0
                            while (i < amount){
                                Text(visual[i].Name)
                                i++
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                            Text("Auditory")
                            var i = 0
                            while (i < amount){
                                Text(auditory[i].Name)
                                i++
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                            Text("Literary")
                            var i = 0
                            while (i < amount){
                                Text(literary[i].Name)
                                i++
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                            Text("Visual average Score: $score1")
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                            Text("Auditory average Score: $score2")
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                            Text("Literary average Score: $score3")
                        }
                    }
                }
            }
        }
    }
}