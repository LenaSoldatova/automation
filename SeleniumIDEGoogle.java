package testAutomation;

import java.util.List;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import google.Gmail;
import org.openqa.jetty.html.Page;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumIDEGoogle  {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "C:/Users/Lena/Downloads/chromedriver_win32/chromedriver.exe");
    driver = new ChromeDriver();
      PageFactory.initElements(driver, this);
            //FirefoxDriver();
    baseUrl = "https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https://mail.google.com/mail/&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1#identifier";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      driver.get(baseUrl );
      login("mizyanya@gmail.com", "elenaWERE231");
  }

  //@FindBy(how = How.ID, using = "Email")
  @FindBy(how = How.ID, using =  "Email")
  @CacheLookup
  public WebElement email;

  @FindBy(how = How.ID, using = "next")
  @CacheLookup
  public WebElement nextBtnLogin;

  @FindBy(id = "Passwd")
  @CacheLookup
  public WebElement pass;

  @FindBy(id = "signIn")
  @CacheLookup
  public WebElement login;

  //@Override
  public void login(final String userEmail, String password) {
      email.sendKeys(userEmail);
      nextBtnLogin.click();
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      pass.sendKeys(password);
      login.click();
     /* driver.findElement(By.id("Email")).sendKeys(userEmail);
      driver.findElement(By.id("next")).click();
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      driver.findElement(By.id("Passwd")).sendKeys(password);
      driver.findElement(By.id("signIn")).click();
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);*/
  }

    @Test (description = "Test Settings", enabled = false)
  public void testSeleniumIDEGoogle() throws Exception {
    Actions builder = new Actions(driver);
    String className;


    WebElement settingsBTN =  driver.findElement(By.xpath(".//div[@gh='s']/div"));
    className = settingsBTN.getAttribute("class");
    settingsBTN.click();

    Assert.assertEquals(settingsBTN.getAttribute("class"), "T-I J-J5-Ji ash T-I-ax7 L3 T-I-JW T-I-JO T-I-Kq");
    Assert.assertEquals(settingsBTN.getAttribute("aria-expanded"), "true");

    WebElement settingsDropDown =  driver.findElement(By.xpath(".//div[@class='J-M asi aYO jQjAxd']"));
    Assert.assertTrue(settingsDropDown.isDisplayed());


    List<WebElement> settingsListOfElements = driver.findElements(By.xpath(".//div[@class='J-M asi aYO jQjAxd']/div['SK AX']/div"));
    //System.out.println(listOfElements.size());
        for(WebElement element: settingsListOfElements){
            if ((!element.getText().trim().equals(""))&&((!element.getText().trim().equals("Интерфейс:")))) {
                className = element.getAttribute("class");
                builder.moveToElement(element).build().perform();
                Assert.assertTrue(element.getAttribute("class").equals(className+" J-N-JT"));
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            }
        }
        settingsBTN.click();
  }
    @Test(description = "Test Gmail Pagination" , enabled = true)
    public void testGmailPagination() throws InterruptedException {

        int page=50;
        int from;
        int to;
        int total;
        String str;
        WebElement pagesSpan =  driver.findElement(By.xpath(".//span[@class='Di']"));
        //System.out.println(pagesSpan.getText());
        str = pagesSpan.getText();



        total = getPage("total", str);
        int i =  total/page;

        from = getPage("from", str);
        Assert.assertEquals(from, 1);
        to = getPage("to", str);
        Assert.assertEquals(to, page);

        WebElement nextPapeBTN =  driver.findElement(By.xpath(".//span[@class='Di']/div[3]"));
        nextPapeBTN.click();
        Thread.sleep(1000);
        WebElement pagesSpanNext =  driver.findElement(By.xpath(".//span[@class='Di']"));
        str = pagesSpanNext.getText();
        from = getPage("from", str);
        Assert.assertEquals(from, 1+page);
        to = getPage("to", str);
        Assert.assertEquals(to, page*2);


        //Assert.assertEquals();


    }

    public static int getPage(String page, String str)  {
        int firstChar;
        int secondChar;
        int from;
        int to;
        int total;
        firstChar = str.indexOf("–");
        secondChar = str.indexOf("из");
        if (page.equals("from")){
            from = Integer.parseInt(str.substring(0, firstChar).trim());
            return from;
        }else if(page.equals("to")){
            to =  Integer.parseInt(str.substring(firstChar+1, secondChar).trim());
            return to;
        }else{
            total = Integer.parseInt(str.substring(secondChar+2).trim());
            return total;
        }

    }



    @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
   // driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
