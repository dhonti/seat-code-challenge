package com.dhontiveros.seatcodechallenge.domain.robot.processor.commons

import com.dhontiveros.seatcodechallenge.domain.robot.model.attrs.RobotDirection
import com.dhontiveros.seatcodechallenge.domain.robot.model.attrs.RobotMovement
import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputData
import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputJson
import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputJsonPosition

fun buildRobotInputJson(
    plateauX: Long = 5,
    plateauY: Long = 5,
    posX: Long = 1,
    posY: Long = 2,
    direction: String = "N",
    movements: String = "LMLMLMLMM"
) = RobotInputJson(
    topRightCorner = RobotInputJsonPosition(plateauX, plateauY),
    roverPosition = RobotInputJsonPosition(posX, posY),
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
) = RobotInputData(
    surfaceSize = Pair(plateauX, plateauY),
    position = Pair(posX, posY),
    robotDirection = direction,
    movementsList = movements
)