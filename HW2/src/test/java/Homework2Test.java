
import config.TestConfig;
import factory.Browser;
import factory.WebDriverFactory;
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

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class Homework2Test {

    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(Homework2Test.class);
    private final TestConfig cfg = ConfigFactory.create(TestConfig.class);
    private final String phoneXPathQuery = "//*[@class='area-code' and contains(text(),'" + cfg.getNumberBegin() + "')]";

    @Before
    public void setUp() {
        String browserName = System.getProperty("browser");
        if (browserName == null || browserName.isEmpty())
            driver = WebDriverFactory.createNewDriver(Browser.CHROME);
        else
            driver = WebDriverFactory.createNewDriver(browserName);
        logger.info("Драйвер поднят");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }
    @Test
    public void yandexTitleCheck() {
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
        WebElement searchField =  new WebDriverWait(driver,3).until(ExpectedConditions.visibilityOfElementLocated(byId));
        Assert.assertNotNull("Поле для поиска не найдено", searchField);
        logger.info("Поле для поиска найдено");
        searchField.sendKeys(cfg.getNumberBegin());
        searchField.sendKeys(Keys.RETURN);
        logger.info("Значение установлено");
        By byXPath = By.xpath(phoneXPathQuery);
        Pattern phoneAreaPattern = Pattern.compile(cfg.getNumberBegin() + "[0-9]");
        new WebDriverWait(driver, 3).until(ExpectedConditions.textMatches(byXPath, phoneAreaPattern));
        WebElement areaPhone = driver.findElement(byXPath);
        Assert.assertNotNull("Телефон не найден", areaPhone);
        logger.info("Телефон найден");
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Дравер закончил работу");
        }
    }
}
