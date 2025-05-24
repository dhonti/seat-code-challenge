package com.dhontiveros.seatcodechallenge.domain.model

data class InputData(
    val surfaceSize: Pair<Long, Long>,
    val position: Pair<Long, Long>,
    val direction: Direction,
    val movementsList: List<Movement>
)