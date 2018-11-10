import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumTest {

    private static WebDriver driver;
    private static Actions action;
    private static String RESOURCES = "RESOURCES";
    private static String SUBJECTS = "SUBJECTS";
    private static String ABOUT = "ABOUT";
    private static String EDUCATION = "EDUCATION";
    private static String RELATED_CONTENT = "Related Content";
    private static String RESOURCES_ITEM2 = "E-L";
    private static String Education = "Education";
    private static String[] submenu = new String [11];
    private static String[] leftPanel = new String [13];
    private static String STUDENTS_URL = "https://www.wiley.com/en-ru/students";
    private static String LINK_URL_TEST_3 = "http://wileyplus.wiley.com/";
    private static String STUDENTS_HEADER = "Students";
    private static String HOME_URL = "https://www.wiley.com/en-ru";
    private static List<String> searchResultStep8;

    @BeforeClass
    public static void setup() {

        submenu[0] = "STUDENTS";
        submenu[1] = "INSTRUCTORS";
        submenu[2] = "RESEARCHERS";
        submenu[3] = "PROFESSIONALS";
        submenu[4] = "LIBRARIANS";
        submenu[5] = "INSTITUTIONS";
        submenu[6] = "AUTHORS";
        submenu[7] = "RESELLERS";
        submenu[8] = "CORPORATIONS";
        submenu[9] = "SOCIETIES";
        submenu[10] = "JOURNALISTS";

        leftPanel[0] = "Information & Library Science";
        leftPanel[1] = "Education & Public Policy";
        leftPanel[2] = "K-12 General";
        leftPanel[3] = "Higher Education General";
        leftPanel[4] = "Vocational Technology";
        leftPanel[5] = "Conflict Resolution & Mediation (School settings)";
        leftPanel[6] = "Curriculum Tools- General";
        leftPanel[7] = "Special Educational Needs";
        leftPanel[8] = "Theory of Education";
        leftPanel[9] = "Education Special Topics";
        leftPanel[10] = "Educational Research & Statistics";
        leftPanel[11] = "Literacy & Reading";
        leftPanel[12] = "Classroom Management";

        searchResultStep8 = new ArrayList<String>();
        System.setProperty("webdriver.chrome.driver",
                "D:/programs/java/plugins/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://www.wiley.com/WileyCDA/");
        driver.findElement(By.xpath("//button[@class='changeLocationCancelBtn button large button-dark-gray']")).click();
        action = new Actions(driver);

    }

    @AfterClass
    public static void finish() {
        driver.quit();
    }

    /*
    Test aims to check the following Resources, Subjects, About are displayed in the top menu
     */
    @Test
    public void test1() {

        WebElement resources = driver.findElement(By.xpath("//a[@href='#']"));
        Assert.assertTrue(resources.isDisplayed());
        Assert.assertEquals(resources.getText(), RESOURCES);

        WebElement subject = driver.findElement(By.xpath("//a[@href='/en-ru/subjects']"));
        Assert.assertTrue(subject.isDisplayed());
        Assert.assertEquals(subject.getText(), SUBJECTS);

        WebElement about = driver.findElement(By.xpath("//a[@href='/en-ru/aboutus']"));
        Assert.assertTrue(about.isDisplayed());
        Assert.assertEquals(about.getText(), ABOUT);

    }

    /*
    Test to check items under Resources for sub-header
    NOTE: in the task is written about 10 items however in the site Resources sub-header has 11 items
     (Journalists item is missed). This test is written to check the presence of all 11 items
     */
    @Test
    public void test2() {

        List<WebElement> list = driver.findElements(By.xpath("//div[@id='navigationNode_00000RS2']/div"));
        Assert.assertEquals(list.size(), 11);

        WebElement resources = driver.findElement(By.id("navigationNode_00000RS2"));
        action.moveToElement(resources).perform();
        for (int i = 0; i < submenu.length; i++) {
            WebElement item = driver.findElement(By.linkText(submenu[i]));
            Assert.assertTrue(item.isDisplayed());
        }

    }

    /*
    Test to check Students submenu
     */
    @Test
    public void test3() {

        WebElement resources = driver.findElement(By.xpath("//a[@href='#']"));
        action.moveToElement(resources);
        WebElement students = driver.findElement(By.linkText("STUDENTS"));
        students.click();
        Assert.assertEquals(driver.getCurrentUrl(), STUDENTS_URL);

        WebElement header = driver.findElement(By.xpath("//div[@class='content cke-content']/p[@class='sg-title-h1']"));
        Assert.assertTrue(header.isDisplayed());
        Assert.assertEquals(header.getText(), STUDENTS_HEADER);

        WebElement link = driver.findElement(By.linkText("WileyPLUS"));
        Assert.assertEquals(link.getAttribute("href"), LINK_URL_TEST_3);

    }

    /*
    Test to check Subject submenu, Education page
     */
    @Test
    public void test4() {

        WebElement subject = driver.findElement(By.xpath("//ul[@class='navigation-menu-items']/li/a[@href='/en-ru/subjects']"));
        try {
            action.moveToElement(subject).perform();
        } catch (StaleElementReferenceException  e) {
            action.moveToElement(subject).perform();
        }
        WebElement item2 = driver.findElement(By.linkText(RESOURCES_ITEM2));
        try {
            action.moveToElement(item2).perform();
        } catch (StaleElementReferenceException  e) {
            action.moveToElement(item2).perform();
        }
        WebElement education = driver.findElement(By.linkText(EDUCATION));
        education.click();

        WebElement header = driver.findElement(By.xpath("//article[@class='section-description page-section']" +
                                                "/div/div/h1[@style='text-align: justify;']/span"));
        Assert.assertTrue(header.isDisplayed());
        Assert.assertEquals(header.getText(), Education);

        Point subjectPoint = driver.findElement(By.xpath("//div[@class='side-panel']" +
                                                "/header/div[@class='content cke-content']/h4")).getLocation();
        for (int i = 0; i < leftPanel.length; i++) {
            WebElement item = driver.findElement(By.linkText(leftPanel[i]));
            Assert.assertTrue(item.isDisplayed());
            Assert.assertTrue(subjectPoint.y < item.getLocation().y);
        }

    }

    /*
    test to check clickable logo
     */
    @Test
    public void test5() {

        WebElement icon = driver.findElement(By.xpath("//div[@class='yCmsContentSlot logo']" +
                                            "/div[@class='simple-responsive-banner-component']/a"));
        icon.click();
        Assert.assertEquals(driver.getCurrentUrl(), HOME_URL);

    }

    /*
    Test to check the result in case of search without any word
     */
    @Test
    public void test6() {
        WebElement search = driver.findElement(By.xpath("//form[@name='search_form_SearchBox']/div/span/button"));
        search.submit();
        Assert.assertEquals(driver.getCurrentUrl(), HOME_URL);
    }

    /*
    Test to check popup menu when "Math" is typed in the Search input

    One check aimed to verify that
    - Area with related content is displayed right under the search header
    was not done. Element id = 'ui-id-2' can not correctly define the position
     */
    @Test
    public void test7() {
        driver.findElement(By.xpath("//form[@name='search_form_SearchBox']/div/input")).sendKeys("Math");
        WebElement inputArea = driver.findElement(By.xpath("//form[@name='search_form_SearchBox']/" +
                "div[@class='input-group']"));
        WebElement resultArea = driver.findElement(By.xpath("//form[@name='search_form_SearchBox']/" +
                "div[@id='ui-id-2']"));
        /*
        Assert.assertEquals(inputArea.getLocation().getX(), resultArea.getLocation().getX());
        Assert.assertEquals(inputArea.getSize().getWidth(), resultArea.getSize().getWidth());
        Assert.assertEquals(inputArea.getLocation().getY() + inputArea.getSize().getHeight(),
                resultArea.getLocation().getY());
        */
        List<WebElement> leftPanelContent = driver.findElements(By.xpath("//div[@id='ui-id-2']/" +
                "div[@class='search-list']/div[@class='ui-menu-item']"));
        Assert.assertEquals(leftPanelContent.size(), 4);
        for (WebElement item : leftPanelContent)
            Assert.assertTrue(item.getText().startsWith("Math"));

        List<WebElement> rightPanelContent = driver.findElements(By.xpath("//div[@id='ui-id-2']/" +
                "div[@class='search-related-content']/section/div[@class='related-content-products']/section"));
        Assert.assertEquals(leftPanelContent.size(), 4);
        for (WebElement item : leftPanelContent)
            Assert.assertTrue(item.getText().startsWith("Math"));

        WebElement relatedContentHeader = driver.findElement(By.xpath("//div[@id='ui-id-2']/div[@class='search-related-content']" +
                "/section/header/h5"));
        Assert.assertEquals(relatedContentHeader.getText(), RELATED_CONTENT);

        List<WebElement> relatedContent = driver.findElements(By.xpath("//div[@class='related-content-products']/section/" +
                "div/div[@class='product-description']"));
        Assert.assertEquals(relatedContent.size(), 4);
        for (WebElement item : relatedContent) {
            Assert.assertTrue(item.getText().contains("Math"));
            Assert.assertTrue(item.getLocation().y > relatedContentHeader.getLocation().y);
        }
    }

    /*
    Test to check Search results when 'Math' was typed.
     NOTE: According to the task "Only titles containing “Math” are displayed" - it is not exactly correct. Results with
      "Math" in the field Author also displayed.
     */
    @Test
    public void test8() {

        driver.findElement(By.xpath("//form[@name='search_form_SearchBox']/div/input")).clear();
        driver.findElement(By.xpath("//form[@name='search_form_SearchBox']/div/input")).sendKeys("Math");
        driver.findElement(By.xpath("//form[@name='search_form_SearchBox']/div/span/button")).submit();

        List<WebElement> searchResults = driver.findElements(By.xpath("//div[@class='products-list']/section/" +
                "div[@class='product-content']"));
        Assert.assertEquals(searchResults.size(), 10);
        for (WebElement item : searchResults)
            Assert.assertTrue(item.getText().contains("Math"));

        List<WebElement> results = driver.findElements(By.xpath("//div[@class='products-list']/section/" +
                "div[@class='product-content']/div[@class='product-table-flexible']/div/div[@class='product-table-body']"));
        for (WebElement item : results)
            Assert.assertTrue(item.getText().contains("ADD TO CART"));

        // to check that each "ADD TO CARD" is a button
        List<WebElement> buttons = driver.findElements(By.xpath("//div[@class='product-button']/div/form/button"));
        Assert.assertEquals(buttons.size(), 15);

    }

    /*
    Test to check if search result got in step 8 will be the same for another search
     */
    @Test
    public void test9() {
        //Doing the same as for step 8
        driver.findElement(By.xpath("//form[@name='search_form_SearchBox']/div/input")).sendKeys("Math");
        driver.findElement(By.xpath("//form[@name='search_form_SearchBox']/div/span/button")).submit();
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='products-list']/section/" +
                "div[@class='product-content']"));
        for (WebElement item : list)
            searchResultStep8.add(item.getText());

        driver.findElement(By.xpath("//form[@name='search_form_SearchBox']/div/input")).sendKeys("Math");
        driver.findElement(By.xpath("//form[@name='search_form_SearchBox']/div/span/button")).submit();
        List<WebElement> searchResults = driver.findElements(By.xpath("//div[@class='products-list']/section/" +
                "div[@class='product-content']/h3"));
        for (int i = 0; i < searchResults.size(); i++) {
            Assert.assertTrue(searchResultStep8.get(i).contains(searchResults.get(i).getText()));
        }

    }

}
