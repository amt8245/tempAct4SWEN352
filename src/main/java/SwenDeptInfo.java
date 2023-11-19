    import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

class SwenDeptInfo {
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
    public void testSEDepartmentContactInfo() throws Exception {
        navigateToDepartmentContactPage();
        Thread.sleep(3000); // 3 seconds delay
    }
    private void navigateToDepartmentContactPage() {
        driver.get("https://www.rit.edu/");
        WebElement academicsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Academics")));
        academicsLink.click();
        WebElement undergLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"block-rit-bootstrap-subtheme-rit-main-menu\"]/ul/li[2]/div/div/ul[1]/li[2]/a")));
        undergLink.click();
        WebElement cookieLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"consent-accept\"]")));
        cookieLink.click();
        WebElement majorLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"block-rit-bootstrap-subtheme-content\"]/div[3]/div[1]/div/div[2]/div[4]/ul[1]/li[35]/a")));
        majorLink.click();
        WebElement deptLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"progress-navigation--sidebar--sidebar\"]/div/div/div[3]/a")));
        deptLink.click();
        WebElement contactLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"block-rit-bootstrap-subtheme-rit-main-menu\"]/ul/li[7]/a")));
        contactLink.click();

        WebElement chairLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"card-header-4291\"]/p/a/span[1]")));
        chairLink.click();
        WebElement test = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("        //*[@id=\"card-collapse-4291\"]/div/div/article/div\n")));

        try {
            // Assuming the first person listed on the page is the department chair
            String name = test.findElement(By.xpath("//*[@id=\"card-collapse-4291\"]/div/div/article/div/div[2]/div[1]/a")).getText();
            String email = test.findElement(By.xpath("//*[@id=\"card-collapse-4291\"]/div/div/article/div/div[3]/div[1]/a")).getText();
            String title = test.findElement(By.xpath("//*[@id=\"card-collapse-4291\"]/div/div/article/div/div[2]/div[2]")).getText();
            String department = test.findElement(By.xpath("//*[@id=\"card-collapse-4291\"]/div/div/article/div/div[2]/div[3]")).getText();
            String college = test.findElement(By.xpath("//*[@id=\"card-collapse-4291\"]/div/div/article/div/div[2]/div[4]")).getText();

            WebElement addressElement = driver.findElement(By.xpath("//*[@id=\"progress-navigation--sidebar--sidebar\"]/div/div/div/div/div/div/p[1]"));
            String address = addressElement.getText();
            String[] elements = (address.split("[\\r\\n]+"));
            address = elements[1] + ", " + elements[2];
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Title: " + title);
            System.out.println("Department: " + department);
            System.out.println("College: " + college);
            System.out.println("Address: " + address);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Contact information not found");
        }

    }
//    private void printDepartmentChairContactInfo() {
//        WebElement chairLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"card-header-4291\"]/p/a/span[1]")));
//        chairLink.click();
//        WebElement test = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("        //*[@id=\"card-collapse-4291\"]/div/div/article/div\n")));
//
//        try {
//            // Assuming the first person listed on the page is the department chair
//            String name = test.findElement(By.xpath("//*[@id=\"card-collapse-4291\"]/div/div/article/div/div[2]/div[1]/a")).getText();
//            String email = test.findElement(By.xpath("//*[@id=\"card-collapse-4291\"]/div/div/article/div/div[3]/div[1]/a")).getText();
//            String title = test.findElement(By.xpath("//*[@id=\"card-collapse-4291\"]/div/div/article/div/div[2]/div[2]")).getText();
//            String department = test.findElement(By.xpath("//*[@id=\"card-collapse-4291\"]/div/div/article/div/div[2]/div[3]")).getText();
//            String college = test.findElement(By.xpath("//*[@id=\"card-collapse-4291\"]/div/div/article/div/div[2]/div[4]")).getText();
//            String address = test.findElement(By.xpath("//*[@id=\"progress-navigation--sidebar--sidebar\"]/div/div/div/div/div/div/p[1]/text()[2]")).getText();
//
//            System.out.println("Name: " + name);
//            System.out.println("Email: " + email);
//            System.out.println("Title: " + title);
//            System.out.println("Department: " + department);
//            System.out.println("College: " + college);
//            System.out.println("Address: " + address);
//        } catch (Exception e) {
//            System.out.println(e.getStackTrace());
//            System.out.println("Contact information not found");
//        }
//    }


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
