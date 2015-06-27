package com.southwest.functionlib;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelReadWrite {

	//USING JXL LIBRARY
		
	int colnNum=0;
	Sheet sheet1;
	Workbook wb = null;

	public ExcelReadWrite(String filename, String sheetName) {
		File fp = new File(filename);

		try{
			wb= Workbook.getWorkbook(fp);
		}
		catch(BiffException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		sheet1 = wb.getSheet(sheetName);
		@SuppressWarnings("unused")
		int row_count = getRowCount();
		@SuppressWarnings("unused")
		int column_count = getColumnCount();
	}


	public int getColumnHeaderNumber(String strColumn){
		for(colnNum=0; colnNum<this.sheet1.getColumns();colnNum++){
			if(this.sheet1.getCell(colnNum,0).getContents().equals(strColumn)){
				break;
			}
		}
		return colnNum;
	}
	
	public int getRowHeaderNumber(String strRowData){  // in case the headers are in the first column vertically 
		int rowNum;
		for(rowNum =1; rowNum<this.sheet1.getRows();rowNum++){
			if(this.sheet1.getCell(0, rowNum).getContents().toString().equals(strRowData)){
				break;
			}
		}
		return rowNum;
	}

	public String getCellData(int iRow, int iColumn)
	{
		return this.sheet1.getCell(iColumn, iRow).getContents().toString();
	}

	public String getCellData(String strColumn, int iRow)
	{
		return this.sheet1.getCell(this.getColumnHeaderNumber(strColumn),iRow).getContents().toString();
	}

	public int getRowCount(){
		return sheet1.getRows();
	}

	public int getColumnCount(){
		return sheet1.getColumns();
	}
	
	//Creates new excel file and add a new sheet at 0 index and writes given data.
	public void writeToExcel(String sfilename, String sSheetName, int iCol, int iRow, String sData) throws IOException, BiffException, WriteException
	{
		WritableWorkbook wworkbook = Workbook.createWorkbook(new File(sfilename));
		WritableSheet wsheet = wworkbook.createSheet(sSheetName, 0);
		//Repeat following 2 lines for multiple entries   - Sachin :- Need to check what that states.
		Label label  = new Label(iCol, iRow, sData);
		wsheet.addCell(label);
		wworkbook.write();
		wworkbook.close();
	}

	public void writeToExistingExcel(String sfilename, String sSheetName, int iCol, int iRow, String sData) throws IOException, BiffException, WriteException
	{
		Workbook workbook = Workbook.getWorkbook(new File(sfilename));			
		WritableWorkbook workbookCopy= Workbook.createWorkbook(new File(sfilename), workbook);		
		WritableSheet wSheet = workbookCopy.getSheet(sSheetName);		
		Label label  = new Label(iCol, iRow, sData);
		wSheet.addCell(label);
		workbookCopy.write();
		workbookCopy.close();
		workbook.close();
	}

	public void writeToExistingExcel(String sfilename, String sSheetName, String colName, int iRow, String sData) throws IOException, BiffException, WriteException
	{
		Workbook workbook = Workbook.getWorkbook(new File(sfilename));			
		WritableWorkbook workbookCopy= Workbook.createWorkbook(new File(sfilename), workbook);		
		WritableSheet wSheet = workbookCopy.getSheet(sSheetName);		
		int number_of_the_writable_column = getColumnHeaderNumber(colName);  //0 based
		Label label  = new Label(number_of_the_writable_column, iRow, sData);
		wSheet.addCell(label);
		workbookCopy.write();
		workbookCopy.close();
		workbook.close();
	}
	
}
