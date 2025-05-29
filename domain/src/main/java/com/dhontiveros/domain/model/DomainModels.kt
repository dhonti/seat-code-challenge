package com.dhontiveros.domain.model

sealed class RobotDomainResult {
    data class Error(val errorInput: RobotDomainErrorInput) : RobotDomainResult()
    data class Success(val robotOutput: RobotDomainOutput) : RobotDomainResult()
}

sealed class RobotDomainErrorInput {
    data object None : RobotDomainErrorInput()
    data object PlateauSize : RobotDomainErrorInput()
    data object StartDirection : RobotDomainErrorInput()
    data object StartPosition : RobotDomainErrorInput()
    data object Movements : RobotDomainErrorInput()
    data object General : RobotDomainErrorInput()
}

data class RobotDomainOutput(
    val posX: Long,
    val posY: Long,
    val direction: RobotDomainDirection,
    val movementsApplied: Long
)

sealed class RobotDomainDirection(private val code: String) {
    object North : RobotDomainDirection("N")
    object South : RobotDomainDirection("S")
    object East : RobotDomainDirection("E")
    object West : RobotDomainDirection("W")

    override fun toString(): String = code
}
