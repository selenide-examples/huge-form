package org.selenide.examples.hugeform;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static java.util.Objects.requireNonNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.Configuration;

public class FillHugeFormTest {

  private static final Logger log = LoggerFactory.getLogger(FillHugeFormTest.class);

  @Test
  void chrome_sendKeys() {
   fillHugeForm("chrome", "sendKeys");
  }

  @Test
  void chrome_fastSetValue() {
    fillHugeForm("chrome", "fastSetValue");
  }

  @Test
  void firefox_sendKeys() {
    fillHugeForm("firefox", "sendKeys");
  }

  @Test
  void firefox_fastSetValue() {
    fillHugeForm("firefox", "fastSetValue");
  }

  private void init(String browser, String method) {
    Configuration.browser = browser;
    Configuration.browserSize = "500x700";
    Configuration.browserPosition = "200x100";
    Configuration.fastSetValue = "fastSetValue".equals(method);
    open(requireNonNull(getClass().getResource("/huge_dynamic_form.html")));
  }

  private void fillHugeForm(String browser, String method) {
    init(browser, method);

    $(By.name("HEADER")).setValue(Configuration.browser + ", " + method + "():");
    $(By.name("FOOTER")).setValue(Configuration.browser + ", " + method + "():");

    long start = System.currentTimeMillis();
    for (int i = 0; i < 50; i++) {
      $(By.name("field["+i+"]")).setValue("Переяславль-Приозёрский " + i);
    }
    long end = System.currentTimeMillis();

    log.info("{}  -->  {}: {} ms.", browser, method, end - start);
  }

  @BeforeEach
  @AfterEach
  public void tearDown() {
    closeWebDriver();
  }
}
