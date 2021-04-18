package tests;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helper.AttachmentHelper.attachAsText;
import static helper.AttachmentHelper.attachPageSource;
import static helper.AttachmentHelper.attachScreenshot;
import static helper.AttachmentHelper.attachVideo;
import static helper.AttachmentHelper.getConsoleLogs;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestBase {
  @BeforeAll
  static void setup() {
    addListener("AllureSelenide", new AllureSelenide());
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("enableVNC", true);
    capabilities.setCapability("enableVideo", true);
    Configuration.browserCapabilities = capabilities;
    Configuration.browser = System.getProperty("web.browser", "chrome");

    Configuration.startMaximized = true;
    String remoteWebDriver = System.getProperty("remote.web.driver");

    if(remoteWebDriver != null) {
      Configuration.remote = remoteWebDriver;
    }
  }

  @AfterEach
  void afterEach() {
    attachScreenshot("Last screenshot");
    attachPageSource();
    attachAsText("Browser console logs", getConsoleLogs());
    if(System.getProperty("video.storage") != null)
      attachVideo();
    closeWebDriver();
  }
}
