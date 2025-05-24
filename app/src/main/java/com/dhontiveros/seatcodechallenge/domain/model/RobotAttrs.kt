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

    fun nextDirection(inputMovement: Movement): Direction =
        when (inputMovement) {
            Movement.TurnLeft -> turnLeft()
            Movement.TurnRight -> turnRight()
            Movement.MoveForward -> this
        }

    private fun turnLeft(): Direction = when (this) {
        North -> West
        West -> South
        South -> East
        East -> North
    }

    private fun turnRight(): Direction = when (this) {
        North -> East
        East -> South
        South -> West
        West -> North
    }
}


data class Position(
    val posX: Long,
    val posY: Long,
    val direction: Direction
) {
    fun next(inputMovement: Movement): Position =
        when (inputMovement) {
            Movement.MoveForward -> when (direction) {
                Direction.North -> this.copy(posY = posY + 1)
                Direction.South -> this.copy(posY = posY - 1)
                Direction.East -> this.copy(posY = posX + 1)
                Direction.West -> this.copy(posY = posX - 1)
            }

            Movement.TurnLeft, Movement.TurnRight -> copy(
                direction = direction.nextDirection(
                    inputMovement
                )
            )
        }
}