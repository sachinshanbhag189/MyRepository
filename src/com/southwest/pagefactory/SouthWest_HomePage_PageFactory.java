package com.southwest.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.southwest.functionlib.ExcelReadWrite;

public class SouthWest_HomePage_PageFactory {


	//Region Identify webElements
	@FindBy(xpath="//a[@id='booking-form--flight-tab']//span[contains(text(),'Flight')]")
	public WebElement flight_tab;

	@FindBy(xpath="//a[@id='booking-form--hotel-tab']//span[contains(text(),'Hotel')]")
	public WebElement hotel_tab;

	@FindBy(xpath="//a[@id='booking-form--car-tab']//span[contains(text(),'Car')]")
	public WebElement car_tab;

	@FindBy(xpath="//a[@id='booking-form--vacations-tab']//span[contains(text(),'Vacations')]")
	public WebElement vacations_tab;

	@FindBy(xpath="//input[@id='trip-type-round-trip']")
	public WebElement roundtrip_ceheckbox;

	@FindBy(xpath="//input[@id='trip-type-one-way']")
	public WebElement oneway_checkbox;

	@FindBy(xpath="//input[@id='air-city-departure']")
	public WebElement air_departure_city;

	@FindBy(xpath="//input[@id='air-city-arrival']")
	public WebElement air_arrival_city;

	@FindBy(xpath="//input[@id='air-date-departure']")
	public WebElement air_departure_date;

	@FindBy(xpath="//input[@id='air-date-return']")
	public WebElement air_arrival_date;

	@FindBy(xpath="//input[@id='air-pax-count-adults']")
	public WebElement no_of_adults;

	@FindBy(xpath="//input[@id='air-pax-count-seniors']")
	public WebElement no_of_seniors;

	@FindBy(xpath="//div[contains(@class,'value')]")
	public WebElement value_of_pax_editor;

	@FindBy(xpath="//button[@id='jb-booking-form-submit-button']")
	public WebElement air_search_button;

	@FindBy(xpath="//ul[@id='errors']")
	public WebElement ul_errors;

	@FindBy(xpath="//ul[@id='errors']//li")
	public WebElement li_errors;

	@FindBy(xpath="//input[@id='hotel-city-destination']")
	public WebElement hotel_destination;

	@FindBy(xpath="//input[@id='hotel-date-check-in']")
	public WebElement checkin_date;

	@FindBy(xpath="//input[@id='hotel-date-check-out']")
	public WebElement checkout_date;

	@FindBy(xpath="//input[@id='hotel-guest-count-adults']")
	public WebElement adults_hotel;

	@FindBy(xpath="//input[@id='hotel-guest-count-children']")
	public WebElement children_hotel;

	@FindBy(xpath="//form[@id='booking-form--hotel-panel']//button[contains(text(),'Search') and @type='submit']")
	public WebElement hotel_search_button;

	//EndRegion Identify webElements

	WebDriver driver;

	public SouthWest_HomePage_PageFactory(WebDriver driver) {
		this.driver = driver;
		// This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
	}

	public void fillSearchFormFlight(ExcelReadWrite excelrw, int loop){

		air_departure_city.clear();
		air_departure_city.sendKeys(excelrw.getCellData("Departure_City",loop));
		air_arrival_city.clear();
		air_arrival_city.sendKeys(excelrw.getCellData("Arrival_City",loop));
		air_departure_date.clear();
		air_departure_date.sendKeys(excelrw.getCellData("Departure_Date", loop));
		air_arrival_date.clear();
		air_arrival_date.sendKeys(excelrw.getCellData("Return_Date", loop));

		no_of_adults.sendKeys(excelrw.getCellData("Adults", loop));

		no_of_seniors.sendKeys(excelrw.getCellData("Seniors", loop));
	}


	public void fillSearchFormHotel(ExcelReadWrite excelrw, int loop){
		hotel_tab.click();
		hotel_destination.clear();
		hotel_destination.sendKeys(excelrw.getCellData("Destination",loop));
		checkin_date.clear();
		checkin_date.sendKeys(excelrw.getCellData("CheckInDate",loop));
		checkout_date.clear();
		checkout_date.sendKeys(excelrw.getCellData("CheckOutDate",loop));
		adults_hotel.sendKeys(excelrw.getCellData("Adults",loop));
		children_hotel.sendKeys(excelrw.getCellData("Children",loop));
	}



}
