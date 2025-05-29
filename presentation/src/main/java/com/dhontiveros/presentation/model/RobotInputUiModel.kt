package com.dhontiveros.presentation.model

import com.dhontiveros.commons.robot.RobotInputDto
import com.dhontiveros.commons.robot.RobotInputPositionDto
import com.dhontiveros.domain.model.RobotDomainDirection

data class RobotInputUiModel(
    val plateauSizeX: Long,
    val plateauSizeY: Long,
    val posX: Long,
    val posY: Long,
    val direction: String,
    val movementsList: String
)

data class RobotResultUiModel(
    val posX: Long,
    val posY: Long,
    val direction: RobotDomainDirection,
    val totalMovements: Long,
    val appliedMovements: Long
) {
    override fun toString(): String = "$posX $posY $direction"
}


fun RobotInputUiModel.toDto() = RobotInputDto(
    topRightCorner = RobotInputPositionDto(plateauSizeX, plateauSizeY),
    roverPosition = RobotInputPositionDto(posX, posY),
    roverDirection = direction,
    movements = movementsList
)