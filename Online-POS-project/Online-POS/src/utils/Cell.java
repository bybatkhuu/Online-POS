package utils;

public class Cell
{
	private String column;
    private String value;
    
    public Cell()
    {
    }
    public Cell(Cell cell)
    {
        this.column = cell.getColumn();
        this.value = cell.getValue();
    }
    public Cell(String column, String value)
    {
        this.column = column;
        this.value = value;
    }
    
	public String getColumn()
	{
		return column;
	}
	public void setColumn(String column)
	{
		this.column = column;
	}
	
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
}