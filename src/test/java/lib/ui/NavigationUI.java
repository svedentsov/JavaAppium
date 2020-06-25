package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {
    protected static String
            MY_LISTS_LINK = "xpath://android.widget.FrameLayout[@content-desc='My lists']",
            OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    /**
     * Открыть страницу навигации.
     */
    public void openNavigation() {
        if (Platform.getInstance().isMW()) {
            this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigation button.", 5);
        } else {
            System.out.println("Method openNavigation() do nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    /**
     * Нажать на главной странице кнопку в тапбаре "Мой список".
     */
    public void clickMyLists() {
        if (Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttemps(MY_LISTS_LINK, "Cannot find navigate button to My lists", 5);
        }
        this.waitForElementAndClick(MY_LISTS_LINK, "Cannot find navigate button to My lists", 5);
    }
}
