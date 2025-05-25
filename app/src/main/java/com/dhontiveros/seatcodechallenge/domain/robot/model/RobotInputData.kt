package com.dhontiveros.seatcodechallenge.domain.robot.model

data class RobotInputData(
    val surfaceSize: Pair<Long, Long>,
    val position: Pair<Long, Long>,
    val robotDirection: RobotDirection,
    val movementsList: List<RobotMovement>
)