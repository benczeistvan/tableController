import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class Main {

    public static void main(String args[]) throws IOException {
        ReadExcel readExcel = new ReadExcel();

        if (readExcel.read() == true){
            System.out.println("Read oke");
        }

        //WriteExcel writeExcel = new WriteExcel();

//        if (writeExcel.write("SZIA", 6,5)== true){
//            System.out.println("Write oke");
//        }

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

    public static String replaceAtTheEnd(String input){
        input = input.replaceAll("\\s+$", "");
        return input;
    }
}
