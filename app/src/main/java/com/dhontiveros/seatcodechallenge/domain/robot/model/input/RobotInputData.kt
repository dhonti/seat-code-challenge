package com.dhontiveros.seatcodechallenge.domain.robot.model.input

import com.dhontiveros.seatcodechallenge.domain.robot.model.attrs.RobotDirection
import com.dhontiveros.seatcodechallenge.domain.robot.model.attrs.RobotMovement

data class RobotInputData(
    val surfaceSize: Pair<Long, Long>,
    val position: Pair<Long, Long>,
    val robotDirection: RobotDirection,
    val movementsList: List<RobotMovement>
)