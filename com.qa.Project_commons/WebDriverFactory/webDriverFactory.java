package WebDriverFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import Utility.ConfigUtility;
import Utility.WaitUtility;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class webDriverFactory {

	public static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();
	public static ThreadLocal<WebDriver> mobileThreadLocal = new ThreadLocal<WebDriver>();

	public static webDriverFactory _webDriverFactory;

	public static webDriverFactory getInstance() {
		if (_webDriverFactory == null) {
			_webDriverFactory = new webDriverFactory();
		}
		return _webDriverFactory;

	}

	public static void setMobileWebDriver(String browserName) throws IOException, InterruptedException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		switch (browserName.toLowerCase()) {
		case "android-chrome":
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigUtility
					.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "android_device_name"));
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
			capabilities.setCapability(AndroidMobileCapabilityType.AVD,
					ConfigUtility.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "avd_name"));
			capabilities.setCapability(AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT, 180000);
			threadLocal.set(new AppiumDriver<>(new URL(
					ConfigUtility.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "appiumURL")),
					capabilities));
			WaitUtility.FixWait(10);
			break;

		case "ios-safari":
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigUtility
					.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "ios_device_name"));
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.IOS);
			capabilities.setCapability(MobileCapabilityType.UDID,
					ConfigUtility.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "UDID"));
			threadLocal.set(new AppiumDriver<>(new URL(
					ConfigUtility.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "appiumURL")),
					capabilities));
			WaitUtility.FixWait(10);
			break;

		}

	}

	public static void setDesktopWebDriver() throws MalformedURLException, IOException, InterruptedException {
		ChromeOptions chromeOptions = new ChromeOptions();
		DesiredCapabilities chromeCapability = new DesiredCapabilities();
		chromeOptions.addArguments("--disable-extensions");
		chromeOptions.addArguments("--incognito");
		chromeOptions.addArguments("start-maximized");
		chromeOptions.addArguments("--allow-running-insecure-content");
		chromeOptions.addArguments("--disable-infobars");

		chromeCapability.setBrowserName("chrome");
		chromeCapability.setPlatform(Platform.WINDOWS);
		chromeCapability.acceptInsecureCerts();
		chromeCapability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		chromeCapability.setVersion(" 80.0.3987.116");

		switch (ConfigUtility.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "DriverToUse")) {

		case "Remote":
			threadLocal.set(
					new RemoteWebDriver(new URL(ConfigUtility.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "HubURL")), chromeCapability));
			break;

		case "Local":
			WebDriverManager.chromedriver().setup();
			WebDriver chromeDriver = new ChromeDriver(chromeOptions);
			threadLocal.set(chromeDriver);
			break;

		}
		threadLocal.get().manage().window().maximize();
		WaitUtility.FixWait(10);
		threadLocal.get().get("https://saucedemo.com/");
		

	}

	public static void setAndroidDriver(Boolean noReset) throws IOException, InterruptedException {
		String appPackage = "com.swaglabsmobileapp";
		String appActivity = "com.swaglabsmobileapp.MainActivity";
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability("appActivity", appActivity);
		capability.setCapability("appPackage", appPackage);
		capability.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigUtility
				.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "android_device_name"));
		capability.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
		capability.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
		capability.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 180000);
		capability.setCapability(AndroidMobileCapabilityType.AVD,
				ConfigUtility.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "avd_name"));
		capability.setCapability(AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT, 180000);
		String appName = ConfigUtility.getConfigProperty("/com.qa.Project_commons/TestData/config.properties",
				"android_APP");
		String app = System.getProperty("user.dir") + appName;
		capability.setCapability(MobileCapabilityType.APP, app);
		capability.setCapability(MobileCapabilityType.NO_RESET, noReset);
		threadLocal.set(new AppiumDriver<>(new URL(
				ConfigUtility.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "appiumURL")),
				capability));
		WaitUtility.FixWait(10);
	}

	public static void setIOSDriver(Boolean noReset) throws IOException, InterruptedException {
		DesiredCapabilities iosCapabilities = new DesiredCapabilities();
		iosCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
		iosCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.IOS);
		iosCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigUtility
				.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "ios_device_name"));
		iosCapabilities.setCapability(MobileCapabilityType.NO_RESET, noReset);
		iosCapabilities.setCapability(MobileCapabilityType.APP,
				ConfigUtility.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "ios_APP"));
		iosCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, ConfigUtility
				.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "ios_platform_version"));
		iosCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "");
		iosCapabilities.setCapability(MobileCapabilityType.UDID,
				ConfigUtility.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "UDID"));
		iosCapabilities.setCapability("language", "en");
		iosCapabilities.setCapability("locale", "en_IND");
		threadLocal.set(new AppiumDriver<>(new URL(
				ConfigUtility.getConfigProperty("/com.qa.Project_commons/TestData/config.properties", "appiumURL")),
				iosCapabilities));
		WaitUtility.FixWait(10);

	}

	public WebDriver getIOSDriver() {
		return threadLocal.get();
	}

	public  WebDriver getAndroidDriver() {
		return threadLocal.get();
	}

	public  WebDriver getWebDriver() {
		return threadLocal.get();
	}

}
