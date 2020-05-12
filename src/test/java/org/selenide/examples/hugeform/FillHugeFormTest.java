package org.selenide.examples.hugeform;

import com.codeborne.selenide.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.CHROME;
import static com.codeborne.selenide.WebDriverRunner.FIREFOX;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static java.util.Arrays.asList;

@RunWith(Parameterized.class)
public class FillHugeFormTest {
  @Parameterized.Parameters
  public static List<Object[]> names() {
    return asList(
        new Object[]{CHROME, "sendKeys"},
        new Object[]{CHROME, "fastSetValue"},
        new Object[]{FIREFOX, "sendKeys"},
        new Object[]{FIREFOX, "fastSetValue"}
    );
  }
  
  private final String method;

  public FillHugeFormTest(String browser, String method) {
    this.method = method;
    Configuration.browser = browser;
    Configuration.browserSize = "500x700";
    Configuration.browserPosition = "200x100";
    Configuration.fastSetValue = "fastSetValue".equals(method);
  }

  @Before
  public void setUp() {
    open(getClass().getResource("/huge_dynamic_form.html"));
  }

  @After
  public void tearDown() {
    closeWebDriver();
  }

  @Test
  public void fillHugeForm() {
    $(By.name("HEADER")).setValue(Configuration.browser + ", " + method + "():");
    $(By.name("FOOTER")).setValue(Configuration.browser + ", " + method + "():");

    long start = System.currentTimeMillis();
    for (int i = 0; i < 50; i++) {
      $(By.name("field["+i+"]")).setValue("Переяславль-Приозёрский " + i);
    }
    long end = System.currentTimeMillis();

    System.out.println(Configuration.browser + "  -->  " + method + ": " + (end-start) + " ms.");
  }
}
