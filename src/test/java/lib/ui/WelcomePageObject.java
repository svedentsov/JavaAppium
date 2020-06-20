package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject {

    private static final String
            STEP_LEARN_MORE_LINK = "Learn more about Wikipedia",
            STEP_WAIT_FOR_NEW_WAY_TO_EXPLORE_TEXT = "New ways to explore",
            STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "Add or edit preferred languages",
            STEP_LEARN_MORE_ABOUT_DATA_COLLECTED = "Learn more about data collected",
            NEXT_BUTTON = "Next",
            GET_STARTED_BUTTON = "Get started",
            SKIP_BUTTON = "Skip";

    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        this.waitForElementPresent(By.id(STEP_LEARN_MORE_LINK), "Cannot find 'Learn more about Wikipedia' link", 10);
    }

    public void waitForNewWayToExploreText() {
        this.waitForElementPresent(By.id(STEP_WAIT_FOR_NEW_WAY_TO_EXPLORE_TEXT), "Cannot find 'New ways to explore' text", 10);
    }

    public void waitForAddOrEditPreferredLangLink() {
        this.waitForElementPresent(By.id(STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK), "Cannot find 'Add or edit preferred languages' link", 10);
    }

    public void waitForLearnMoreAboutDataCollected() {
        this.waitForElementPresent(By.id(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED), "Cannot find 'Learn more about data collected' link", 10);
    }

    public void clickNextButton() {
        this.waitForElementAndClick(By.id(NEXT_BUTTON), "Cannot find 'Next' button", 10);
    }

    public void clickGetStartedButton() {
        this.waitForElementAndClick(By.id(GET_STARTED_BUTTON), "Cannot find 'Get started' button", 10);
    }

    public void clickSkip() {
        this.waitForElementAndClick(By.id(SKIP_BUTTON), "Cannot find 'Skip' button on iOS", 5);
    }
}
