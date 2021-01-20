package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.Optional;

public class WebDriverFactory {

    private WebDriverFactory() {

    }

    private static WebDriver createNewDriver(String name, Optional<Capabilities> capabilities)
    {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("wrong name: " + name);
        try {
            switch (name.toLowerCase()) {
                case "chromium": {
                    WebDriverManager.chromiumdriver().setup();
                    return capabilities.map(value -> new ChromeDriver((ChromeOptions) value)).orElseGet(ChromeDriver::new);
                }
                case "chrome": {
                    WebDriverManager.chromedriver().setup();
                    return capabilities.map(value -> new ChromeDriver((ChromeOptions) value)).orElseGet(ChromeDriver::new);
                }
                case "opera": {
                    WebDriverManager.operadriver().setup();
                    return capabilities.map(value -> new OperaDriver((OperaOptions) value)).orElseGet(OperaDriver::new);
                }
                case "edge": {
                    WebDriverManager.edgedriver().setup();
                    return capabilities.map(value -> new EdgeDriver((EdgeOptions) value)).orElseGet(EdgeDriver::new);
                }
                case "firefox": {
                    WebDriverManager.firefoxdriver().setup();
                    return capabilities.map(value -> new FirefoxDriver((FirefoxOptions) value)).orElseGet(FirefoxDriver::new);
                }
                case "ie": {
                    WebDriverManager.iedriver().setup();
                    return capabilities.map(value -> new InternetExplorerDriver((InternetExplorerOptions) value)).orElseGet(InternetExplorerDriver::new);
                }
                case "safari": {
                    WebDriverManager.chromiumdriver().setup();
                    return capabilities.map(value -> new SafariDriver((SafariOptions) value)).orElseGet(SafariDriver::new);
                }
                default:
                    throw new IllegalArgumentException("there is no driver for " + name);
            }
        } catch (ClassCastException exception) {
            throw  new IllegalArgumentException("wrong capabilities");
        }
    }

    public static WebDriver createNewDriver(String name) {
        return createNewDriver(name, Optional.empty());
    }

    public static WebDriver createNewDriver(String name, Capabilities capabilities) {
        return createNewDriver(name, Optional.of(capabilities));
    }

    public static  WebDriver createNewDriver(Browser browser) {
        return createNewDriver(browser.name().toLowerCase());
    }
}
