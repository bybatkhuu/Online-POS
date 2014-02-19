package utils;

import java.util.List;

public class Row
{
	private List<Cell> cellList;
	
	public Row()
    {
    }
    public Row(List<Cell> cellList)
    {
        this.cellList = cellList;
    }
    public Row(Row row)
    {
        this.cellList = row.getCellList();
    }

    public List<Cell> getCellList()
    {
        return cellList;
    }
    public void setCellList(List<Cell> cellList)
    {
        this.cellList = cellList;
    }
}