package com.dhontiveros.seatcodechallenge.domain.robot.model.attrs

sealed class RobotDirection(private val code: String) {
    object North : RobotDirection("N")
    object South : RobotDirection("S")
    object East : RobotDirection("E")
    object West : RobotDirection("W")

    override fun toString(): String = code

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