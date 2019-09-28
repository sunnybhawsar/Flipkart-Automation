package com.wp.automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.FindFailed;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
//import org.sikuli.script.Pattern;
//import org.sikuli.script.Screen;
import org.testng.annotations.Test;

public class Flipkart {

	public WebDriver chromeDriver;
	public String rootDir = System.getProperty("user.dir");

// Method - Open Browser

	@BeforeTest
	public void openFlipkart() throws InterruptedException, IOException {
		
		
		// For linux
		//System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
		
		// For Windows
		System.setProperty("webdriver.chrome.driver", rootDir+"/Driver/chromedriver.exe");

		chromeDriver = new ChromeDriver();

		String URL = Configuration.getConfig("Environment");

		chromeDriver.get(URL);

		chromeDriver.manage().window().maximize();

		chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		chromeDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		Thread.sleep(3000);

							// Tried JavascriptExecutor
					
							/*
							 * JavascriptExecutor jse = (JavascriptExecutor) chromeDriver;
							 * 
							 * jse.executeScript("window.open()");
							 */

	}

// Method - Login

	@Test(priority = 1)
	public void login() throws InterruptedException, FindFailed, IOException {
		Thread.sleep(2000);
		
		FileInputStream fileInputStream = new FileInputStream(rootDir+"/assets/LoginCredentials.xlsx");

			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
	
			XSSFSheet sheet = workbook.getSheet("loginData");
	
			XSSFRow row = sheet.getRow(0);
	
			String userName = row.getCell(0).getStringCellValue();
	
			String password = row.getCell(1).getStringCellValue();

		Thread.sleep(2000);

		chromeDriver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div/form/div[1]/input")).sendKeys(userName);

		chromeDriver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div/form/div[2]/input")).sendKeys(password);

		chromeDriver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div/form/div[3]/button")).click();

							// Tried Sikuli
					
							/*
							 * Screen screen = new Screen();
							 * 
							 * Pattern loginButton = new Pattern("/home/sunny/Downloads/loginPic.png");
							 * 
							 * screen.click(loginButton);
							 */
	}
	
// Method - search

	@Test(priority = 2)

	@Parameters({ "product" })
	public void searchItems(String product) throws InterruptedException 
	{
		Thread.sleep(3000);
		chromeDriver.findElement(By.xpath("//input[@name='q']")).sendKeys(product);

		chromeDriver.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div[2]/div[2]/form/div/button")).click();
	}
	

// Method - select item

	@Test(priority = 3)
	public void selectItem() throws InterruptedException 
	{
		Thread.sleep(3000);
		chromeDriver.findElement(By.xpath("//a[@class='_2mylT6'][1]")).click();

		ArrayList<String> tabs = new ArrayList<String>(chromeDriver.getWindowHandles());

		chromeDriver.switchTo().window(tabs.get(1));
	}

	
// Method - add to cart
	
	@Test (priority=4) public void addToCart() throws InterruptedException, IOException 
	{
	  Thread.sleep(3000); 
	  
	  WebElement webElement = chromeDriver.findElement(By.xpath("//button[@class=\"_2AkmmA _2Npkh4 _2MWPVK\"]"));
	  
	  Actions actions = new Actions(chromeDriver);
	  
	  Action builde = actions.click(webElement).build();
	  
	  builde.perform(); 
	  
	  Thread.sleep(2000);
	  
	  	TakesScreenshot takesScreenshot = (TakesScreenshot)chromeDriver;
		
		File file = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		FileUtils.copyFile(file, new File(rootDir+"/Screenshots/Cart.png"));
		
		System.out.println("Screenshot Captured!");
	 }

	
// Method - placeOrder
	
	@Test(priority = 5)
	public void placeOrder() throws InterruptedException 
	{
		Thread.sleep(3000);
		chromeDriver.findElement(By.xpath("//button[@class='_2AkmmA iwYpF9 _7UHT_c']")).click();
	}
	

// Method - orderSummary

	@Test(priority = 6)
	public void orderSummary() throws InterruptedException 
	{
		Thread.sleep(3000);
		chromeDriver.findElement(By.xpath("//button[@class='wNrY5O'][2]")).click();

		Thread.sleep(4000);
		chromeDriver.findElement(By.xpath("//button[@class='_2AkmmA _2Q4i61 _7UHT_c']")).click();
	}
	
	
// Method - select payment mode

	@Test(priority = 7)
	public void selectPaymentMode() throws InterruptedException 
	{
		Thread.sleep(3000);
		chromeDriver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[1]/div[4]/div/div/div[1]/div/label[4]/div[2]/div/div/div[1]")).click();
	}
	

// Method - Screenshot

		@AfterTest
		public void screenShot() throws InterruptedException, IOException 
		{
			Thread.sleep(2000);
			
			TakesScreenshot takesScreenshot = (TakesScreenshot)chromeDriver;
			
			File file = takesScreenshot.getScreenshotAs(OutputType.FILE);
			
			FileUtils.copyFile(file, new File(rootDir+"/Screenshots/Screenshot.png"));
			
			System.out.println("Screenshot Captured!");
		
		}
		
}
