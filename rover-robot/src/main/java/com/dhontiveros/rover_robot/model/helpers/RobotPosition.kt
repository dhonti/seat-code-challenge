package com.dhontiveros.rover_robot.model.helpers

data class RobotPosition(
    val x: Long,
    val y: Long,
    val direction: RobotDirection
) {
    fun updateFromMovement(inputRobotMovement: RobotMovement): RobotPosition =
        when (inputRobotMovement) {
            RobotMovement.MoveForward -> when (direction) {
                RobotDirection.North -> this.copy(y = y + 1)
                RobotDirection.South -> this.copy(y = y - 1)
                RobotDirection.East -> this.copy(x = x + 1)
                RobotDirection.West -> this.copy(x = x - 1)
            }

            RobotMovement.TurnLeft, RobotMovement.TurnRight -> copy(
                direction = direction.nextDirection(
                    inputRobotMovement
                )
            )
        }

    fun isInBounds(sizeX: Long, sizeY: Long): Boolean =
        (x in 0L..sizeX) && (y in 0L..sizeY)

    override fun toString(): String = "$x $y $direction"
}