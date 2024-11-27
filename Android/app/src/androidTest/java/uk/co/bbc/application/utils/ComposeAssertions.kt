package uk.co.bbc.application.utils

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue

object ComposeAssertions {

    // Verifies that a UI element with the given testTag is displayed on the screen
    fun isDisplayedWithTag(composeTestRule: ComposeTestRule, testTag: String) {
        composeTestRule.onNodeWithTag(testTag).assertIsDisplayed()
    }

    // Verifies that a UI element with the specified text is displayed on the screen
    fun isDisplayedWithText(composeTestRule: ComposeTestRule, text: String) {
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }

    // Verifies that a UI element with the specified text is displayed on the screen
    fun isNotDisplayedWithText(composeTestRule: ComposeTestRule, text: String) {
        composeTestRule.onNodeWithText(text).assertIsNotDisplayed()
    }

    // Asserts that the number of nodes with the specified content description matches the expected count
    fun assertNodeCountWithContentDescription(composeTestRule: ComposeTestRule, text: String, expectedCount: Int) {
        composeTestRule.onAllNodesWithContentDescription(text).assertCountEquals(expectedCount)
    }

    // Verifies that a UI element with the given testTag does not exist on the screen
    fun doesNotExist(composeTestRule: ComposeTestRule, testTag: String) {
        composeTestRule.onNodeWithTag(testTag).assertDoesNotExist()
    }

    // Asserts that a UI element with the given text exists, with optional substring and case-insensitive matching
    fun assertNodeExistsWithText(composeTestRule: ComposeTestRule, text: String, substring: Boolean = false, ignoreCase: Boolean = false) {
        composeTestRule.onNodeWithText(text, substring = substring, ignoreCase = ignoreCase)
            .assertExists()
    }

    // Asserts that the number of nodes with the given text matches the expected count
    fun assertNodeCountWithText(composeTestRule: ComposeTestRule, text: String, expectedCount: Int) {
        composeTestRule.onAllNodesWithText(text).assertCountEquals(expectedCount)
    }

    // Verifies that the 'Last updated' time matches the given regex pattern
    fun assertLastUpdatedTimeIsCorrect(composeTestRule: ComposeTestRule, tag: String, dateRegex: Regex) {
        val timeNode = composeTestRule.onNodeWithTag(tag)
        val timeText = timeNode.fetchSemanticsNode().config[SemanticsProperties.Text].joinToString()
        assertTrue(dateRegex.matches(timeText))
    }

    // Verifies that the 'Last updated' time has changed after a refresh, and matches the given regex pattern
    fun assertLastUpdatedTimeHasChanged(composeTestRule: ComposeTestRule, tag: String, initialTimeText: String, dateRegex: Regex) {
        val updatedTimeNode = composeTestRule.onNodeWithTag(tag)
        val updatedTimeText = updatedTimeNode.fetchSemanticsNode().config[SemanticsProperties.Text].joinToString()
        assertNotEquals("Last updated time should change after refresh", initialTimeText, updatedTimeText)
        assertTrue(dateRegex.matches(updatedTimeText))
    }
}
