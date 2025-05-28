package com.dhontiveros.seatcodechallenge.presentation.ui.screen

import com.dhontiveros.seatcodechallenge.domain.robot.model.attrs.RobotDirection
import com.dhontiveros.seatcodechallenge.presentation.ui.commons.base.AcceptanceTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class MainScreenTest : AcceptanceTest() {

    @Test
    fun robotForm_isInitiallyEmpty_andSubmitButtonIsDisabled() {
        onMainScreen()
            .checkSubmitButtonIsNotEnabled()
    }

    @Test
    fun robotFormPlateauInput_receivesInvalidInput_andPlateauInputIsEmpty(){
        onMainScreen()
            .inputInvalidPlateauSize()
            .checkPlateauInputIsEmpty()
    }

    @Test
    fun robotFormPlateauInput_receivesInvalidNumber_andPlateauInputFilterCorrectNumber(){
        val inputNumber = 5L
        onMainScreen()
            .inputInvalidNumberPlateauSizeX(inputNumber = inputNumber)
            .checkInputPlateauSizeXNumber(inputNumber = inputNumber)
    }

    @Test
    fun robotForm_IsPartiallyFilled_andSubmitButtonIsDisabled(){
        onMainScreen()
            .inputValidPlateauSize()
            .inputValidStartRobotPosition()
        closeKeyBoard()
    }

    @Test
    fun whenFormIsFilled_buttonIsEnabled_andResultAppearsAfterClickSubmit() {
        val mainScreen = onMainScreen()
            .inputValidPlateauSize()
            .inputValidStartRobotPosition()
            .selectRobotDirection(RobotDirection.North)
            .inputValidMovements()

        closeKeyBoard()
        mainScreen.submitFormAndWaitForResult()
    }

}