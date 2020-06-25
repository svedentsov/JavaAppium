package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {
    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    /**
     * Ожидание появления элемента.
     *
     * @param locator          локатор
     * @param error_message    текст ошибки
     * @param timeoutInSeconds время ожидания
     * @return
     */
    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Ожидание появления элемента (таймаут по умолчанию 5 секунд).
     *
     * @param locator       локатор
     * @param error_message текст ошибки
     * @return
     */
    public WebElement waitForElementPresent(String locator, String error_message) {
        return waitForElementPresent(locator, error_message, 5);
    }

    /**
     * Нажать по элемент.
     *
     * @param locator          локатор
     * @param error_message    текст ошибки
     * @param timeoutInSeconds время ожидания
     * @return
     */
    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    /**
     * Ввести значение в поле ввода.
     *
     * @param locator          локатор
     * @param value            вводимое значение
     * @param error_message    текст ошибки
     * @param timeoutInSeconds время ожидания
     * @return
     */
    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    /**
     * Проверить отсутствие элемента.
     *
     * @param locator          локатор
     * @param error_message    текст ошибки
     * @param timeoutInSeconds время ожидания
     * @return
     */
    public Boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    /**
     * Удалить текст.
     *
     * @param locator          локатор
     * @param error_message    текст ошибки
     * @param timeoutInSeconds время ожидания
     * @return
     */
    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message);
        element.clear();
        return element;
    }

    /**
     * Скролл.
     *
     * @param timeOfSwipe время скролла
     */
    public void swipeUp(int timeOfSwipe) {
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);
            action
                    .press(x, start_y)
                    .waitAction(timeOfSwipe)
                    .moveTo(x, end_y)
                    .release()
                    .perform();
        } else {
            System.out.println("Method rotateScreenPortrait() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    /**
     * Быстрый скрол на основе обычного.
     */
    public void swipeUpQuick() {
        swipeUp(200);
    }

    /**
     * Эмуляция скрола для веб.
     */
    public void scrollWebPageUp() {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor jsExcecutor = (JavascriptExecutor) driver;
            jsExcecutor.executeScript("window.scrollBy(0,250)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    /**
     * Скролл пока элемент на странице не будет виден.
     *
     * @param locator       локатор
     * @param error_message текст ошибки
     * @param max_swipes    максимальное количество свайпов
     */
    public void scrollWebPageTillElementNotVisible(String locator, String error_message, int max_swipes) {
        int alreasy_swiped = 0;
        WebElement element = this.waitForElementPresent(locator, error_message);
        while (!this.isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp();
            ++alreasy_swiped;
            if (alreasy_swiped > max_swipes) {
                Assert.assertTrue(error_message, element.isDisplayed());
            }
        }

        if (Platform.getInstance().isMW()) {
            JavascriptExecutor jsExcecutor = (JavascriptExecutor) driver;
            jsExcecutor.executeScript("window.scrollBy(0,250)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    /**
     * Свайп веб-страницы до появления элемента.
     *
     * @param locator       локатор
     * @param error_message текст ошибки
     * @param max_swipes    максимальное количество свайпов
     */
    public void swipeUpFindElement(String locator, String error_message, int max_swipes) {
        By by = this.getLocatorByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up. \n" + error_message, 0);
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    /**
     * Скролл пока элемент на странице не будет виден (метод для iOS платформы).
     *
     * @param locator       локатор
     * @param error_message текст ошибки
     * @param max_swipes    максимальное количество свайпов
     */
    public void swipeUpTillElementAppear(String locator, String error_message, int max_swipes) {
        int already_swiped = 0;
        while (!this.isElementLocatedOnTheScreen(locator)) {
            if (already_swiped > max_swipes) {
                Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    /**
     * Метода сравнивает фактическое расположение объекта по оси Y относительно видимой части экрана.
     *
     * @param locator локатор
     * @return true когда объект будет виден на экране пользователя.
     */
    public boolean isElementLocatedOnTheScreen(String locator) {
        int element_location_by_y = this.waitForElementPresent(locator, "Cannot find element by locator", 5).getLocation().getY();
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor jsExcecutor = (JavascriptExecutor) driver;
            Object js_result = jsExcecutor.executeScript("return window.pageYOffset");
            element_location_by_y = Integer.parseInt(js_result.toString());
        }
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y < screen_size_by_y;
    }

    public void clickElementToTheRightUpperCorner(String locator, String error_message) {
        if (driver instanceof AppiumDriver) {
            WebElement element = this.waitForElementPresent(locator + "/..", error_message);
            int right_x = element.getLocation().getX();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;
            int width = element.getSize().getWidth();

            int point_to_click_x = (right_x + width) - 3;
            int point_to_click_y = middle_y;

            TouchAction action = new TouchAction((AppiumDriver) driver);
            action.tap(point_to_click_x, point_to_click_y).perform();
        } else {
            System.out.println("Method rotateScreenPortrait() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    /**
     * Метода для совершения свайпа по элементу справа на лево
     *
     * @param locator      локатор
     * @param error_string текст ошибки
     */
    public void swipeElementToLeft(String locator, String error_string) {
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(locator, error_string, 10);
            int left_x = element.getLocation().getX();
            int right_x = left_x + element.getSize().getWidth();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;

            TouchAction action = new TouchAction((AppiumDriver) driver);
            action.press(right_x, middle_y);
            action.waitAction(300);

            if (Platform.getInstance().isAndroid()) {
                action.moveTo(left_x, middle_y);
            } else {
                int offset_x = (-1 * element.getSize().getWidth());
                action.moveTo(offset_x, 0);
            }
            action.release();
            action.perform();
        }
    }

    /**
     * Метод возвращает число элементов, найденных на странице.
     *
     * @param locator локатор
     * @return количество найденных элементов
     */
    public int getAmountOfElements(String locator) {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    /**
     * Метод проверяет есть ли элемент на странице для веб.
     *
     * @param locator локатор
     * @return
     */
    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    /**
     * Метод кликает несколько раз по элементу, даже если первый раз клик был не успешным.
     *
     * @param locator            локатор
     * @param error_message      текст ошибки
     * @param amount_of_attempts количество попыток
     */
    public void tryClickElementWithFewAttemps(String locator, String error_message, int amount_of_attempts) {
        int current_attemps = 0;
        boolean need_more_attemps = true;
        while (need_more_attemps) {
            try {
                this.waitForElementAndClick(locator, error_message, 2);
                need_more_attemps = false;
            } catch (Exception e) {
                if (current_attemps > amount_of_attempts) {
                    this.waitForElementAndClick(locator, error_message, 2);
                }
            }
            ++current_attemps;
        }
    }

    /**
     * Метод проверяет  наличия элемента на странице.
     *
     * @param locator       локатор
     * @param error_message текст ошибки
     */
    public void assertElementNotPresent(String locator, String error_message) {
        int amount_of_element = getAmountOfElements(locator);
        if (amount_of_element > 0) {
            String default_result = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(default_result + " " + error_message);
        }
    }

    public String waitForElementAndGetAtribute(String locator, String attribute, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSecond);
        return element.getAttribute(attribute);
    }

    /**
     * Метод для построения локатора. В него передается строка, которая парсится и возвращается как By.
     *
     * @param locator_with_type локатор с указанием типа локатора
     * @return
     */
    public By getLocatorByString(String locator_with_type) {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];
        if (by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else if (by_type.equals("css")) {
            return By.cssSelector(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator);
        }
    }
}
