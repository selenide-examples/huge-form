package org.selenide.examples.hugeform;

import com.codeborne.selenide.SelenideConfig;
import com.codeborne.selenide.SelenideDriver;
import org.openqa.selenium.By;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.sleep;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class Demo implements Runnable {
  private final SelenideDriver driver;

  public Demo(SelenideDriver driver) {
    this.driver = driver;
  }

  @Override
  public void run() {
    driver.open(getClass().getResource("/huge_dynamic_form.html"));

    driver.$(By.name("FOOTER")).setValue("fastSetValue = " + driver.config().fastSetValue());
    driver.$(By.name("HEADER")).setValue("fastSetValue = " + driver.config().fastSetValue());
    sleep(1000);

    for (int i = 0; i < 50; i++) {
      driver.$(By.name("field["+i+"]")).setValue("Переяславль-Приозёрский " + i);
    }
  }

  public static void main(String[] args) throws InterruptedException {
    SelenideDriver browser1 = new SelenideDriver(new SelenideConfig()
        .browser("chrome").browserPosition("100x100").browserSize("500x700").fastSetValue(false)
    );
    SelenideDriver browser2 = new SelenideDriver(new SelenideConfig()
        .browser("chrome").browserPosition("650x100").browserSize("500x700").fastSetValue(true)
    );

    ExecutorService pool = newFixedThreadPool(2);
    pool.submit(new Demo(browser1));
    pool.submit(new Demo(browser2));
    pool.shutdown();
    pool.awaitTermination(1, TimeUnit.MINUTES);

    sleep(4000);
    browser2.close();
    browser1.close();
  }
}
