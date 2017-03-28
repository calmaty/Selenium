package test;

import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarsTest1 {

    private static final int WAIT_MAX = 4;
    static WebDriver driver;

    @BeforeClass
    public static void setup() {
        /*########################### IMPORTANT ######################*/
 /*## Change this, according to your own OS and location of driver(s) ##*/
 /*############################################################*/
        //System.setProperty("webdriver.gecko.driver", "C:\\diverse\\drivers\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Legendslayer\\Desktop\\chromedriver.exe");

        //Reset Database
        com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");
        driver = new ChromeDriver();
        driver.get("http://localhost:3000");
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
        //Reset Database 
        com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");
    }

    @Test
    //Verify that page is loaded and all expected data are visible
    public void test1() throws Exception {
        (new WebDriverWait(driver, WAIT_MAX)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
            WebElement e = d.findElement(By.tagName("tbody"));
            List<WebElement> rows = e.findElements(By.tagName("tr"));
            Assert.assertThat(rows.size(), is(5));
            return true;
        });
    }

    @Test
    public void test2() throws Exception {
        WebElement element = driver.findElement(By.id("filter"));
        element.sendKeys("2002");
        WebElement e = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = e.findElements(By.tagName("tr"));
        Assert.assertThat(rows.size(), is(2));

    }

    @Test
    public void test3() throws Exception {
        WebElement element = driver.findElement(By.id("filter"));
        element.sendKeys(Keys.BACK_SPACE);
        WebElement e = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = e.findElements(By.tagName("tr"));
        Assert.assertThat(rows.size(), is(5));

    }

    @Test
    public void test4() throws Exception {
        boolean yes = false;
        WebElement element = driver.findElement(By.id("h_year"));
        element.click();
        WebElement e = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = e.findElements(By.tagName("tr"));
        int i = driver.findElements(By.xpath("//tbody/tr[1 and td ='938']")).size();
        int j = driver.findElements(By.xpath("//tbody/tr[5 and td ='940']")).size();
        if (i == 1 && j == 1) {
            yes = true;
        }

        Assert.assertEquals(true, yes);

    }

    @Test
    public void test5() throws Exception {
        WebElement row = driver.findElement(By.xpath("//tbody/tr[td='938']"));
        WebElement a = row.findElements(By.tagName("a")).get(0);
        a.click(); //Click the tag
        boolean yes = false;
        WebElement element = driver.findElement(By.id("description"));
        element.clear();
        //element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys("cool car");
        WebElement saveElement = driver.findElement(By.id("save"));
        saveElement.click();

        List<WebElement> checkRow = driver.findElements(By.xpath("//tbody/tr[td='938']/td"));
        String text = checkRow.get(5).getText();
        //int i = driver.findElements(By.xpath("//tbody/tr[td='938']")).size();

        Assert.assertEquals(text, "cool car");

    }

    @Test
    public void test6() throws Exception {

        WebElement newCar = driver.findElement(By.id("new"));
        newCar.click();

        WebElement saveElement = driver.findElement(By.id("save"));
        saveElement.click();

        WebElement submitter = driver.findElement(By.id("submiterr"));
        String result = submitter.getText();

        WebElement e = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = e.findElements(By.tagName("tr"));

        Assert.assertEquals(result, "All fields are required");
        Assert.assertThat(rows.size(), is(5));
    }

    @Test
    public void test7() throws Exception {
        WebElement newCar = driver.findElement(By.id("new"));
        newCar.click();
        WebElement element = driver.findElement(By.id("year"));
        element.sendKeys("2008");
        WebElement registered = driver.findElement(By.id("registered"));
        registered.sendKeys("2002-5-5");
        WebElement make = driver.findElement(By.id("make"));
        make.sendKeys("Kia");
        WebElement model = driver.findElement(By.id("model"));
        model.sendKeys("Rio");
        WebElement description = driver.findElement(By.id("description"));
        description.sendKeys("As new");
        WebElement price = driver.findElement(By.id("price"));
        price.sendKeys("31000");
        WebElement saveElement = driver.findElement(By.id("save"));
        saveElement.click();

        WebElement e = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = e.findElements(By.tagName("tr"));
        Assert.assertThat(rows.size(), is(6));

    }

}
