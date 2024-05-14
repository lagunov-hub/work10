package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {

        WebDriverManager.chromedriver().setup(); //.safaridriver().setup();

    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

//        driver = new SafariDriver();
        driver.get("http://localhost:9999");
    }



    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
        System.out.println("QUIT");
    }

    @Test
    void badPhoneTest() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Василий");
        elements.get(1).sendKeys("+7927");
        elements.get(1).submit();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText();
        System.out.println(text);
        assertEquals("Телефон указан неверно", text.trim().substring(0,22));

    }

    @Test
    void shouldTestV1() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Василий");
        elements.get(1).sendKeys("+79270000000");

//        var check = driver.findElement(By.className("checkbox__box"));
        var check = driver.findElement(By.cssSelector("[data-test-id=agreement]"));
        //WebElement check = driver.findElement(By.cssSelector(".checkbox input"));
        new Actions(driver)
                .moveToElement(check)
                .click(check)
                .perform();

        WebElement form = driver.findElement(By.className("form"));
        form.submit();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String text = driver.findElement(By.className("paragraph")).getText();
        System.out.println(text);assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }




}

