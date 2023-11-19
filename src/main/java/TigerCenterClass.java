import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

class TigerCenterClass {
    private WebDriver driver;

    private String baseUrl;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() throws Exception {
        Browser browser = Browser.FIREFOX; // Can be changed to Browser.CHROME
        driver = browser.setUpDriver();
        baseUrl = "https://"; // TARGET URL
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    public void testClassSearchButton() throws Exception {
        driver.get(baseUrl+"tigercenter.rit.edu/tigerCenterApp/landing");
        WebElement classButton = driver.findElement(By.xpath("//*[@id=\"angularApp\"]/app-root/div[2]/mat-sidenav-container[2]/mat-sidenav-content/div[2]/landing-page/div/div/div/div/div[4]/a[1]"));
        assertEquals("Class Search", classButton.getText());
        classButton.click();
    }


    private void searchAndPrintClassDetails(String courseName) throws InterruptedException {
        driver.get(baseUrl+"tigercenter.rit.edu/tigerCenterApp/api/class-search");
        //driver.findElement(By.xpath("//*[@id=\"ng2Completer\"]/div/input"));
        WebElement termDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"hideTerm\"]/div/select")));
        termDropdown.click();
        WebElement fallTermOption = driver.findElement(By.xpath("//*[@id=\"hideTerm\"]/div/select/option[2]"));
        fallTermOption.click();

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ng2Completer\"]/div/input")));
        searchBox.clear();
        searchBox.sendKeys(courseName);
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"classSearchContainer\"]/div[2]/form/div/button"));
        searchButton.click();

        List<WebElement> classes = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='classSearchContainer']/div[2]/div[4]/div[5]/app-class-search-row")));
        for (WebElement classElement : classes) {
            String className = classElement.findElement(By.className("classSearchResultsDisplayName")).getText();
            String classTimes = classElement.findElement(By.xpath("//span[contains(@class, 'classSearchBasicResultsText')][2]")).getText();
            String location = classElement.findElement(By.xpath("//a[contains(@href, 'maps')]")).getText();
            String instructor = classElement.findElement(By.xpath("//a[contains(@href, 'mailto')]")).getText();

            System.out.println("Class: " + className + ", Times: " + classTimes + ", Location: " + location + ", Instructor: " + instructor);
        }
        Thread.sleep(3000); // 3 seconds delay
    }

    @Test
    public void testClassSearchSWEN352() throws Exception {
        searchAndPrintClassDetails("SWEN 352");
    }

    @Test
    public void testClassSearchPSYC410() throws Exception {
        searchAndPrintClassDetails("PSYC 410");
    }

    @Test
    public void testClassSearchCCER601() throws Exception {
        searchAndPrintClassDetails("CCER 601");
    }


    private enum Browser {
        CHROME("webdriver.chrome.driver",
                "chromedriver",
                ChromeDriver::new),
        FIREFOX("webdriver.gecko.driver",
                "src/main/geckodriver",
                FirefoxDriver::new);

        private final String driverPropertyKey;
        private final String driverBaseName;
        private final Supplier<WebDriver> webDriverSupplier;

        Browser(String driverPropertyKey,
                String driverBaseName,
                Supplier<WebDriver> webDriverSupplier) {
            this.driverPropertyKey = driverPropertyKey;
            this.driverBaseName = driverBaseName;
            this.webDriverSupplier = webDriverSupplier;
        }

        private WebDriver setUpDriver() {
            String driverName = this.driverBaseName;
            if (System.getProperty("os.name").startsWith("Windows")) {
                driverName += ".exe";
            }
            System.setProperty(this.driverPropertyKey, driverName);
            return this.webDriverSupplier.get();
        }
    }
}
