package com.dhontiveros.seatcodechallenge.domain.model

import javax.inject.Inject

class RobotProcessor @Inject constructor() {

    fun move(inputData: InputData): Position? {
        val position = inputData.position
        val direction = inputData.direction

        return null
    }

}