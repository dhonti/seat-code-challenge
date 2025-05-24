package com.dhontiveros.seatcodechallenge.domain.models

sealed class Movement {
    data object TurnLeft : Movement()
    data object TurnRight : Movement()
    data object MoveForward : Movement()
}

sealed class Direction {
    data object North: Direction()
    data object South: Direction()
    data object East: Direction()
    data object West: Direction()
}