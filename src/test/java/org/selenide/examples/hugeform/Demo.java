package org.selenide.examples.hugeform;

import static com.codeborne.selenide.Selenide.sleep;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.concurrent.ExecutorService;

import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideConfig;
import com.codeborne.selenide.SelenideDriver;

public class Demo implements Runnable {
  private final SelenideDriver driver;

  public Demo(SelenideDriver driver) {
    this.driver = driver;
  }

  @Override
  public void run() {
    driver.open(requireNonNull(getClass().getResource("/huge_dynamic_form.html")));

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
    pool.awaitTermination(1, MINUTES);

    sleep(4000);
    browser2.close();
    browser1.close();
  }
}
