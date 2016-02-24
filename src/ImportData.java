import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: LGM
 * Date: 2/24/16
 * Time: 2:38 PM
 */

public class ImportData
{
	public String GetData(Sheet s, String country, int year)
	{
		for(int i=0; i<s.getCols(); i++)
			if(s.values[16][i].equals(Integer.toString(year)))
			{
				for(int j=0; j<s.getRows(); j++)
					if(s.values[j][2].equals(country))
						return s.values[j][i];
			}
		return null;
	}
	public Sheet Import(String File, int sheetNum) throws IOException
	{
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(File));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		if(sheetNum>wb.getNumberOfSheets()) return null;
		HSSFSheet sheet = wb.getSheetAt(sheetNum);
		HSSFRow row;
		HSSFCell cell;

		int rows; // No of rows
		rows = sheet.getPhysicalNumberOfRows();
		int cols = 0; // No of columns
		int tmp = 0;

		// This trick ensures that we get the data properly even if it doesn't start from first few rows
		for(int i = 0; i < 10 && i < rows; i++) {
			row = sheet.getRow(i);
			if(row != null) {
				tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				if(tmp > cols) cols = tmp;
			}
		}
		System.err.println(cols);
		String [][] res=new String[rows][cols];
		for(int r = 0; r < rows; r++) {
			res[r]=new String[cols];
			row = sheet.getRow(r);
			if(row != null) {
				for(int c = 0; c < cols; c++) {
					cell = row.getCell((short)c);
					res[r][c]=(cell==null?null:cell.toString());
				}
			}
		}
		return new Sheet(rows,cols,res);
	}

	public static void main(String[] args) throws IOException
	{
		Sheet data=new ImportData().Import("Data/WPP2015_POP_F01_2_TOTAL_POPULATION_MALE.XLS",0);
		Scanner scanner=new Scanner(System.in);
		String country=scanner.next();
		int year=scanner.nextInt();
		System.out.println(new ImportData().GetData(data,country,year));
	}
}
