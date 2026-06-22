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
    private String createdUsername;
    private String createdEmployeeName;
    private String createdEmployeeLastName;

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

    @And("user menekan tombol Add pada halaman Admin")
    public void userMenekanTombolAddPadaHalamanAdmin() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Add']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Add User']")));
    }

    @And("user memilih role {string}")
    public void userMemilihRole(String role) {
        selectDropdownByLabel("User Role", role);
    }

    @And("user memilih employee dari autocomplete dengan keyword {string}")
    public void userMemilihEmployeeDariAutocompleteDenganKeyword(String keyword) {
        WebElement employeeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Employee Name']/../following-sibling::div//input")
        ));
        employeeInput.sendKeys(keyword);

        By optionLocator = By.xpath("//div[contains(@class,'oxd-autocomplete-option') and not(contains(.,'No Records Found'))]");
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
    }

    @When("user membuat employee baru melalui menu PIM")
    public void userMembuatEmployeeBaruMelaluiMenuPIM() {
        String suffix = String.valueOf(System.currentTimeMillis());
        String firstName = "Auto";
        String lastName = "Employee" + suffix;
        createdEmployeeName = firstName + " " + lastName;
        createdEmployeeLastName = lastName;

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='PIM']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Add Employee']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstName"))).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName"))).sendKeys(lastName);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Save']"))).click();
        wait.until(ExpectedConditions.urlContains("/pim/viewPersonalDetails"));
    }

    @And("user memilih employee baru yang sudah dibuat")
    public void userMemilihEmployeeBaruYangSudahDibuat() {
        WebElement employeeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Employee Name']/../following-sibling::div//input")
        ));
        employeeInput.sendKeys(createdEmployeeLastName);

        By createdEmployeeOption = By.xpath(
                "//div[contains(@class,'oxd-autocomplete-option') and contains(.,'" + createdEmployeeLastName + "')]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(createdEmployeeOption)).click();
    }

    @And("user memilih status {string}")
    public void userMemilihStatus(String status) {
        selectDropdownByLabel("Status", status);
    }

    @And("user mengisi username user baru dengan prefix {string}")
    public void userMengisiUsernameUserBaruDenganPrefix(String prefix) {
        createdUsername = prefix + "_" + System.currentTimeMillis();
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Username']/../following-sibling::div//input")
        ));
        usernameInput.clear();
        usernameInput.sendKeys(createdUsername);
    }

    @And("user mengisi password user baru {string}")
    public void userMengisiPasswordUserBaru(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Password']/../following-sibling::div//input")
        ));
        WebElement confirmPasswordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Confirm Password']/../following-sibling::div//input")
        ));

        passwordInput.clear();
        passwordInput.sendKeys(password);
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(password);
    }

    @And("user menekan tombol Save")
    public void userMenekanTombolSave() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Save']"))).click();
    }

    @Then("user baru berhasil disimpan")
    public void userBaruBerhasilDisimpan() {
        wait.until(ExpectedConditions.urlContains("/admin/viewSystemUsers"));

        WebElement usernameSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Username']/../following-sibling::div//input")
        ));
        usernameSearch.clear();
        usernameSearch.sendKeys(createdUsername);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Search']"))).click();

        By createdUserCell = By.xpath("//div[@role='row']//div[@role='cell']//div[text()='" + createdUsername + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(createdUserCell));
    }

    private void selectDropdownByLabel(String label, String option) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[text()='" + label + "']/../following-sibling::div//div[contains(@class,'oxd-select-text')]")
        ));
        dropdown.click();

        By optionLocator = By.xpath("//div[@role='option']//span[normalize-space()='" + option + "']");
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
