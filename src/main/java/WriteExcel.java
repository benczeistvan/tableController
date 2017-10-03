import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.*;

public class WriteExcel {
    public Row row;
    public Cell cell;

    public boolean write(String string, int k, int l, int m, String DEST) throws IOException {


        FileInputStream input_excel = new FileInputStream(new File(DEST));
        HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_excel);
        HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(m);

        Cell cell = null;
        // Access the cell first to update the value

        //my_worksheet.getRow(10).getCell(8).setCellValue("ISTVANKAAAA");

        // Get current value and then add 5 to it

        Iterator<Row> rowIterator = my_worksheet.iterator();
        for (int i = 0; i < k; i++){
            row = rowIterator.next();
        }
        Iterator<Cell> cellIterator = row.cellIterator();
        for (int i = 0; i < l; i++){
            cell = cellIterator.next();
        }

        cell.setCellValue(string);

        input_excel.close();

        FileOutputStream output_file =new FileOutputStream(new File(DEST));
        //write changes
        my_xls_workbook.write(output_file);
        //close the stream
        output_file.close();

        return true;
    }
}
