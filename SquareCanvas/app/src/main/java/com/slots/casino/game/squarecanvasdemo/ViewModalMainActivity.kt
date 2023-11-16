package com.slots.casino.game.squarecanvasdemo

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    var childTravellersList = mutableListOf<SquareInfo>()
    var onUpdate = mutableStateOf(0)

    init {
        childTravellersList.add(SquareInfo(20.0,20.0, Color.Red,"R01"))
        childTravellersList.add(SquareInfo(200.0,200.0, Color.Blue,"E12"))
        childTravellersList.add(SquareInfo(300.0,30.0, Color.Gray,"M14"))
    }

    private fun updateUI() {
        onUpdate.value = (0..1_000_000).random()
    }

    fun update(index: Int, posX: Float, posY: Float){
        childTravellersList[index].posX += posX.toDouble()
        childTravellersList[index].poY += posY.toDouble()
        updateUI()
    }
}