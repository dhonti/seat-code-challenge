package com.dhontiveros.roverrobot.model.helpers

sealed class RobotDirection {
    data object North : RobotDirection()
    data object South : RobotDirection()
    data object East : RobotDirection()
    data object West : RobotDirection()

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

    companion object {
        fun from(char: Char): RobotDirection? = when (char.uppercaseChar()) {
            'N' -> North
            'S' -> South
            'E' -> East
            'W' -> West
            else -> null
        }
    }
}