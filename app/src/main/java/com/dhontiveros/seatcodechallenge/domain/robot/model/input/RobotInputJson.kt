package com.dhontiveros.seatcodechallenge.domain.robot.model.input

data class RobotInputJson(
    val topRightCorner: RobotInputJsonPosition,
    val roverPosition: RobotInputJsonPosition,
    val roverDirection: String,
    val movements: String
)

data class RobotInputJsonPosition(
    val x: Long,
    val y: Long
)
