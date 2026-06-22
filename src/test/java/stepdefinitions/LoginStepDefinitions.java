package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginStepDefinitions {
    private WebDriver driver;
    private WebDriverWait wait;

    @Given("user berada di halaman login OrangeHRM")
    public void userBeradaDiHalamanLoginOrangeHRM() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
    }

    @When("user mengisi username {string}")
    public void userMengisiUsername(String username) {
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    @And("user mengisi password {string}")
    public void userMengisiPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    @And("user menekan tombol login")
    public void userMenekanTombolLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
    }

    @Then("user berhasil masuk ke halaman dashboard")
    public void userBerhasilMasukKeHalamanDashboard() {
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        WebElement dashboardTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h6.oxd-topbar-header-breadcrumb-module"))
        );

        Assert.assertEquals("Dashboard", dashboardTitle.getText());
    }

    @Then("pesan error login {string} ditampilkan")
    public void pesanErrorLoginDitampilkan(String expectedMessage) {
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-alert-content-text"))
        );

        Assert.assertEquals(expectedMessage, errorMessage.getText());
    }

    @When("user membuka menu Admin")
    public void userMembukaMenuAdmin() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Admin']"))).click();
        wait.until(ExpectedConditions.urlContains("/admin/viewSystemUsers"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Admin']")));
    }

    @And("user mengisi username pencarian {string}")
    public void userMengisiUsernamePencarian(String username) {
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                //By.xpath("//label[text()='Username']/../following-sibling::div//input")
                By.xpath("//input[@class='oxd-input oxd-input--focus']")
        ));
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    @And("user menekan tombol Search")
    public void userMenekanTombolSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Search']"))).click();
    }

    @Then("data user {string} ditampilkan")
    public void dataUserDitampilkan(String username) {
        By hasilPencarian = By.xpath("//div[@role='row']//div[@role='cell']//div[text()='" + username + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(hasilPencarian));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
