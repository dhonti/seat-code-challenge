package com.dhontiveros.seatcodechallenge.domain.model

object RobotInputValidator {

    fun validateInput(inputData: InputData): ErrorInputRobot {
        if (!validateInputPlateauSize(inputSize = inputData.surfaceSize)) return ErrorInputRobot.PlateauSize
        if (!validateInputStarPosition(inputData = inputData)) return ErrorInputRobot.StartPosition
        if (!validateInputMovements(movementsList = inputData.movementsList)) ErrorInputRobot.Movements

        return ErrorInputRobot.None
    }

    private fun validateInputPlateauSize(inputSize: Pair<Long, Long>): Boolean =
        if (inputSize.first == 0L && inputSize.second == 0L) {
            false
        } else if (inputSize.first < 0L || inputSize.second < 0L) {
            false
        } else true

    private fun validateInputStarPosition(inputData: InputData): Boolean {
        val startPosition = inputData.position
        val plateauSize = inputData.surfaceSize
        return !(startPosition.first < 0L ||
                startPosition.second < 0L ||
                startPosition.first > plateauSize.first ||
                startPosition.second > plateauSize.second)
    }

    private fun validateInputMovements(movementsList: List<Movement>): Boolean =
        movementsList.isNotEmpty()

}

sealed class ErrorInputRobot {
    data object None : ErrorInputRobot()
    data object PlateauSize : ErrorInputRobot()
    data object StartPosition : ErrorInputRobot()
    data object Movements : ErrorInputRobot()
}