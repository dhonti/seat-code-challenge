package com.dhontiveros.seatcodechallenge.ui.screen

import androidx.annotation.StringRes
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextReplacement
import androidx.test.platform.app.InstrumentationRegistry
import com.dhontiveros.domain.model.RobotDomainDirection
import com.dhontiveros.presentation.R
import com.dhontiveros.presentation.ui.screen.MainScreenTestTags
import com.dhontiveros.seatcodechallenge.ui.commons.base.BaseScreen
import com.dhontiveros.seatcodechallenge.ui.commons.extensions.waitForNodeDisplayed
import com.dhontiveros.seatcodechallenge.ui.commons.extensions.waitForNodeWithTagDisplayed

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

    fun inputZeroPlateauSize() = apply {
        inputTextByTag(
            testTag = MainScreenTestTags.PLATEAU_INPUT_POS_X,
            message = INPUT_ZERO
        )
        inputTextByTag(
            testTag = MainScreenTestTags.PLATEAU_INPUT_POS_Y,
            message = INPUT_ZERO
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

    fun removePlateauSize() = apply {
        inputTextByTag(
            testTag = MainScreenTestTags.PLATEAU_INPUT_POS_X,
            message = ""
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

    fun inputValidOutStartRobotPosition() = apply {
        inputTextByTag(
            testTag = MainScreenTestTags.ROBOT_INPUT_POS_X,
            message = INPUT_VALID_OUT_ROBOT_POS
        )
        inputTextByTag(
            testTag = MainScreenTestTags.ROBOT_INPUT_POS_Y,
            message = INPUT_VALID_OUT_ROBOT_POS
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

    // Result -- Is not visible:

    fun checkNoFeedbackExists() = apply {
        composeTestRule
            .onNodeWithTag(MainScreenTestTags.ROBOT_RESULT_POSITION, useUnmergedTree = true)
            .assertIsNotDisplayed()
        composeTestRule
            .onNodeWithTag(MainScreenTestTags.ROBOT_RESULT_ERROR_SECTION, useUnmergedTree = true)
            .assertIsNotDisplayed()
    }

    // Result -- Success:

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

    // Result -- Error:

    fun submitFormAndWaitForPlateauError() = apply {
        submitFormAndWaitForErrorSection()
        waitForNodeWithTagAndText(
            tag = MainScreenTestTags.ROBOT_RESULT_ERROR_TITLE,
            textId = R.string.main_screen_error_plateau_size_title
        )
        waitForNodeWithTagAndText(
            tag = MainScreenTestTags.ROBOT_RESULT_ERROR_BODY,
            textId = R.string.main_screen_error_plateau_size_body
        )
    }

    fun submitFormAndWaitForRobotStartPos() = apply {
        submitFormAndWaitForErrorSection()
        waitForNodeWithTagAndText(
            tag = MainScreenTestTags.ROBOT_RESULT_ERROR_TITLE,
            textId = R.string.main_screen_error_start_position_title
        )
        waitForNodeWithTagAndText(
            tag = MainScreenTestTags.ROBOT_RESULT_ERROR_BODY,
            textId = R.string.main_screen_error_start_position_body
        )
    }


    private fun submitFormAndWaitForErrorSection() = apply {
        with(composeTestRule) {
            waitForNodeDisplayed {
                it.onNode(
                    hasTestTag(MainScreenTestTags.ROBOT_SUBMIT_FORM_BUTTON)
                )
            }.performClick()
            waitForNodeWithTagDisplayed(MainScreenTestTags.ROBOT_RESULT_ERROR_SECTION)
        }
    }

    private fun waitForNodeWithTagAndText(tag: String, @StringRes textId: Int) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule.waitForNodeDisplayed {
            it.onNode(hasTestTag(testTag = tag) and hasText(context.getString(textId)))
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

        const val INPUT_ZERO = "0"

        const val INPUT_VALID_OUT_ROBOT_POS = "9"
    }
}
