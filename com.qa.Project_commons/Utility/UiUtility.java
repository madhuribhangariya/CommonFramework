package Utility;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import WebDriverFactory.webDriverFactory;

public class UiUtility {

	@Test
	@Parameters({ "initializeDriver", "noReset", "browserName" })
	public static void InitializeDriver( @Optional String initializeDriver, @Optional Boolean noReset, @Optional String browserName) {
		if (initializeDriver!=null) {
			try {
				switch (initializeDriver.toLowerCase()) {

				case "ios":
					webDriverFactory.setIOSDriver(noReset);
					break;

				case "webportal":
					webDriverFactory.setMobileWebDriver(browserName);
					break;

				case "android":
					webDriverFactory.setAndroidDriver(noReset);
					break;

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Test
	@Parameters({ "quitDriver" })
	public static void quitDriver(@Optional String quitDriver) {
		if (quitDriver!=null) {
			switch (quitDriver.toLowerCase()) {

			case "ios":
				webDriverFactory.getInstance().getIOSDriver().quit();

				break;

			case "webportal":
				webDriverFactory.getInstance().getWebDriver().quit();
				break;

			case "android":
				webDriverFactory.getInstance().getAndroidDriver().quit();
				break;

			}
		}

	}

}
