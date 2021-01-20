
import config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.regex.Pattern;


public class Homework2Test {

    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(Homework2Test.class);
    private final TestConfig cfg = ConfigFactory.create(TestConfig.class);
    private final String phoneXPathQuery = "//*[@class='area-code' and contains(text(),'" + cfg.getNumberBegin() + "')]";

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
    }
    @Test
    public void yandexTitleCheck() {
        driver.manage().window().maximize();
        driver.get(cfg.yaUrl());
        logger.info("Открыта страница yandex");
        Assert.assertEquals("Wrong title", cfg.getYandexTitle(), driver.getTitle());
        logger.info("Title checked");
    }
    @Test
    public void tele2NumberSearch() {
        driver.get(cfg.tele2Url());
        logger.info("Открыта страница tele2");
        By byId = By.id(cfg.getTele2FieldId());
        WebElement searchField =  new WebDriverWait(driver,5).until(ExpectedConditions.visibilityOfElementLocated(byId));
        Assert.assertNotNull("Поле для поиска не найдено", searchField);
        logger.info("Поле для поиска найдено");
        searchField.sendKeys(cfg.getNumberBegin());
        searchField.sendKeys(Keys.RETURN);
        logger.info("Значение установлено");
        By byXPath = By.xpath(phoneXPathQuery);
        Pattern phoneAreaPattern = Pattern.compile(cfg.getNumberBegin() + "[0-9]");
        new WebDriverWait(driver, 5).until(ExpectedConditions.textMatches(byXPath, phoneAreaPattern));
        logger.info("Телефоны найдены");
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Дравер закончил работу");
        }
    }
}
