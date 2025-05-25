package com.dhontiveros.seatcodechallenge.domain.robot.model.input

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RobotInputJson(
    val topRightCorner: RobotInputJsonPosition,
    val roverPosition: RobotInputJsonPosition,
    val roverDirection: String,
    val movements: String
)

@JsonClass(generateAdapter = true)
data class RobotInputJsonPosition(
    val x: Long,
    val y: Long
)
