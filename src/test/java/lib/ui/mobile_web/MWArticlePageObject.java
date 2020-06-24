package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@id='ca-watch']";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "xpath://*[@title='Remove this page from your watchlist']";
        CLOSE_ARTICLE_BUTTON = "id:Back";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
