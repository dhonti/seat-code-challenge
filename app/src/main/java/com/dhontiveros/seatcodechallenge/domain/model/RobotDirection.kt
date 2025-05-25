package com.dhontiveros.seatcodechallenge.domain.model

sealed class RobotDirection {
    data object North : RobotDirection()
    data object South : RobotDirection()
    data object East : RobotDirection()
    data object West : RobotDirection()

    override fun toString(): String {
        return this::class.simpleName?.first()?.uppercaseChar().toString()
    }

    fun nextDirection(inputRobotMovement: RobotMovement): RobotDirection =
        when (inputRobotMovement) {
            RobotMovement.TurnLeft -> turnLeft()
            RobotMovement.TurnRight -> turnRight()
            RobotMovement.MoveForward -> this
        }

    private fun turnLeft(): RobotDirection = when (this) {
        North -> West
        West -> South
        South -> East
        East -> North
    }

    private fun turnRight(): RobotDirection = when (this) {
        North -> East
        East -> South
        South -> West
        West -> North
    }
}