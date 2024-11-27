package uk.co.bbc.application.utils

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput

object ComposeActions {

    // Click on the node with the specified test tag.
    fun performClickOnNodeWithTag(composeTestRule: ComposeTestRule, testTag: String) {
        composeTestRule.onNodeWithTag(testTag).performClick()
    }

    // Click on the node containing the specified text.
    fun performClickOnNodeWithText(composeTestRule: ComposeTestRule, testTag: String) {
        composeTestRule.onNodeWithText(testTag).performClick()
    }

    // Wait until the loading spinner disappears, or the timeout is reached.
    fun waitForLoadingSpinnerToDisappear(composeTestRule: ComposeTestRule, tag: String, timeoutMillis: Long = 5000) {
        composeTestRule.waitUntil(timeoutMillis = timeoutMillis) {
            composeTestRule.onAllNodesWithContentDescription(tag).fetchSemanticsNodes().isEmpty()
        }
    }

    // Scroll to the node containing the specified text.
    fun scrollToNodeWithText(composeTestRule: ComposeTestRule, text: String, substring: Boolean = false, ignoreCase: Boolean = false) {
        composeTestRule.onNodeWithText(text, substring = substring, ignoreCase = ignoreCase)
            .performScrollTo()
    }
}
