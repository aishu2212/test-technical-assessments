package uk.co.bbc.application.support

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import uk.co.bbc.application.TEST_TAG_BREAKING_NEWS_BUTTON
import uk.co.bbc.application.TEST_TAG_DROPDOWN_MENU
import uk.co.bbc.application.TEST_TAG_GO_TO_BUTTON
import uk.co.bbc.application.TEST_TAG_LAST_UPDATED
import uk.co.bbc.application.TEST_TAG_REFRESH_BUTTON
import uk.co.bbc.application.utils.ComposeAssertions
import uk.co.bbc.application.utils.ComposeActions

object HomepageHelper {
    // Helper function to check the main activity is loaded
    fun waitForMainActivityToLoad(composeTestRule: ComposeTestRule) {
        composeTestRule.waitForIdle()
        // Verify key logo is visible to ensure page loaded
        ComposeAssertions.isDisplayedWithText(composeTestRule, "My BBC")
    }


}
