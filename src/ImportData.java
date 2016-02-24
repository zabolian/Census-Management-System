import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: LGM
 * Date: 2/24/16
 * Time: 2:38 PM
 */
public class ImportData
{
	public String [][] Import(String File) throws IOException
	{
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(File));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);
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
		return res;
	}

	public static void main(String[] args) throws IOException
	{
		new ImportData().Import("Data/WPP2015_POP_F01_2_TOTAL_POPULATION_MALE.XLS");
	}
}
