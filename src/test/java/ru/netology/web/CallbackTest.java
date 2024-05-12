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
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {

//        ChromeDriverService service = new ChromeDriverService.Builder()
//                .usingDriverExecutable(new File("/Users/segolov/Downloads/chromedriver-mac-x64/chromedriver"))
////                .usingChromeDriverExecutable(new File("path/to/my/chromedriver"))
////                .usingPort(9515)
//                .usingAnyFreePort()
//                .build();
//        try {
//            service.start();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        WebDriverManager.safaridriver().setup();
    //chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--headless");
//        driver = new ChromeDriver(options);


        driver = new SafariDriver();

        driver.get("http://localhost:9999");
    }



    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
        System.out.println("QUIT");
    }

    @Test
    void shouldTestV1() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Василий");
        elements.get(1).sendKeys("+79270000000");
        var check = driver.findElement(By.className("checkbox__box"));
        System.out.println("WAS "+check.isSelected());
        driver.findElement(By.className("checkbox__box")).click();
        System.out.println("NOW "+check.isSelected());
        driver.findElement(By.className("button")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String text = driver.findElement(By.className("paragraph")).getText();
        System.out.println(text);
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

   // @Test
    void shouldTestV2() {
//        WebElement form = driver.findElement(By.cssSelector("[data-test-id=callback-form]"));
        WebElement form = driver.findElement(By.className("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
//        form.findElement(By.cssSelector("[data-test-id=submit]")).click();
        String text = driver.findElement(By.className("alert-success")).getText();
        assertEquals("Ваша заявка успешно отправлена!", text.trim());
    }
}

