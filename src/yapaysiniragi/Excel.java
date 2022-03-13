/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yapaysiniragi;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author kubra
 */
public class Excel implements IDosyaTipi {
    
    ArrayList<Double> veriler = new ArrayList<Double>();

    @Override
    public ArrayList<Double> verileriCek(String girisYol) {
        try {
            FileInputStream file = new FileInputStream(new File(girisYol));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //System.out.println("Alt satıra geçti");
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            //System.out.print(cell.getNumericCellValue() + " ");
                            veriler.add(cell.getNumericCellValue());
                            break;
                        case STRING:
                            //System.out.print(cell.getStringCellValue() + " ");
                            break;
                    }
                }
            }
            file.close();
            //System.out.println(veriler.size());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return veriler;
    }
}
