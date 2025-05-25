package com.dhontiveros.seatcodechallenge.domain.robot.processor

import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputJson
import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputJsonPosition
import com.dhontiveros.seatcodechallenge.presentation.ui.viewmodel.InitialInputData
import com.squareup.moshi.Moshi
import javax.inject.Inject

class RobotInputMapper @Inject constructor(
    private val moshi: Moshi
) {
    private val adapter by lazy { moshi.adapter(RobotInputJson::class.java) }

    fun toJsonString(initialInputData: InitialInputData): String {
        val inputReadyObject = toRobotInputJson(inputData = initialInputData)
        return adapter.toJson(inputReadyObject)
    }

    fun toRobotInputJson(jsonString: String): RobotInputJson? =
        try {
            adapter.fromJson(jsonString)
        } catch (e: Exception) {
            null
        }

    private fun toRobotInputJson(inputData: InitialInputData) = RobotInputJson(
        topRightCorner = RobotInputJsonPosition(
            x = inputData.plateauSizeX,
            y = inputData.plateauSizeY
        ),
        roverPosition = RobotInputJsonPosition(x = inputData.posX, y = inputData.posY),
        roverDirection = inputData.direction,
        movements = inputData.movementsList
    )
}