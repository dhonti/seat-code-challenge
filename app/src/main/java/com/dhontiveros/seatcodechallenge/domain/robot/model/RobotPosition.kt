package com.dhontiveros.seatcodechallenge.domain.robot.model

data class RobotPosition(
    val posX: Long,
    val posY: Long,
    val robotDirection: RobotDirection
) {
    fun updateFromMovement(inputRobotMovement: RobotMovement): RobotPosition =
        when (inputRobotMovement) {
            RobotMovement.MoveForward -> when (robotDirection) {
                RobotDirection.North -> this.copy(posY = posY + 1)
                RobotDirection.South -> this.copy(posY = posY - 1)
                RobotDirection.East -> this.copy(posY = posX + 1)
                RobotDirection.West -> this.copy(posY = posX - 1)
            }

            RobotMovement.TurnLeft, RobotMovement.TurnRight -> copy(
                robotDirection = robotDirection.nextDirection(
                    inputRobotMovement
                )
            )
        }

    override fun toString(): String = "$posX $posY $robotDirection"
}