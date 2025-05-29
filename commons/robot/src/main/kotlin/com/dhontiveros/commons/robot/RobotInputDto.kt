package com.dhontiveros.commons.robot

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class RobotInputDto(
    val topRightCorner: RobotInputPositionDto,
    val roverPosition: RobotInputPositionDto,
    val roverDirection: String,
    val movements: String
)

@JsonClass(generateAdapter = true)
data class RobotInputPositionDto(
    val x: Long,
    val y: Long
)

fun RobotInputDto.toJsonString(moshi: Moshi): String {
    return moshi.adapter(RobotInputDto::class.java).toJson(this)
}
