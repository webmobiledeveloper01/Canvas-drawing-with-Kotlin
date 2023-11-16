package com.slots.casino.game.squarecanvasdemo

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChipDefaults.shape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.slots.casino.game.squarecanvasdemo.ui.theme.SquareCanvasDemoTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SquareCanvasDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Main( viewModal: MyViewModel = viewModel()): Unit {

    // This will detect any changes to data and recompose your composable.
    viewModal.onUpdate.value
    val openDialog = remember { mutableStateOf(false)  }
    val selectedIndex = remember { mutableStateOf(0)  }

    Box (modifier = Modifier.fillMaxSize()){

        Image(
            painterResource(R.drawable.image),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        viewModal.childTravellersList.forEachIndexed { index, squareInfo ->

            Box(modifier = Modifier.offset { IntOffset(squareInfo.posX.roundToInt(), squareInfo.poY.roundToInt()) }.clickable {
                Log.d("click","${index}")
                selectedIndex.value=index
                openDialog.value=true
            }.pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()

                    viewModal.update(index, dragAmount.x, dragAmount.y)




                }
            }) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(shape)
                        .background(squareInfo.color)
                )
                Text(text = squareInfo.des, Modifier.align(Alignment.Center), color = Color.White)
            }

        }

        if (openDialog.value) {

            AlertDialog(
                onDismissRequest = {

                },
                title = {
                    Text(text = "Alert")
                },
                text = {
                    Text(viewModal.childTravellersList[selectedIndex.value].des)
                },
                confirmButton = {
                    Button(

                        onClick = {
                            openDialog.value = false
                        }) {
                        Text("Ok")
                    }
                },
            )

        }

    }

}
