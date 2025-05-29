package com.dhontiveros.roverrobot.model

import com.dhontiveros.roverrobot.model.helpers.RobotDirection
import com.dhontiveros.roverrobot.model.helpers.RobotMovement
import com.dhontiveros.roverrobot.model.helpers.RobotPosition

data class RobotInput(
    val surfaceSize: Pair<Long, Long>,
    val position: Pair<Long, Long>,
    val robotDirection: RobotDirection,
    val movementsList: List<RobotMovement>
)

data class RobotOutput(
    val position: RobotPosition,
    val movementsApplied: Long
)

sealed class RobotErrorInput {
    data class None(val result: RobotInput) : RobotErrorInput()
    data object PlateauSize : RobotErrorInput()
    data object StartDirection : RobotErrorInput()
    data object StartPosition : RobotErrorInput()
    data object Movements : RobotErrorInput()
    data object General : RobotErrorInput()
}

sealed class RobotResult {
    data class Error(val errorInput: RobotErrorInput) : RobotResult()
    data class Success(val robotOutput: RobotOutput) : RobotResult()
}