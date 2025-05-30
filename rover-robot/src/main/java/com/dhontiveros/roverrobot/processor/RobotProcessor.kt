package com.dhontiveros.roverrobot.processor

import com.dhontiveros.roverrobot.model.RobotResult

interface RobotProcessor {
    fun move(inputJsonString: String): RobotResult
}