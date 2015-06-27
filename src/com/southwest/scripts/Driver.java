package com.southwest.scripts;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import org.testng.annotations.Test;

import com.southwest.functionlib.ExcelReadWrite;
import com.southwest.suitebase.BrowserBase;

public class Driver {

	//BrowserBase browserbase = new BrowserBase();
	ExcelReadWrite excelrw;
	BusinessActions business_actions = new  BusinessActions();

	public Driver(){

	}

	@Test
	public void driver_Script() throws IOException, BiffException, WriteException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		excelrw = new ExcelReadWrite(BrowserBase.filename, "Driver");
		int NoOfScenarios = excelrw.getRowCount();
		String sExecutionFlag, sAction;
		int iActCnt;

		for(int i=1; i<NoOfScenarios;i++)
		{
			sExecutionFlag = excelrw.getCellData("ExecutionFlag",i);
			if(sExecutionFlag.equalsIgnoreCase("Yes")){
				iActCnt=1;
				while(excelrw.getCellData("Action"+iActCnt, i)!="")
				{
					sAction = excelrw.getCellData("Action"+iActCnt, i);

					//Here ReflectionAPI can be used///////////////////////////
					/*Method method = BusinessActions.class.getMethod(sAction);
					method.invoke(method);*/


					if(sAction.equals("Open_Application")){
						business_actions.Open_Application();

					}
					else if(sAction.equals("SearchFlight")){
						business_actions.SearchFlights();
					}
					else if(sAction.equals("SearchHotel")){
						business_actions.Search_Hotel();
					}
					else
					{
						System.out.println("Do Nothing");
					}

					//////////////////////////////////////////////////////////
					iActCnt++;
				}
			}
			else
			{
				System.out.println("The Test is Skippped");
			}
		}
	}

}

