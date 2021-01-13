
import config.CheckTitleConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class CheckTitleTest {

    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(CheckTitleTest.class);
    private CheckTitleConfig cfg = ConfigFactory.create(CheckTitleConfig.class);

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
    }
    @Test
    public void titleCheck() {
        driver.get(cfg.url());
        logger.info("Открыта страница otus");
        Assert.assertEquals("Wrong title", cfg.getTitle(), driver.getTitle());
        logger.info("Title checked");
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Дравер закончил работу");
        }
    }
}
