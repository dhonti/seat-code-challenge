package com.dhontiveros.roverrobot.processor

import com.dhontiveros.commons.core.di.result.Either
import com.dhontiveros.roverrobot.model.RobotErrorInput
import com.dhontiveros.roverrobot.model.RobotOutput

interface RobotProcessor {
    fun move(inputJsonString: String): Either<RobotErrorInput, RobotOutput>
}