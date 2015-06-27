package com.southwest.scripts;

import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.southwest.functionlib.ExcelReadWrite;
import com.southwest.pagefactory.SouthWest_HomePage_PageFactory;
import com.southwest.suitebase.BrowserBase;

public class BusinessActions extends BrowserBase {

	SoftAssert s_assert;
	ExcelReadWrite excelrw;
	SouthWest_HomePage_PageFactory home_page_factory;

	public BusinessActions(){

		//TO-DO Action
	}

	@BeforeTest(description="Open the SouthWest Web Application",alwaysRun = true,enabled=true)
	public void Open_Application() throws IOException
	{
		//String methodName =Thread.currentThread().getStackTrace()[1].getMethodName();
		loadWebBrowserfromExcelFile();
		driver.get("https://www.southwest.com/");
		s_assert =  new SoftAssert();
		s_assert.assertTrue(driver.getTitle().equals("Southwest Airlines | Book Flights, Airline Tickets, Airfare"), "The Title of the Web Page does not match");
	}

	
	@BeforeMethod	
	public void RefreshPage() throws IOException
	{
		//String methodName =Thread.currentThread().getStackTrace()[1].getMethodName();
		loadWebBrowserfromExcelFile();
		driver.get("https://www.southwest.com/");
		s_assert =  new SoftAssert();
		s_assert.assertTrue(driver.getTitle().equals("Southwest Airlines | Book Flights, Airline Tickets, Airfare"), "The Title of the Web Page does not match");
	}

	
	/**
     * You can enter text here
     * You can enter text here
     * You can enter text here
     * You can enter text here
     * <p>
     * @param null
     * <p>
     * You can enter text here
     * You can enter text here
     * <blockquote><pre>
     *     System.out.println(data)
     * </pre></blockquote>
     * <p>
     * See the <code>println</code> methods in class <code>PrintStream</code>.
     *
     * @see     java.io.PrintStream#println()
     * @see     java.io.PrintStream#println(boolean)
     * @see     java.io.PrintStream#println(char)
     * @see     java.io.PrintStream#println(char[])
     * @see     java.io.PrintStream#println(double)
     * @see     java.io.PrintStream#println(float)
     * @see     java.io.PrintStream#println(int)
     * @see     java.io.PrintStream#println(long)
     * @see     java.io.PrintStream#println(java.lang.Object)
     * @see     java.io.PrintStream#println(java.lang.String)
     **/
	@Test (priority= 0,alwaysRun=true, description="Flight Search Test",enabled = true, groups = {"Sanity"})
	public void SearchFlights() throws BiffException, WriteException, IOException
	{
		excelrw = new ExcelReadWrite(filename,"FlightSearch");
		home_page_factory = new SouthWest_HomePage_PageFactory(driver);
		int no_of_rows = excelrw.getRowCount();
		int count;
		for(int iteration=1; iteration<no_of_rows; iteration++ )
		{
			home_page_factory.fillSearchFormFlight(excelrw, iteration);
			home_page_factory.air_search_button.click();
			if(driver.getTitle().equalsIgnoreCase("Southwest Airlines - Select Flight(s)"))
			{
				excelrw.writeToExistingExcel(filename, "FlightSearch", "Result", iteration,"Passed"); 
			}
			else{
				excelrw.writeToExistingExcel(filename, "FlightSearch", "Result", iteration,"Failed");
				List<WebElement> allRows =home_page_factory.ul_errors.findElements(By.tagName("li"));
				if((allRows.size())!=0)
				{
					count = allRows.size();
					StringBuilder value = new StringBuilder();
					for(int i=1;i<=count;i++)
					{					
						WebElement list = driver.findElement(By.xpath("//ul[@id='errors']//li["+i+"]"));
						String error_text = list.getText();						
						value.append(error_text);
						value.append("\n");											
					}
					String error_details = value.toString();
					excelrw.writeToExistingExcel(filename, "FlightSearch", "ErrorDetails", iteration,error_details);	
				}				
			}
			driver.navigate().back();
		}
	}

	@Test (priority= 2,alwaysRun=true, description="Less than 3 Star Hotel Search Test",enabled = true, groups = {"Sanity"})
	public void Search_Hotel() throws BiffException, WriteException, IOException{
		excelrw = new ExcelReadWrite(filename,"HotelSearch");
		home_page_factory = new SouthWest_HomePage_PageFactory(driver);

		int no_of_rows = excelrw.getRowCount();   //can give 10 rows    0-8 = 9 rows

		for(int iteration=1; iteration<no_of_rows; iteration++ )                 //1-9 == 9 rows will be iterated
		{  
			home_page_factory.fillSearchFormHotel(excelrw, iteration);
			home_page_factory.hotel_search_button.click();

			int no_of_stars_for_a_hotel;
			String HotelName;
			StringBuilder hotel_information_text  = new StringBuilder();

			List <WebElement> list_hotel_star_ratings = driver.findElements(By.xpath("//div[@class='hotel_description_container']//span[@id='hotelRating']"));

			for(WebElement stars : list_hotel_star_ratings)
			{
				List<WebElement> yellow_star  = stars.findElements(By.xpath(".//span[contains(@class,'yellow')]"));   
				//Region IMPORTANT NOTE PLEASE READ
				//Find all elements within the current context using the given mechanism. 
				//When using xpath be aware that webdriver follows standard conventions: 
				//a search prefixed with "//" will search the entire document, not just the children of 
				//this current node. Use ".//" to limit your search to the children of this WebElement. 
				//This method is affected by the 'implicit wait' times in force at the time of execution. 
				//When implicitly waiting, this method will return as soon as there are more than 0 items in the found collection, or will return an empty list if the timeout is reached.
				//EndRegion
				no_of_stars_for_a_hotel = yellow_star.size();			

				if(no_of_stars_for_a_hotel<4){
					HotelName = stars.findElement(By.xpath("./../../..//span[@class='hotelName']")).getText().toString();
					hotel_information_text.append(HotelName+" is a "+no_of_stars_for_a_hotel+" star hotel");
					hotel_information_text.append("\n");
				}	
			}
			String hotel_information_star_details = hotel_information_text.toString();			
			excelrw.writeToExistingExcel(filename, "HotelSearch", "StarDetails", iteration,hotel_information_star_details);	
		}
	}
}

