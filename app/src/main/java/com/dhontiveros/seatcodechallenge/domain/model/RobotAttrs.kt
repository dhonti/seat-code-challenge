package com.dhontiveros.seatcodechallenge.domain.model

sealed class Movement {
    data object TurnLeft : Movement()
    data object TurnRight : Movement()
    data object MoveForward : Movement()
}

sealed class Direction {
    data object North : Direction()
    data object South : Direction()
    data object East : Direction()
    data object West : Direction()
}

data class Position(
    val posX: Long,
    val posY: Long,
    val direction: Direction
) {
    fun nextPos(inputMovement: Movement): Position? = null
}