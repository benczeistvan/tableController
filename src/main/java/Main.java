import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public class Main {

    public static void main(String args[]) {
        ReadExcel readExcel = new ReadExcel();

        if (readExcel.read() == true){
            System.out.println("Read oke");
        }

//        for (int i = 0; i < 10; i++){
//            System.out.println(readExcel.tanulo[i].getAzonosito());
//            System.out.println(readExcel.tanulo[i].getNev());
//            System.out.println(readExcel.tanulo[i].getAnyanev());
//            System.out.println(readExcel.tanulo[i].getSzuletes());
//            System.out.println(readExcel.tanulo[i].getHely());
//            System.out.println();
//        }



//        WritePDF writePDF = new WritePDF();
//        if (writePDF.write(readExcel) == true){
//            System.out.println("write okay");
//        }

//        ModifyPDF modifyPDF = new ModifyPDF();
//        if (modifyPDF.modify(readExcel) == true){
//            System.out.println("modify okay");
//        }
    }
}
