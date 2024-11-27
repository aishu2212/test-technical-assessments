# Homepage Test Suite - README

## Overview

This project aims to test the functionality of the BBC homepage using Jetpack Compose UI testing. The test suite includes various tests to verify the correct behavior of the homepage, including interactions with UI elements like buttons, dropdown menus, and loading indicators. To make the tests more maintainable and reusable, helper functions provided as a part of assessment are used that separate UI actions (`ComposeActions`) and assertions (`ComposeAssertions`).

## Project Structure

### 1. **Tests Directory**
- **HomepageTest.kt**: Contains the UI tests for the homepage. These tests interact with the UI and verify that the page behaves as expected. Running these test cases should provide results as below:
![image](https://github.com/user-attachments/assets/8993a978-af23-4a58-9662-e9bd4a4cef69)

[Test+Simulation+in+Android.webm](https://github.com/user-attachments/assets/447c812f-3467-47e7-b691-3894436dc87b)

### 2. **Helper Functions**
- **ComposeActions.kt**: A utility file that defines helper functions for performing actions on the UI, such as clicking buttons, waiting for loading spinners, and refreshing the page.
- **ComposeAssertions.kt**: A utility file that defines helper functions for verifying the state of the UI, such as checking if an element is displayed, ensuring text matches a regex, and verifying if the text has changed after an action.
- **HomepageHelper.kt**: A utility file that defines the assertion for main activity to load.

---

## How to Use

### 1. **Adding Actions and Assertions**

To keep tests clean and reusable, all UI actions and assertions are abstracted into helper functions.

## ComposeAssertions

- `isDisplayedWithTag(composeTestRule, testTag)`  
  Asserts that the node with the specified test tag is displayed.

- `isDisplayedWithText(composeTestRule, text)`  
  Asserts that the node with the specified text is displayed.

- `isNotDisplayedWithText(composeTestRule, text)`  
  Asserts that the node with the specified text is not displayed.

- `assertNodeCountWithContentDescription(composeTestRule, text, expectedCount)`  
  Asserts the number of nodes with the given content description matches the expected count.

- `doesNotExist(composeTestRule, testTag)`  
  Asserts that the node with the specified test tag does not exist.

- `assertNodeExistsWithText(composeTestRule, text, substring, ignoreCase)`  
  Asserts that the node with the specified text exists, with optional substring and case-insensitive matching.

- `assertNodeCountWithText(composeTestRule, text, expectedCount)`  
  Asserts the number of nodes with the specified text matches the expected count.

- `assertLastUpdatedTimeIsCorrect(composeTestRule, tag, dateRegex)`  
  Asserts that the 'Last updated' time matches the given regex pattern.

- `assertLastUpdatedTimeHasChanged(composeTestRule, tag, initialTimeText, dateRegex)`  
  Asserts that the 'Last updated' time has changed after a refresh and matches the given regex pattern.

## ComposeActions

- `performClickOnNodeWithTag(composeTestRule, testTag)`  
  Performs a click on the node identified by the given test tag.

- `performClickOnNodeWithText(composeTestRule, testTag)`  
  Performs a click on the node containing the specified text.

- `waitForLoadingSpinnerToDisappear(composeTestRule, tag, timeoutMillis)`  
  Waits until the loading spinner disappears or the timeout is reached.

- `scrollToNodeWithText(composeTestRule, text, substring, ignoreCase)`  
  Scrolls to the node containing the specified text.


---

## Test Scenarios

### Scenario 1: **Ensure the user can successfully navigate to and view the content on the home page**
- Launch the app.
- Verify that the home page loads completely.
- Assert that essential content elements on the home page are visible.

### Scenario 2: **Verify that tapping the refresh button updates the last updated time**
- On the home page, tap the refresh button.
- Wait for the loading spinner to appear and then disappear.
- Verify that the last updated time updates to a new time.

### Scenario 3: **Verify the user can pick the ‘Technology’ topic from the Topic picker**
- Open the Topic picker.
- Select ‘Technology’ from the options.
- Verify that the ‘Go to..’ link name updates to ‘Go to Technology.’

### Scenario 4: **Verify that selecting the 'Go to Technology' link navigates the user to the correct content page**
- Tap on the 'Go to Technology' link.
- Verify that the user lands on the expected page.
- Scroll to the end of the page content and verify it loads fully.
- Verify if the user can navigate back to the home page.

### Scenario 5: **Choose the ‘TV Guide’ topic from the Topic picker and select ‘No’ in the alert confirmation**
- Open the Topic picker.
- Choose ‘TV Guide’ from the list of options.
- Tap the ‘Go to TV Guide’ link.
- Confirm that an alert dialog appears.
- In the alert dialog, select the "No" option.
- Verify that the user stays on the current page.

### Scenario 6: **Choose the ‘TV Guide’ topic and select ‘Yes’ in the alert confirmation**
- Open the Topic picker.
- Choose ‘TV Guide’ from the list of options.
- Tap the ‘Go to TV Guide’ link.
- Confirm that an alert dialog appears.
- In the alert dialog, select the "Yes" option.
- Verify that the user is redirected to the appropriate content page.
- Verify that the user can navigate back to the home page.

### Scenario 7: **Tap on the Breaking News button and handle the error alert**
- Tap the "Breaking News" button.
- Verify an error confirmation appears.
- Click ‘Ok’ and verify the user remains in a stable app state.

---

## How to Run Tests

To run the tests, use the Android Studio test runner or Gradle.

### Using Android Studio:
1. Open the project in Android Studio.
2. Navigate to the test file you wish to run (e.g., `HomepageTest.kt`).
3. Right-click on the test method and select **Run**.

### Using Gradle:
Run the following command from the terminal from ../test_technical_assessments/Android/ folder:
```bash
./gradlew connectedAndroidTest
