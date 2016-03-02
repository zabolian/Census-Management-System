/**
 * Created with IntelliJ IDEA.
 * User: LGM
 * Date: 2/24/16
 * Time: 3:00 PM
 */
public class Sheet
{
	int rows,cols;
	String [][] values;

	public Sheet(int rows, int cols, String[][] values)
	{
		this.rows = rows;
		this.cols = cols;
		this.values = values;
	}

	public int getCols()
	{
		return cols;
	}

	public void setCols(int cols)
	{
		this.cols = cols;
	}

	public int getRows()
	{
		return rows;
	}

	public void setRows(int rows)
	{
		this.rows = rows;
	}

	public String[][] getValues()
	{
		return values;
	}

	public void setValues(String[][] values)
	{
		this.values = values;
	}

	public Pair<Integer, Integer> getCell(String country, int year)
	{
		for(int i=0; i<getCols(); i++)
			if(values[16][i].equals(Integer.toString(year)))
			{
				for(int j=0; j<getRows(); j++)
					if(values[j][2].equals(country))
						return new Pair<>(j,i);
			}
		return null;
	}
}
