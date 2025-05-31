package com.dhontiveros.seatcodechallenge.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dhontiveros.domain.model.RobotDomainDirection
import com.dhontiveros.seatcodechallenge.ui.commons.base.AcceptanceTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainScreenTest : AcceptanceTest() {

    @Test
    fun robotForm_isInitiallyEmpty_andSubmitButtonIsDisabled() {
        onMainScreen()
            .checkSubmitButtonIsNotEnabled()

        compareScreenshot()
    }

    @Test
    fun robotForm_IsPartiallyFilled_andSubmitButtonIsDisabled() {
        onMainScreen()
            .inputValidPlateauSize()
            .inputValidStartRobotPosition()
        closeKeyBoard()
    }

    @Test
    fun robotFormPlateauInput_receivesInvalidInput_andPlateauInputIsEmpty() {
        onMainScreen()
            .inputInvalidPlateauSize()
            .checkPlateauInputIsEmpty()
    }

    @Test
    fun robotFormPlateauInput_receivesInvalidNumber_andPlateauInputFilterCorrectNumber() {
        val inputNumber = 5L
        onMainScreen()
            .inputInvalidNumberPlateauSizeX(inputNumber = inputNumber)
            .checkInputPlateauSizeXNumber(inputNumber = inputNumber)
    }

    @Test
    fun robotFormStartPositionInput_receivesInvalidInput_andStartPositionInputIsEmpty() {
        onMainScreen()
            .inputInvalidStartPosition()
            .checkStartPositionInputIsEmpty()
    }

    @Test
    fun robotFormInputMovements_receivesInvalidInput_andFilterOnlyAcceptedCharacters() {
        val startInputText = "LMRMmTXMtLrUI"
        val filteredInputText = startInputText.uppercase().filter {
            it == 'L' || it == 'R' || it == 'M'
        }
        onMainScreen()
            .inputTextMovements(text = startInputText)
            .checkTextInputMovement(text = filteredInputText)
    }

    @Test
    fun whenFormIsFilled_buttonIsEnabled_andResultAppearsAfterClickSubmit() {
        val mainScreen = onMainScreen()
            .inputValidPlateauSize()
            .inputValidStartRobotPosition()
            .selectRobotDirection(RobotDomainDirection.North)
            .inputValidMovements()

        closeKeyBoard()
        mainScreen.submitFormAndWaitForResult()

        compareScreenshot()
    }

    @Test
    fun whenFormIsFilled_submitIsClicked_andResultDisappearsAfterInputSomeField() {
        val mainScreen = onMainScreen()
            .inputValidPlateauSize()
            .inputValidStartRobotPosition()
            .selectRobotDirection(RobotDomainDirection.North)
            .inputValidMovements()

        closeKeyBoard()
        with(mainScreen){
            submitFormAndWaitForResult()
            removePlateauSize()
            closeKeyBoard()
            checkNoFeedbackExists()
            checkSubmitButtonIsNotEnabled()
        }
    }

    // ERROR MANAGEMENT:
    // ----------------------------------

    @Test
    fun whenFormIsFilledWithZeroPlateauSize_submitButtonIsEnabled_andErrorAppearsAfterClickSubmit() {
        val mainScreen = onMainScreen()
            .inputZeroPlateauSize()
            .inputValidStartRobotPosition()
            .selectRobotDirection(RobotDomainDirection.North)
            .inputValidMovements()

        closeKeyBoard()
        mainScreen.submitFormAndWaitForPlateauError()

        compareScreenshot()
    }

    @Test
    fun whenFormIsFilledWithOutBoundsStartPosRobot_submitButtonIsEnabled_andErrorAppearsAfterClickSubmit() {
        val mainScreen = onMainScreen()
            .inputValidPlateauSize()
            .inputValidOutStartRobotPosition()
            .selectRobotDirection(RobotDomainDirection.North)
            .inputValidMovements()

        closeKeyBoard()
        mainScreen.submitFormAndWaitForRobotStartPos()

        compareScreenshot()
    }

}