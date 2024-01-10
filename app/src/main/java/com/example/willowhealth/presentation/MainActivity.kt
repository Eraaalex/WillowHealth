package com.example.willowhealth.presentation.main


//import com.samsung.android.sdk.healthdata.HealthDataStore

//class MainActivity : AppCompatActivity() {
//
//
//    private val viewModel  by viewModel<MainViewModel>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_main)
//
//        val module = getMainModule(this)
//        (applicationContext as WillowHealth).koinApp.modules(module) // integration
//        viewModel.steps.observe(this, Observer {stepsData->
//            stepsTextView.text = stepsData.toString()
//        })
//        viewModel.fetchData(HealthMetric.STEPS)
//        setContent {
//            Text("Hello")
////            WillowTheme {
//////                Text("Hello")
////                Log.d("MyApp", "Start MainScreen")
//////
////                // A surface container using the 'background' color from the theme
////                Surface(
////                    modifier = Modifier.fillMaxSize(),
////                    color = MaterialTheme.colorScheme.background
////                ) {
////////                    val steps = remember { mutableStateOf("") }
////////                    LaunchedEffect(viewModel) {
////////                        viewModel.steps.observe(this@MainActivity, Observer {stepsData->
////////                            steps.value = stepsData.toString()
////////                        })
////////                    }
////////                    Column {
////                    MainScreen(viewModel = viewModel)
//////                    Greeting("Android")
////////                    StepsText(steps.value)
//////                    BarChartPreview()
////                }
//
//
//            }
//        }
////        val module = getMainModule(this)
////        (applicationContext as WillowHealth).koinApp.modules(module) // integration
////        viewModel.fetchData(HealthMetric.STEPS)
//
//
//    }
//
//    private fun initViews(){
//        stepsTextView = findViewById(R.id.steps_tv)
//    }
//}


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.willowhealth.presentation.Screen
import com.example.willowhealth.ui.theme.WillowTheme
import com.example.willowhealth.HealthMetric
import com.example.willowhealth.app.WillowHealth
import com.example.willowhealth.presentation.main.di.getMainModule
//import com.example.willowhealth.ui.theme.WillowHealthTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val module = getMainModule(this)
        (applicationContext as WillowHealth).koinApp.modules(module) // integration

        // Set Compose content
        setContent {
            WillowTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen(viewModel = viewModel)
                }
            }
        }
        viewModel.fetchData(HealthMetric.STEPS)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun StepsScreen(viewModel: MainViewModel) {
    val stepsData = viewModel.steps.observeAsState()

    stepsData.value?.let {
        Text(text = it.toString())
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WillowTheme {
        Greeting("Android")
    }
}