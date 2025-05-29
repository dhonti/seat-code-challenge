package com.dhontiveros.roverrobot.model.helpers

sealed class RobotMovement {
    data object TurnLeft : RobotMovement()
    data object TurnRight : RobotMovement()
    data object MoveForward : RobotMovement()

    companion object {
        private fun from(char: Char): RobotMovement? = when (char.uppercaseChar()) {
            'L' -> TurnLeft
            'R' -> TurnRight
            'M' -> MoveForward
            else -> null
        }

        fun parse(input: String): Result<List<RobotMovement>> {
            val robotMovements = mutableListOf<RobotMovement>()
            for (char in input) {
                from(char)?.let {
                    robotMovements.add(it)
                } ?: run {
                    return Result.failure(IllegalStateException("Invalid character to be movement: $char"))
                }
            }
            return Result.success(robotMovements)
        }
    }
}