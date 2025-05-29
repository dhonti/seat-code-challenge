package com.dhontiveros.domain.mapper

import com.dhontiveros.domain.model.RobotDomainDirection
import com.dhontiveros.domain.model.RobotDomainErrorInput
import com.dhontiveros.domain.model.RobotDomainOutput
import com.dhontiveros.domain.model.RobotDomainResult
import com.dhontiveros.rover_robot.model.RobotErrorInput
import com.dhontiveros.rover_robot.model.RobotOutput
import com.dhontiveros.rover_robot.model.RobotResult
import com.dhontiveros.rover_robot.model.helpers.RobotDirection

fun RobotResult.toDomain(): RobotDomainResult = when (this) {
    is RobotResult.Error -> RobotDomainResult.Error(errorInput.toDomain())
    is RobotResult.Success -> RobotDomainResult.Success(robotOutput.toDomain())
}

fun RobotErrorInput.toDomain(): RobotDomainErrorInput = when (this) {
    is RobotErrorInput.None -> RobotDomainErrorInput.None
    RobotErrorInput.PlateauSize -> RobotDomainErrorInput.PlateauSize
    RobotErrorInput.StartDirection -> RobotDomainErrorInput.StartDirection
    RobotErrorInput.StartPosition -> RobotDomainErrorInput.StartPosition
    RobotErrorInput.Movements -> RobotDomainErrorInput.Movements
    RobotErrorInput.General -> RobotDomainErrorInput.General
}

fun RobotOutput.toDomain(): RobotDomainOutput = RobotDomainOutput(
    posX = position.x,
    posY = position.y,
    direction = position.direction.toDomain(),
    movementsApplied = movementsApplied
)

fun RobotDirection.toDomain(): RobotDomainDirection = when (this) {
    RobotDirection.North -> RobotDomainDirection.North
    RobotDirection.South -> RobotDomainDirection.South
    RobotDirection.East -> RobotDomainDirection.East
    RobotDirection.West -> RobotDomainDirection.West
}