package uk.co.bbc.application

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before
import uk.co.bbc.application.support.HomepageHelper
import uk.co.bbc.application.utils.ComposeActions
import uk.co.bbc.application.utils.ComposeAssertions

@RunWith(AndroidJUnit4::class)
class HomepageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mainActivityScenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        // Launch the MainActivity before each test
        mainActivityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testHomePageLoadsAndDropdownMenuToggles() {
        // Scenario 1: Ensure the user can successfully navigate to and view the content on the home page
        mainActivityScenario.use {
            // Launch the app and verify the home page loads completely.
            HomepageHelper.waitForMainActivityToLoad(composeTestRule) // checks "My BBC" to ensure main activity loaded
            ComposeAssertions.isDisplayedWithTag(composeTestRule, TEST_TAG_REFRESH_BUTTON)
            // Assert that essential content elements on the home page are visible.
            ComposeAssertions.isDisplayedWithTag(composeTestRule, TEST_TAG_LAST_UPDATED)
            ComposeAssertions.assertNodeCountWithContentDescription(composeTestRule, "BBC Logo", 2) // Assert there are exactly 2 nodes of BBC image logo

            composeTestRule.onNodeWithTag(TEST_TAG_DROPDOWN_MENU).assertDoesNotExist()
            ComposeAssertions.isDisplayedWithText(composeTestRule, "This is a BBC app with all your favourite content")
            // Verify the breaking news button is displayed
            ComposeAssertions.isDisplayedWithTag(composeTestRule, TEST_TAG_BREAKING_NEWS_BUTTON)
        }
    }

    @Test
    fun testRefreshButtonUpdatesLastUpdatedTime() {
        // Scenario: Verify that tapping the refresh button updates the last updated time
        mainActivityScenario.use {
            val dateRegex = Regex("""Last updated: \d{2} \w{3} \d{4} at \d{2}:\d{2}:\d{2}""")

            // Assert that the 'Last updated' text is initially displayed and matches the regex
            ComposeAssertions.assertLastUpdatedTimeIsCorrect(composeTestRule, TEST_TAG_LAST_UPDATED, dateRegex)

            // Check the initial last updated time
            val initialTimeNode = composeTestRule.onNodeWithTag(TEST_TAG_LAST_UPDATED)
            val initialTimeText = initialTimeNode.fetchSemanticsNode().config[SemanticsProperties.Text].joinToString()

            // Perform the refresh action (click the refresh button and wait for the spinner to disappear)
            ComposeActions.performClickOnNodeWithTag(composeTestRule, TEST_TAG_REFRESH_BUTTON)
            ComposeActions.waitForLoadingSpinnerToDisappear(composeTestRule, TEST_TAG_LOADING_SPINNER)

            // Assert that the last updated time has changed after the refresh
            ComposeAssertions.assertLastUpdatedTimeHasChanged(composeTestRule, TEST_TAG_LAST_UPDATED, initialTimeText, dateRegex)
        }
    }

    @Test
    fun testDropdownMenuItemClick() {
        // Scenario 3: Verify that selecting 'Technology' updates the 'Go to..' link name
        mainActivityScenario.use {
            HomepageHelper.waitForMainActivityToLoad(composeTestRule)

            // Verify dropdown is initially not displayed as tag is visible only when expanded
            ComposeAssertions.doesNotExist(composeTestRule, TEST_TAG_DROPDOWN_MENU)
            // Open the dropdown menu
            ComposeActions.performClickOnNodeWithText(composeTestRule, "Politics")
            ComposeAssertions.isDisplayedWithTag(composeTestRule, TEST_TAG_DROPDOWN_MENU)

            // Select 'Technology' from dropdown
            ComposeActions.performClickOnNodeWithText(composeTestRule, "Technology")
            ComposeAssertions.doesNotExist(composeTestRule, TEST_TAG_DROPDOWN_MENU)

            // Verify 'Go to Technology' link name
            ComposeAssertions.isDisplayedWithText(composeTestRule, "Go to Technology ")
        }
    }

    @Test
    fun testGoToTechnologyNavigationAndReturn() {
        // Scenario 4: Verify navigation to and return from 'Technology' content page
        mainActivityScenario.use {
            ComposeActions.performClickOnNodeWithText(composeTestRule, "Politics")
            ComposeAssertions.isDisplayedWithTag(composeTestRule, TEST_TAG_DROPDOWN_MENU)

            ComposeActions.performClickOnNodeWithText(composeTestRule, "Technology")
            ComposeActions.performClickOnNodeWithTag(composeTestRule, TEST_TAG_GO_TO_BUTTON)

            // Verify 'Technology' content page
            ComposeAssertions.assertNodeCountWithText(composeTestRule, "Technology", 2)
            ComposeActions.scrollToNodeWithText(composeTestRule, "This is the end of the placeholder text.", substring = true, ignoreCase = true)
            ComposeAssertions.assertNodeExistsWithText(composeTestRule, "This is the end of the placeholder text.", substring = true, ignoreCase = true)

            // Navigate back to the home page
            ComposeActions.performClickOnNodeWithTag(composeTestRule, TEST_TAG_BACK_BUTTON)
            ComposeAssertions.isDisplayedWithText(composeTestRule, "My BBC")
        }
    }

    @Test
    fun testStayOnHomePageAfterChoosingNoInAlert() {
        // Scenario 5: Verify user remains on home page after choosing 'No' in alert
        mainActivityScenario.use {
            ComposeActions.performClickOnNodeWithText(composeTestRule, "Politics")
            ComposeAssertions.isDisplayedWithTag(composeTestRule, TEST_TAG_DROPDOWN_MENU)

            // Choose TV Guide from list of options
            ComposeActions.performClickOnNodeWithText(composeTestRule, "TV Guide")
            ComposeActions.performClickOnNodeWithTag(composeTestRule, TEST_TAG_GO_TO_BUTTON)

            // Confirm alert and select 'No'
            ComposeAssertions.isDisplayedWithText(composeTestRule, "Do you have a TV License ?")
            ComposeActions.performClickOnNodeWithText(composeTestRule, "No")

            // Verify user remains on home page
            ComposeAssertions.isDisplayedWithText(composeTestRule, "My BBC")
        }
    }

    @Test
    fun testRedirectToContentPageAfterChoosingYesInAlert() {
        // Scenario 6: Verify user navigates to correct page after choosing 'Yes' in alert
        mainActivityScenario.use {
            ComposeActions.performClickOnNodeWithText(composeTestRule, "Politics")
            ComposeAssertions.isDisplayedWithTag(composeTestRule, TEST_TAG_DROPDOWN_MENU)

            // Choose TV Guide from list of options
            ComposeActions.performClickOnNodeWithText(composeTestRule, "TV Guide")
            ComposeActions.performClickOnNodeWithTag(composeTestRule, TEST_TAG_GO_TO_BUTTON)

            // Confirm alert and select 'Yes'
            ComposeAssertions.isDisplayedWithText(composeTestRule, "Do you have a TV License ?")
            ComposeActions.performClickOnNodeWithText(composeTestRule, "Yes")

            // Verify 'TV Guide' page content
            ComposeAssertions.assertNodeCountWithText(composeTestRule, "TV Guide", 2)
            ComposeActions.scrollToNodeWithText(composeTestRule, "This is the end of the placeholder text.", substring = true, ignoreCase = true)
            ComposeAssertions.assertNodeExistsWithText(composeTestRule, "This is the end of the placeholder text.", substring = true, ignoreCase = true)

            // Navigate back to the home page
            ComposeActions.performClickOnNodeWithTag(composeTestRule, TEST_TAG_BACK_BUTTON)
            ComposeAssertions.isDisplayedWithText(composeTestRule, "My BBC")
        }
    }

    @Test
    fun testBreakingNewsButton() {
        // Scenario 7: Verify error handling after tapping 'Breaking News' button
        mainActivityScenario.use {
            ComposeAssertions.isDisplayedWithTag(composeTestRule, TEST_TAG_BREAKING_NEWS_BUTTON)
            ComposeActions.performClickOnNodeWithTag(composeTestRule, TEST_TAG_BREAKING_NEWS_BUTTON)

            // Verify error alert and confirm
            ComposeAssertions.isDisplayedWithText(composeTestRule, "Something has gone wrong")
            ComposeActions.performClickOnNodeWithTag(composeTestRule, TEST_TAG_ALERT_CONFIRM_BUTTON)
            // App returns to stable state
            ComposeAssertions.isDisplayedWithText(composeTestRule, "My BBC")
        }
    }
}