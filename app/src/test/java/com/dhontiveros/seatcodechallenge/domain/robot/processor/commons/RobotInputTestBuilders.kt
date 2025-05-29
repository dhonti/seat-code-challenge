package com.dhontiveros.seatcodechallenge.domain.robot.processor.commons

import com.dhontiveros.rover_robot.model.helpers.RobotDirection
import com.dhontiveros.rover_robot.model.helpers.RobotMovement
import com.dhontiveros.rover_robot.model.models.RobotInput
import com.dhontiveros.commons.robot.RobotInputDto
import com.dhontiveros.commons.robot.RobotInputPositionDto

fun buildRobotInputJson(
    plateauX: Long = 5,
    plateauY: Long = 5,
    posX: Long = 1,
    posY: Long = 2,
    direction: String = "N",
    movements: String = "LMLMLMLMM"
) = RobotInputDto(
    topRightCorner = RobotInputPositionDto(plateauX, plateauY),
    roverPosition = RobotInputPositionDto(posX, posY),
    roverDirection = direction,
    movements = movements
)

fun buildRobotInputData(
    plateauX: Long = 5,
    plateauY: Long = 5,
    posX: Long = 1,
    posY: Long = 2,
    direction: RobotDirection = RobotDirection.North,
    movements: List<RobotMovement> = listOf(
        RobotMovement.TurnLeft,
        RobotMovement.MoveForward,
        RobotMovement.TurnLeft,
        RobotMovement.MoveForward,
        RobotMovement.TurnLeft,
        RobotMovement.MoveForward,
        RobotMovement.TurnLeft,
        RobotMovement.MoveForward,
        RobotMovement.MoveForward
    )
) = RobotInput(
    surfaceSize = Pair(plateauX, plateauY),
    position = Pair(posX, posY),
    robotDirection = direction,
    movementsList = movements
)