package org.selenide.examples.hugeform;

import com.codeborne.selenide.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;

import java.net.URL;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.*;
import static java.util.Arrays.asList;

@RunWith(Parameterized.class)
public class FillHugeFormTest {
  @Parameterized.Parameters
  public static List<Object[]> names() {
    return asList(
        new Object[]{FIREFOX, "sendKeys"},
        new Object[]{FIREFOX, "setValue"},
        new Object[]{CHROME, "sendKeys"},
        new Object[]{CHROME, "setValue"}
//        new Object[]{HTMLUNIT, "sendKeys"},
//        new Object[]{HTMLUNIT, "setValue"}
    );
  }
  
  private final String method;

  public FillHugeFormTest(String browser, String method) {
    this.method = method;
    Configuration.browser = browser;
    Configuration.fastSetValue = "fastSetValue".equals(method);
  }

  @Before
  public void setUp() {
    URL page = getClass().getResource("/huge_dynamic_form.html");
    open(page);
  }

  @After
  public void tearDown() {
    closeWebDriver();
  }

  @Test
  public void fillHugeForm() {
    $(By.name("HEADER")).setValue(Configuration.browser + ", " + method + "():");

    long start = System.currentTimeMillis();
    for (int i = 0; i < 100; i++) {
      $(By.name("field["+i+"]")).setValue("Переяславль-Приозёрский");
    }
    long end = System.currentTimeMillis();

    System.out.println(Configuration.browser + "  -->  " + method + ": " + (end-start) + " ms.");
  }
}
