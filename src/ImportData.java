import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
	static Sheet male,female;
	static Set<String> protect;
	public static void main(String[] args) throws IOException
	{
		protect=new HashSet<>();
		ImportData importData=new ImportData();
		male=importData.Import("Data/WPP2015_POP_F01_2_TOTAL_POPULATION_MALE.XLS",0);
		female=importData.Import("Data/WPP2015_POP_F01_3_TOTAL_POPULATION_FEMALE.XLS",0);
		File f = new File("Data/Changes");
		if (f.exists())
		{
			Scanner changes = new Scanner(f);
			while (changes.hasNext())
			{
				String country = changes.next();
				int year = changes.nextInt();
				String s = changes.next();
				Pair<Integer, Integer> cell = (s.equals("male") ? male : female).getCell(country, year);
				if (cell != null)
					(s.equals("male") ? male : female).values[cell.first][cell.second] = changes.next();
				System.err.println("updated");
			}
			changes.close();
		}
		File f2 = new File("Data/Protect");
		if (f2.exists())
		{
			Scanner changes = new Scanner(f2);
			while (changes.hasNext())
			{
				String country = changes.next();
				protect.add(country);
			}
			changes.close();
		}
		PrintStream change=new PrintStream(new FileOutputStream("Data/Changes",true));
		PrintStream protectLog=new PrintStream(new FileOutputStream("Data/Protect",true));

		Scanner scanner=new Scanner(System.in);
		while (true)
		{
			String com = scanner.next();
			if (com.equals("1"))
			{
				String country=scanner.next();
				int year=scanner.nextInt();
				System.out.println(importData.GetData(male,country,year)+" , "+importData.GetData(female,country,year));
			}
			if (com.equals("2"))
			{
				String country=scanner.next();
				int year=scanner.nextInt();
				String s=scanner.next();
				Pair<Integer,Integer> cell=(s.equals("male")?male:female).getCell(country,year);
				String value=scanner.next();
				if (protect.contains(country))
					continue;
				if (cell!=null)
					(s.equals("male")?male:female).values[cell.first][cell.second]=value;
				change.println(country+" "+year+" "+s+" "+value);
			}
			if (com.equals("3"))
			{
				int year=scanner.nextInt();
				int sheetNo=scanner.nextInt();
				new Lister().getList(year,sheetNo);
			}
			if (com.equals("8"))
			{
				String country=scanner.next();
				if (!protect.contains(country))
				{
					protect.add(country);
					protectLog.println(country);
				}
			}
		}
	}
}
