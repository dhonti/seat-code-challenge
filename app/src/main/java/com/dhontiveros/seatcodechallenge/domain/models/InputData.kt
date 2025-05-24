package com.dhontiveros.seatcodechallenge.domain.models

data class InputData(
    val plateauSize: Pair<Int, Int>,
    val startPositionRobot: Pair<Int, Int>,
    val movementsList: List<Movement>
)