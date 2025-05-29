package com.dhontiveros.presentation.ui.screen

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextReplacement
import com.dhontiveros.domain.model.RobotDomainDirection
import com.dhontiveros.presentation.ui.commons.base.BaseScreen
import com.dhontiveros.presentation.ui.commons.extensions.waitForNodeDisplayed
import com.dhontiveros.presentation.ui.commons.extensions.waitForNodeWithTagDisplayed

class MainScreen(
    private val composeTestRule: ComposeTestRule
) : BaseScreen(composeTestRule, MainScreenTestTags.SCREEN) {

    // Plateau - Size
    // -------------------------------------------------------------
    fun inputValidPlateauSize() = apply {
        inputTextByTag(
            testTag = MainScreenTestTags.PLATEAU_INPUT_POS_X,
            message = INPUT_VALID_PLATEAU_SIZE_X
        )
        inputTextByTag(
            testTag = MainScreenTestTags.PLATEAU_INPUT_POS_Y,
            message = INPUT_VALID_PLATEAU_SIZE_Y
        )
    }

    fun inputInvalidPlateauSize() = apply {
        inputTextByTag(
            testTag = MainScreenTestTags.PLATEAU_INPUT_POS_X,
            message = INPUT_NUMBERS_INVALID
        )
        inputTextByTag(
            testTag = MainScreenTestTags.PLATEAU_INPUT_POS_Y,
            message = INPUT_NUMBERS_INVALID
        )
    }

    fun inputInvalidNumberPlateauSizeX(inputNumber: Long) = apply {
        inputTextByTag(
            testTag = MainScreenTestTags.PLATEAU_INPUT_POS_X,
            message = String.format(INPUT_INVALID_NUMBER_PREFIX, inputNumber)
        )
    }

    fun checkInputPlateauSizeXNumber(inputNumber: Long) = apply {
        checkInputText(MainScreenTestTags.PLATEAU_INPUT_POS_X, inputNumber.toString())
    }

    fun checkPlateauInputIsEmpty() = apply {
        checkInputText(MainScreenTestTags.PLATEAU_INPUT_POS_X, "")
        checkInputText(MainScreenTestTags.PLATEAU_INPUT_POS_Y, "")
    }

    // Robot - Start position
    // -------------------------------------------------------------
    fun inputValidStartRobotPosition() = apply {
        inputTextByTag(
            testTag = MainScreenTestTags.ROBOT_INPUT_POS_X,
            message = INPUT_VALID_ROBOT_POS_X
        )
        inputTextByTag(
            testTag = MainScreenTestTags.ROBOT_INPUT_POS_Y,
            message = INPUT_VALID_ROBOT_POS_Y
        )
    }

    fun inputInvalidStartPosition() = apply {
        inputTextByTag(
            testTag = MainScreenTestTags.ROBOT_INPUT_POS_X,
            message = INPUT_NUMBERS_INVALID
        )
        inputTextByTag(
            testTag = MainScreenTestTags.ROBOT_INPUT_POS_Y,
            message = INPUT_NUMBERS_INVALID
        )
    }

    fun checkStartPositionInputIsEmpty() = apply {
        checkInputText(MainScreenTestTags.ROBOT_INPUT_POS_X, "")
        checkInputText(MainScreenTestTags.ROBOT_INPUT_POS_Y, "")
    }

    // Robot- Direction
    // -------------------------------------------------------------
    fun selectRobotDirection(direction: RobotDomainDirection) = apply {
        composeTestRule.waitForNodeWithTagDisplayed(
            String.format(
                MainScreenTestTags.ROBOT_INPUT_DIRECTION,
                direction.toString()
            )
        ).performClick()
    }

    // Robot - Movements
    // -------------------------------------------------------------
    fun inputValidMovements() = apply {
        inputTextByTag(
            testTag = MainScreenTestTags.ROBOT_INPUT_MOVEMENTS,
            message = INPUT_VALID_MOVEMENTS
        )
    }

    fun inputTextMovements(text: String) = apply {
        inputTextByTag(
            testTag = MainScreenTestTags.ROBOT_INPUT_MOVEMENTS,
            message = text
        )
    }

    fun checkTextInputMovement(text: String) = apply {
        checkInputText(
            testTag = MainScreenTestTags.ROBOT_INPUT_MOVEMENTS,
            text = text
        )
    }

    // Form - Submit button
    // -------------------------------------------------------------
    fun checkSubmitButtonIsNotEnabled() = apply {
        composeTestRule
            .waitForNodeWithTagDisplayed(tag = MainScreenTestTags.ROBOT_SUBMIT_FORM_BUTTON)
            .assertIsNotEnabled()
    }

    fun submitFormAndWaitForResult() = apply {
        with(composeTestRule) {
            waitForNodeDisplayed {
                it.onNode(
                    hasTestTag(MainScreenTestTags.ROBOT_SUBMIT_FORM_BUTTON)
                )
            }.performClick()
            waitForNodeWithTagDisplayed(MainScreenTestTags.ROBOT_RESULT_POSITION)
            waitForNodeWithTagDisplayed(MainScreenTestTags.ROBOT_RESULT_MOVEMENTS)
        }

    }

    private fun inputTextByTag(testTag: String, message: String) {
        composeTestRule.waitForNodeWithTagDisplayed(tag = testTag).apply {
            performTextClearance()
            performTextReplacement(text = message)
        }
    }

    private fun checkInputText(testTag: String, text: String) {
        composeTestRule.waitForNodeWithTagDisplayed(tag = testTag)
            .assert(hasText(text))
    }

    private companion object {
        const val INPUT_VALID_PLATEAU_SIZE_X = "5"
        const val INPUT_VALID_PLATEAU_SIZE_Y = "5"
        const val INPUT_VALID_ROBOT_POS_X = "1"
        const val INPUT_VALID_ROBOT_POS_Y = "2"
        const val INPUT_VALID_MOVEMENTS = "LMLMLMLMM"

        const val INPUT_NUMBERS_INVALID = "añldk"
        const val INPUT_INVALID_NUMBER_PREFIX = "-0,%s"
    }
}
