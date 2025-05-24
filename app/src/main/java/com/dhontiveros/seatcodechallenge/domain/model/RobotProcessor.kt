package com.dhontiveros.seatcodechallenge.domain.model

import javax.inject.Inject

class RobotProcessor @Inject constructor() {

    fun move(inputData: InputData): Position {
        var currentPosition = Position(
            posX = inputData.position.first,
            posY = inputData.position.second,
            direction = inputData.direction
        )
        inputData.movementsList.forEach { movement ->
            currentPosition = currentPosition.updateFromMovement(inputMovement = movement)
        }
        return currentPosition
    }

}