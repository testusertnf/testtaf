package com.tnf.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

import static org.junit.Assert.*;

public class Keywords {
	
	//public static Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"\\src\\com\\tnf\\xls\\Data.xlsx");
	public static Xls_Reader xls = new Xls_Reader("C:\\xls\\Data.xlsx");
	static Keywords keywords=null;
	public Properties CONFIG=null;
	public Properties OR=null;
	public WebDriver driver= null;
	
	
/*	private Keywords(){
		System.out.println("Initializing Keywords");
		// initialize properties files
		try {
			// config
			CONFIG = new Properties();
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\qtpselenium\\hybrid\\config\\config.properties");
			CONFIG.load(fs);
			// OR
			OR = new Properties();
			fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\qtpselenium\\hybrid\\config\\OR.properties");
			OR.load(fs);

		} catch (Exception e) {
			// Error is found
			e.printStackTrace();
		}
	}*/
	
	
	public void executeKeywords(String testName, Map<String,String> data, List<List<String>> rowList, StringBuffer tr_testData) throws InterruptedException, AWTException{
		boolean resultFlag = true;
		System.out.println("Executing - "+testName);
		
		// find the keywords for the test
		String keyword=null;
		String objectKey=null;
		String dataColVal=null;
		System.out.println("TestName :" + testName);
		for(int rNum=2;rNum<=xls.getRowCount("Test Steps");rNum++){
			String testStepsData = xls.getCellData("Test Steps", "TCID", rNum);
			
			if(testStepsData.contains(testName)&&xls.getCellData("Test Steps", "RunMode", rNum).equals("Y")){
			 List<String> rowData = new ArrayList<String>();
			 keyword=xls.getCellData("Test Steps", "Keyword", rNum);
			 objectKey=xls.getCellData("Test Steps", "Object", rNum);
			 dataColVal=xls.getCellData("Test Steps", "Data", rNum);
			 
			 System.out.println("Keyword : " + keyword);
			 
			
			 
			 String result=null;
			// System.out.println(keyword +" -- "+objectKey+" -- "+ dataColName);
			 if(keyword.equals("openBrowser_navigate"))
				 result=openBrowser_navigate(dataColVal);
			 else if (keyword.equals("openBrowser_URL"))
				 result=openBrowser_URL("",dataColVal);
			 else if (keyword.equals("clickLink"))
				 result=clickLink(objectKey,data.get(dataColVal));
			 else if (keyword.equals("clickButton"))
				 result=clickButton(objectKey);
			 else if(keyword.equals("input"))
				 result=input(objectKey,data.get(dataColVal));
			/* else if(keyword.equals("navigate"))
				 result=navigate(dataColVal);*/
			/* else if(keyword.equals("verifyImage"))
				 result=verifyImage(objectKey);*/
			 else if(keyword.equals("verifyText"))
				 result=verifyText(objectKey,data.get(dataColVal));
			 /*else if(keyword.equals("verifyContent"))
				 result=verifyContent(objectKey);*/
			 else if(keyword.equals("select"))
				 result=select(objectKey,data.get(dataColVal));
			/* else if(keyword.equals("checkMail"))
				 result= checkMail(data.get(dataColVal));*/
			 else if (keyword.equals("CheckBox"))
				 result=CheckBox(objectKey);
			 else if (keyword.equals("RadioButton"))
				 result=RadioButton(objectKey);
			 else if (keyword.equals("WebGrid"))
				 result=WebGrid(objectKey);
			 else if (keyword.equals("verifyBreadCrumb"))
				 result=verifyBreadCrumb(objectKey);
			 else if (keyword.equals("Sync"))
				 result=waitThread();
			 else if (keyword.equals("clickLink_text"))
				 result=clickLink_text(objectKey,data.get(dataColVal));
			 else if (keyword.equals("verifyObject"))
				 result=verifyObject(objectKey);
			 else if (keyword.equals("popup"))
				 result=popup();
			 else if (keyword.equals("deletcookies"))
				 result=deletcookies();
			 else if (keyword.equals("closeBrowser"))
				 result=closeBrowser();
			 else if (keyword.equals("Robot_click"))
				 result=Robot_click();
			 else if (keyword.equals("excelinput"))
				 result=excelinput("","",1,data.get(dataColVal));
			 else if (keyword.equals("SwitchBrowser"))
				 result=SwitchBrowser(data.get(dataColVal));
			 else if (keyword.equals("file_upload"))
				 result=file_upload(data.get(dataColVal));
			 else if (keyword.equals("openBrowser_AutoSignIn"))
				 result=openBrowser_AutoSignIn("",dataColVal);
			 
			 
			 //excelinput(String sheetname,String colname,int rownum,String data)
			 
			 
		
			 
			 
			
			 /*else if(keyword.equals("verifyLogin"))
				 result=verifyLogin(data.get(dataColVal));*/
			 
			
			 
			 System.out.println(result);
			 rowData.add(xls.getCellData("Test Steps", "TCID", rNum));//Edited for HTML report 24/09
			 rowData.add(xls.getCellData("Test Steps", "Desciption", rNum));//Edited for HTML report 24/09
			 rowData.add(xls.getCellData("Test Steps", "ExpectedResult", rNum));
			 rowData.add(result.equals("Pass")?"PASS":"FAIL");//Edited for HTML report 24/09
			 if(!resultFlag || !result.equalsIgnoreCase("Pass")) {//Edited for HTML report 24/09
				 resultFlag = false;//Edited for HTML report 24/09
			 }//Edited for HTML report 24/09
			 
			 rowList.add(rowData);//Edited for HTML report 24/09
			 
			 if(!result.equals("Pass")){
				 
				 // screenshot
				 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				 String fileName=testName+"_"+keyword+".jpg";
				    try {
						FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+fileName));
					} catch (IOException e) {
						System.out.println("***ERR***");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
					// Assert.assertTrue(false, result);


			 }
			}
		}
		if(resultFlag) {//Edited for HTML report 24/09
			tr_testData.append("PASS");//Edited for HTML report 24/09
		} else {//Edited for HTML report 24/09
			tr_testData.append("FAIL");//Edited for HTML report 24/09
		}//Edited for HTML report 24/09
		
	
		//keywords.deletcookies();
		//keywords.closeBrowser();
		
		
	}
	
	public String closeBrowser()
	{
		try
		{
			driver.quit();	
		}
		catch(Exception e)
		{
			return "Fail";
		}
		
		return "Pass";
	}
	
	public String excelinput(String sheetname,String colname,int rownum,String data)
	{
		try
		{
			xls.setCellData(sheetname, colname,rownum,data);
		}
		catch(Exception e)
		{
			return "Fail";
		}
		
		return "Pass";
	}
	
	public String openBrowser_navigate(String browserType){
		/*System.out.println("Executing openBrowser");
		if(CONFIG.getProperty(browserType).equals("Mozilla"))
			driver= new FirefoxDriver();
		else if(CONFIG.getProperty(browserType).equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", "Path to chromedriver exe");
			driver= new ChromeDriver();
		}
		else if ((CONFIG.getProperty(browserType).equals("IE")))
			driver= new InternetExplorerDriver();
			
		return "Pass";*/
		
		try
		{
		Thread.sleep(2000);
		String Browertype = xls.getCellData("config", "Browertype", 2);
		//Condition to pick browserType
		if(Browertype.equals("Firefox")){
			driver = new FirefoxDriver();
		}else if(Browertype.equals("IE")){
			System.setProperty("webdriver.ie.driver", "C:\\softwares\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}else if(Browertype.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\softwares\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		
		String URL = xls.getCellData("config", "URL", 2);
		String Test = xls.getCellData("config", "Test", 2);
		String Live = xls.getCellData("config", "Live", 2);
		String QA = xls.getCellData("config", "QA", 2);
		String OEH = xls.getCellData("config", "OEH", 2);
		String ASA = xls.getCellData("config", "ASA", 2);
		String RSA = xls.getCellData("config", "RSA", 2);
		
		
	
		
		if(URL.equals("QA")){
			driver.get(QA);
		}else if(URL.equals("Test")){
			driver.get(Test);
		}else if(URL.equals("Live")){
			driver.get(Live);
		}else if(URL.equals("OEH")){
			driver.get(OEH);
		}else if(URL.equals("ASA")){
			driver.get(ASA);
		}else if(URL.equals("RSA")){
			driver.get(RSA);
		}
		
		driver.manage().window().maximize();
		}
		catch(Exception e)
		{
			return "Fail";
		}
		
		return "Pass";
		
	}
	
	public String openBrowser_URL(String browserType,String  data){
		
		try
		{
			Thread.sleep(2000);
		String Browertype = xls.getCellData("config", "Browertype", 2);
		//Condition to pick browserType
		if(Browertype.equals("Firefox")){
			driver = new FirefoxDriver();
		}else if(Browertype.equals("IE")){
			driver = new InternetExplorerDriver();
		}else if(Browertype.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\softwares\\chromedriver.exe");
			driver = new ChromeDriver();
		}
						
			driver.get(data);
		
		
		driver.manage().window().maximize();
		}
		catch(Exception e)
		{
			return "Fail";
		}
		
		return "Pass";
		
	
		
	}
	
public String openBrowser_AutoSignIn(String browserType,String  data) throws InterruptedException{
		
		try
		{
			Thread.sleep(2000);
				
			driver.get(data);
		
		
		driver.manage().window().maximize();
		}
		catch(NoSuchElementException e)
		{
			return "Fail";
		}
		
		return "Pass";
		
	
		
	}
	
	public String navigate(String URLKey){
		System.out.println("Executing navigate");
		try{
		driver.get(CONFIG.getProperty(URLKey));
		}catch(NoSuchElementException e){
			return "Fail";
		}
		return "Pass";
	}
	
	public String Robot_click() throws AWTException, InterruptedException
	{
		try
		{
			Thread.sleep(3000);
		Robot robot = new Robot();//Comment RobotClass When Running on Chrome
        robot.delay(500);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.delay(1000);

        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(2000);
        /*driver.switchTo().defaultContent();

        Thread.sleep(2000);*/
        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(3000);
		}
         catch(Exception e)
         {
                  return "Fail";	
         }
		return "Pass";

	}
	
	

	public String clickLink(String xpathKey,String data){
		System.out.println("Executing click");
		try{
			//Thread.sleep(3000);
			String[] xpathKey1 =  xpathKey.split(">");
			if((xpathKey.contains("text"))&&((xpathKey.contains(":"))))
			{
				
				String[] xpathKey2 =  xpathKey1[1].split(":");
				
				driver.findElement(By.xpath("(//a[contains(text(),'"+xpathKey2[0]+"')])["+xpathKey2[1]+"]")).click();
			}
			else if((xpathKey.contains("text"))&&((xpathKey.contains(">"))))
			{
				driver.findElement(By.xpath("//a[contains(text(),'"+xpathKey1[1]+"')]")).click();
			}
			else if(xpathKey.contains("//"))
			{
				driver.findElement(By.xpath(xpathKey)).click();
			}
			else if(xpathKey.contains("id")&&(data!=null))
			{
				driver.findElement(By.xpath("//*[@id='"+data+"']")).click();
			}
			else if(xpathKey.contains("id"))
			{
				driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']")).click();
			}
			
			/*else if(xpathKey.contains("id"))
			{
				driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']")).click();
			}*/
			else if(xpathKey.contains("name"))
			{
				driver.findElement(By.xpath("//*[@name='"+xpathKey1[1]+"']")).click();
			}
			
			else if(xpathKey.contains("class"))
			{
				
					driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']")).click();
					
			}
			
			else if(xpathKey.contains("text"))
			{
				driver.findElement(By.xpath("//a[contains(text(),'"+data+"')]")).click();
			}
			else if(xpathKey.contains("//"))
			{
				driver.findElement(By.xpath(xpathKey)).click();
			}
			else if(xpathKey.contains("link"))
			{
				driver.findElement(By.xpath("//*[@link='"+xpathKey1[1]+"']")).click();
			}
			
			
		}catch(NoSuchElementException e){
			return "Fail";

		}
		catch(Exception e){
			return "Fail";

		}
		return "Pass";	
	}
	
	public String clickLink_text(String xpathKey,String data){
		System.out.println("Executing click");
		try{
			String[] xpathKey1 =  xpathKey.split(">");
			if((xpathKey.contains("text"))&&((xpathKey.contains(":"))))
			{
				
				String[] xpathKey2 =  xpathKey1[1].split(":");
				
				driver.findElement(By.xpath("(//a[contains(text(),'"+data+"')])["+xpathKey2[1]+"]")).click();
			}
						
			else if(xpathKey.contains("text"))
			{
				driver.findElement(By.xpath("//a[contains(text(),'"+data+"')]")).click();
			}
			
			
		}catch(NoSuchElementException e){
			return "Fail";

		}
		catch(Exception e){
			return "Fail";

		}
		return "Pass";	
	}
	public String clickButton(String xpathKey){
		System.out.println("Executing click");
		try{
			//Thread.sleep(3000);
			String[] xpathKey1 =  xpathKey.split(">");
			
			if(xpathKey.contains("value"))
			{
				driver.findElement(By.xpath("//*[@value='"+xpathKey1[1]+"']")).click();
				//driver.findElement(By.xpath("//a[contains(text(),'"+xpathKey+"')]")).click();	
			}
			else if(xpathKey1[0].contains("button"))
			{
				driver.findElement(By.xpath("//button[@id='"+xpathKey1[1]+"']")).click();
			}
			else if((xpathKey.contains("id"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				
				 driver.findElement(By.xpath("(//input[@id='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]")).click();
			
				// driver.findElement(By.xpath("(//input[@id='"+xpathKey2[0]+"')])["+xpathKey2[1]+"]")).click();
			}
			else if(xpathKey.contains("id"))
			{
				driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']")).click();
			}
			else if(xpathKey.contains("class"))
			{
				driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']")).click();
			}
			
			else if(xpathKey.contains("text"))
			{
				driver.findElement(By.xpath("//a[contains(text(),'"+xpathKey1[1]+"')]")).click();
			}
		
			else if(xpathKey.contains("alt"))
			{
				driver.findElement(By.xpath("//*[@alt='"+xpathKey1[1]+"']")).click();
			}
			else if(xpathKey.contains("name"))
			{
				driver.findElement(By.xpath("//*[@name='"+xpathKey1[1]+"']")).click();
			}
			else if(xpathKey.contains("type"))
			{
				driver.findElement(By.xpath("//*[@type='"+xpathKey1[1]+"']")).click();
			}
			
			else if((xpathKey.contains("text"))&&((xpathKey.contains(":"))))
			{
				
				String[] xpathKey2 =  xpathKey1[1].split(":");
				
				driver.findElement(By.xpath("(//a[contains(text(),'"+xpathKey2[0]+"')])["+xpathKey2[1]+"]")).click();
			}
		
			
			
			
		}catch(NoSuchElementException e){
			return "Fail";

		}
		catch(Exception e){
			return "Fail";

		}
		return "Pass";	
	}
	
	public String input(String xpathKey,String data) throws InterruptedException{
		System.out.println("Executing input");
		try{
			//Thread.sleep(3000);
			String[] xpathKey1 =  xpathKey.split(">");
			
			//driver.findElement(By.xpath("//*[@name='"+xpathKey+"']")).sendKeys(data);
			if(xpathKey.contains("name"))
			{
				driver.findElement(By.xpath("//*[@name='"+xpathKey1[1]+"']")).clear();
				driver.findElement(By.xpath("//*[@name='"+xpathKey1[1]+"']")).sendKeys(data);	}
			
			else if(xpathKey.contains("id"))
			{
				driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']")).clear();
				driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']")).sendKeys(data);
			}
			else if(xpathKey.contains("class"))
			{
				driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']")).clear();
				driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']")).click();
			}
			
			
			Thread.sleep(3000);
			
		//driver.findElement(By.xpath(OR.getProperty(xpathKey))).sendKeys(data);
		}catch(NoSuchElementException e){
			return "Fail";
		}
		catch(Exception e){
			return "Fail";

		}
		return "Pass";			
	}
	

	public  String CheckBox(String xpathKey)
	{
		try{
		//driver.findElement(By.xpath("//*[@id='"+xpathKey+"']")).click();
			//Thread.sleep(3000);
			
				String[] xpathKey1 =  xpathKey.split(">");
			
			if(xpathKey.contains("value"))
			{
				driver.findElement(By.xpath("//*[@value='"+xpathKey1[1]+"']")).click();
				//driver.findElement(By.xpath("//a[contains(text(),'"+xpathKey+"')]")).click();	
			}
			else if(xpathKey.contains("//"))
			{
				driver.findElement(By.xpath(xpathKey)).click();
			}
			else if(xpathKey.contains("id"))
			{
				driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']")).click();
			}
			else if(xpathKey.contains("text"))
			{
				driver.findElement(By.xpath("//a[contains(text(),'"+xpathKey1[1]+"')]")).click();
			}
			else if(xpathKey.contains("type")&&xpathKey.contains(":"))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				driver.findElement(By.xpath("(//input[@type='checkbox'])["+xpathKey2[1]+"]")).click();
			}
			else if(xpathKey.contains("type"))
			{
				driver.findElement(By.xpath("//*[@type='"+xpathKey1[1]+"']")).click();
						
			}
			else if(xpathKey.contains("class"))
			{
				driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']")).click();
			}
			else if(xpathKey.contains("name"))
			{
				driver.findElement(By.xpath("//*[@name='"+xpathKey1[1]+"']")).click();
			}
			
		
			
		}catch(NoSuchElementException e){
			return "Fail";

		}
		catch(Exception e){
			return "Fail";

		}
		
		return "Pass";	
	}
	
	public  String RadioButton(String xpathKey)
	{
		try{
		//driver.findElement(By.xpath("//*[@id='"+xpathKey+"']")).click();
			//Thread.sleep(3000);
			
			String[] xpathKey1 =  xpathKey.split(">");
			
			if(xpathKey.contains("value"))
			{
				driver.findElement(By.xpath("//*[@value='"+xpathKey1[1]+"']")).click();
				//driver.findElement(By.xpath("//a[contains(text(),'"+xpathKey+"')]")).click();	
			}
			else if(xpathKey.contains("//"))
			{
				driver.findElement(By.xpath(xpathKey)).click();
			}
			else if(xpathKey.contains("id"))
			{
				driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']")).click();
			}
			else if(xpathKey.contains("class"))
			{
				driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']")).click();
			}
			
			else if(xpathKey.contains("text"))
			{
				driver.findElement(By.xpath("//a[contains(text(),'"+xpathKey1[1]+"')]")).click();
			}
		
			
		}catch(NoSuchElementException e){
			return "Fail";

		}
		catch(Exception e){
			return "Fail";

		}
		
		return "Pass";	
	}
	
	public  String WebGrid(String xpathKey)
	{
		try {
			//Thread.sleep(3000);
			String[] xpathKey1 =  xpathKey.split(">");
			
			if(xpathKey.contains("class"))
			{
	           	if(driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']")).isDisplayed())
		        	{
		        		
		        	}
			}
			else if(xpathKey.contains("id"))
			{
	           	if(driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']")).isDisplayed())
	        	{
	        		
	        	}
			}
      
			} catch (NoSuchElementException e) {
        	  return "Fail";
			}
			catch(Exception e){
				return "Fail";
	
			}
		
		return "Pass";	
	}
	
	public String verifyText(String xpathKey,String data){
		

        try {
        	
        	//Thread.sleep(3000);
        	String[] xpathKey1 =  xpathKey.split(">");
			if(xpathKey.contains(">"))
			{
				String appnText=driver.findElement(By.xpath(xpathKey1[0])).getText();
				if(data!=null)
				{
					if(appnText.contains(data))
					{
						
					}	
				}
				else
				{
					if(appnText.contains(xpathKey1[1]))
					{
						
					}	
				}
				
			}
			
			else if(driver.findElement(By.xpath(xpathKey)).isDisplayed())
        	{
        		
        	}
           	
        	/*if(driver.findElement(By.xpath("//a[contains(text(),'"+xpathKey+"')]")).isDisplayed())
        	{
        		
        	}*/
      
           } catch (NoSuchElementException e) {
        	  return "Fail";
          }
	        catch(Exception e){
				return "Fail";
	
			}
		System.out.println("Executing verifyText");

		return "Pass";					
	}
	
public String verifyContent(String xpathKey){
		

        try {
        	
           	
	        	if(driver.findElement(By.xpath("//p[contains(text(),'"+xpathKey+"')]")).isDisplayed())
	        	{
	        		
	        	}
	      
	            } catch (NoSuchElementException e) {
	        	  return "Fail";
            }
		        catch(Exception e){
					return "Fail";
		
			}
		System.out.println("Executing verifyText");

		return "Pass";					
	}
	
  /* public String verifyImage(String xpathKey){
		
         try {
        	
        	if(driver.findElement(By.xpath("//*[@id='"+xpathKey+"']")).isDisplayed())
        	{
        		
        	}
              } catch (NoSuchElementException e) {
        	  //return "Fail - can't see element-"+xpathKey;
            	  return "Fail";
          }
		System.out.println("Executing verifyText");

		return "Pass";					
	}*/
		//modified on 11/10/2013
		public String verifyImage(String xpathKey){
			
		    try {
		    	Thread.sleep(3000);
		    	if(xpathKey.contains("alt"))
		    	{
		    		if(driver.findElement(By.xpath("//*[@alt='"+xpathKey+"']")).isDisplayed())
				   	{
				   		
				   	}
		    	}
		    	else if(xpathKey.contains("id"))
		    	{
		    		if(driver.findElement(By.xpath("//*[@id='"+xpathKey+"']")).isDisplayed())
				   	{
				   		
				   	}	
		    	}
		    	else if(xpathKey.contains("class"))
		    	{
		    		if(driver.findElement(By.xpath("//*[@class='"+xpathKey+"']")).isDisplayed())
				   	{
				   		
				   	}	
		    	}
		    
		   	
		         } catch (NoSuchElementException e) {
		   	  //return "Fail - can't see element-"+xpathKey;
		       	  return "Fail";
		     }
			    catch(Exception e){
					return "Fail";
			    }
			System.out.println("Executing verifyText");
		
			return "Pass";					
		}
   
   //new Select(driver.findElement(By.xpath("//select[@id='cardType']"))).selectByVisibleText("Visa");
   
   public String select(String xpathKey,String data){
		

       try {
    	   //Thread.sleep(3000);
       	
    	   String[] xpathKey1 =  xpathKey.split(">");
    	  
			
			if(xpathKey.contains("class"))
			{
          	
				new Select(driver.findElement(By.xpath("//select[@id='"+xpathKey1[1]+"']"))).selectByVisibleText(data);
			}
			else if(xpathKey.contains("value"))
			{
				new Select(driver.findElement(By.xpath("//value[@id='"+xpathKey1[1]+"']"))).selectByVisibleText(data);

			}
			else if(xpathKey.contains("select")||(xpathKey.contains("id")))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				
				if((xpathKey.contains("select")||(xpathKey.contains("id")))&&((xpathKey.contains(":"))))
				{
					new Select(driver.findElement(By.xpath("(//select[@id='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"))).selectByVisibleText(data);
				}
				else
				{
					new Select(driver.findElement(By.xpath("//select[@id='"+xpathKey1[1]+"']"))).selectByVisibleText(data);
				}
				
				
				

			}
			/*else if(xpathKey.contains("id"))
			{
				new Select(driver.findElement(By.xpath("//select[@id='"+xpathKey1[1]+"']"))).selectByVisibleText(data);

			}*/
			else if(xpathKey.contains("name"))
			{
				try
				{
				new Select(driver.findElement(By.xpath("//value[@name='"+xpathKey1[1]+"']"))).selectByVisibleText(data);
				}
				catch(NoSuchElementException e)
				{
					try
					{
						Thread.sleep(3000);
						String[] xpathKey2 =  xpathKey1[1].split(":");
						if(xpathKey.contains("name")&&xpathKey.contains(":"))
						{
							new Select(driver.findElement(By.xpath("(//select[@name='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"))).selectByVisibleText(data);
							//(//select[@name='action'])[2]
						}
						else
						{
						new Select(driver.findElement(By.xpath("//select[@name='"+xpathKey1[1]+"']"))).selectByVisibleText(data);
						}
						
						
					}
					catch(NoSuchElementException ae)
					{
						 return "Fail";
					}
				}


			}
          } catch (NoSuchElementException e) {
       	  return "Fail";
         }
       
	       catch(Exception e){
				return "Fail";
	       }
		System.out.println("Executing verifyText");

		return "Pass";					
	}
	

	public String verifyObject(String xpathKey){
		

       try {
    	   //Thread.sleep(3000);
    	   String[] xpathKey1 =  xpathKey.split(">");
       	
	       	if(xpathKey.contains("checked"))
	       	{
	       		if(driver.findElement(By.xpath("//*[@checked='"+xpathKey+"']")).isEnabled());
	       	}
	       	else if(xpathKey.contains("active"))
	    	{
				if(driver.findElement(By.xpath("h4//[@class='"+xpathKey1[1]+"']")).isDisplayed())
	        	{
					
	        	}
	    	}
	       	
		   	else if(xpathKey.contains("class"))
			{
	           	if(driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']")).isDisplayed())
		        	{
		        		
		        	}
			}
		   	else if(xpathKey.contains("//"))
	    	{
	    		if(driver.findElement(By.xpath(xpathKey)).isDisplayed())
			   	{
			   		
			   	}
	    	}
			else if(xpathKey.contains("id"))
			{
	           	if(driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']")).isDisplayed())
	        	{
	        		
	        	}
			}
			else if(xpathKey.contains("alt"))
	    	{
	    		if(driver.findElement(By.xpath("//*[@alt='"+xpathKey1[1]+"']")).isDisplayed())
			   	{
			   		
			   	}
	    	}
			else if(xpathKey.contains("title"))
	    	{
	    		if(driver.findElement(By.xpath("//*[@title='"+xpathKey1[1]+"']")).isDisplayed())
			   	{
			   		
			   	}
	    	}
			
	       	
     
          } catch (NoSuchElementException e) {
       	  return "Fail";
         }
       
	       catch(Exception e){
				return "Fail";
	       	}
		System.out.println("Executing verifyObject");

		return "Pass";					
	}
 
	public String SwitchBrowser(String title) throws InterruptedException
	{
		//Thread.sleep(3000);
		//WebDriver driver = new FirefoxDriver();
		//driver.switchTo().window("firepath-matching-node-style");
		
		Set<String> windows = driver.getWindowHandles();
	    Iterator<String> window = windows.iterator();
	    while( window.hasNext() ) {

	        driver.switchTo().window( window.next() );
	        
	        try
	        {
	        	if(driver.switchTo().window( window.next()).getTitle().equals(title));
	        	
	        }
	        catch(Exception e)
	        {
	        	return "Fail";
	        }
	   

	    }
	    
	    /*driver.switchTo().defaultContent();
	    driver.close();*/
	    //driver.switchTo().frame(0);
	    return "Pass";
	
	}
	
	public String MouseHover(String xpathKey)
	{
		try
		{
			WebElement menu = driver.findElement(By.xpath(xpathKey));
			
			Actions action = new Actions(driver);
			action.moveToElement(menu).perform();
			
		}
		catch(Exception e)
		{
			
		}
		return xpathKey;
		
	}
	
	public String file_upload (String data)
	{
		try
		{
			Thread.sleep(3000);
			StringSelection abc=new StringSelection(data);
			
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(abc, null);
			
			Thread.sleep(3000);
			
			Robot r = new Robot(); 
					
			r.keyPress(KeyEvent.VK_ENTER); 
			r.keyRelease(KeyEvent.VK_ENTER); 
			r.keyPress(KeyEvent.VK_CONTROL); 
			r.keyPress(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_V); 
			r.keyRelease(KeyEvent.VK_CONTROL); 
			r.keyPress(KeyEvent.VK_ENTER); 
			r.keyRelease(KeyEvent.VK_ENTER); 
			
			
		}
		catch(Exception e)
		{
			return "Fail";	
		}
		return "Pass";
		
	}
   // Added on 11/10/2013
 /*  public String verifyBreadCrumb(String xpathKey){
		
	    try {
	   	
	   	if(driver.findElement(By.xpath("//*[@id='"+xpathKey+"']")).isDisplayed())
	   	{
	   		
	   	}
	         } catch (NoSuchElementException e) {
	   	  //return "Fail - can't see element-"+xpathKey;
	       	  return "Fail";
	     }
		System.out.println("Executing verifyText");
	
		return "Pass";					
	}
   */
   public String verifyBreadCrumb(String xpathKey){
		
	    try {
	    	//Thread.sleep(3000);
	   	
	   	if(driver.findElement(By.xpath(xpathKey)).isDisplayed())
	   	{
	   		
	   	}
	         } catch (NoSuchElementException e) {
	   	  //return "Fail - can't see element-"+xpathKey;
	       	  return "Fail";
	     }
	    
	    catch(Exception e){
			return "Fail";
       }
		System.out.println("Executing verifyText");
	
		return "Pass";					
	}
   
   public String waitThread() throws InterruptedException
   {
	   Thread.sleep(5000);
	   return "Pass";	  
   }
	
	/**************************Application dependent****************************/
	public String checkMail(String mailName){
		System.out.println("Executing checkMail");

		try{
		driver.findElement(By.linkText(mailName)).click();
		}catch(Exception e){
			return "Fail-Mail not found";
		}
		
		
		
		return "Pass";							
	}
	
	
	
	//******Singleton function*******//
	public static Keywords getKeywordsInstance(){
		if(keywords == null){
			keywords = new Keywords();
		}
		
		
		return keywords;
	}
	
/*	public String verifyLogin(String verificationText){
		
		int total= driver.findElements(By.xpath(OR.getProperty("login_err_msg"))).size();
		int inboxcount=driver.findElements(By.xpath(OR.getProperty("inbox"))).size();
		if(total!=0){
			// not logged in
			// verify err msg
			if(driver.findElement(By.xpath(OR.getProperty("login_err_msg"))).equals(verificationText))
				return "Pass";
			else 
				return "Fail - Not able to find the error message ";
		}
			
		else if(inboxcount!=0){
		 // logged in
			if(driver.findElement(By.xpath(OR.getProperty("inbox"))).equals(verificationText))
				return "Pass";
			else 
				return "Fail - Not able to find the Inbox link";
		}
		
		return "Fail";
	}*/
	
	
	public  String deletcookies()
	{
		try
		{
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		}
		catch(Exception e)
		{
			return "Fail";
		}
		
		return "Pass";
	}
	
	public  String handle()
	{
		/*if(driver.findElement(By.xpath("//p[contains(text(),'"+xpathKey+"')]")).isDisplayed())
    	{
    		
    	}*/
		
		//driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).click();
		
		if(driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).isDisplayed())
    	{
			driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).click();
    	}
		return "Pass";
  
	}
	
	 public  String popup()
     {
            try {
                //  assertTrue(isElementPresent(By.id("//*[@id='banner-proceed-button']")));
                  
                  if(driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).isDisplayed())
              	{
          			driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).click();
              	}
          		
               
                } catch (NoSuchElementException e) {
                  System.out.println("Sign in link does not exist");
                }
        
            return "Pass";

     
     }
     
     public  boolean isElementPresent(By by) {
                try {
                  driver.findElement(by);
                  return true;
                } catch (NoSuchElementException e) {
                  return false;
                }
              }

	

	
	
	

}
