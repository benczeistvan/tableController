import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class Main {

    public String CLASSIC = "/Users/istvan/GitHub/tableController/src/main/java/CLASSIC.xls";
    public String SZILVER = "/Users/istvan/GitHub/tableController/src/main/java/SZILVER.xls";



    public static void main(String args[]) throws IOException {
        ReadExcel readExcel = new ReadExcel();

//        if (readExcel.read() == true){
//            System.out.println("Read oke");
//        }

        String melyiken = "SZILVER";

        //String destination = "/Users/istvan/Documents/kir/Telephelyek/";
        //destination += melyiken + "/";


        //String szilverClassic = "/Users/istvan/GitHub/tableController/src/main/java/";
        //szilverClassic += melyiken + ".xls";

        String szilverClassic = "/Users/istvan/GitHub/tableController/src/main/java/SZILVER.xls";
        String destination = "/Users/istvan/Documents/kir/Telephelyek/Rossz/";

        File path = new File(destination);

        File [] files = path.listFiles();
        for (int i = 0; i < files.length; i++){
            //a mac-es DS storet is kikell szurni...
            if (files[i].isFile() && files[i].toString().indexOf("DS_Store") == -1){ //this line weeds out other directories/folders
                System.out.println("\nFile: " + files[i]);
                if (readExcel.read(files[i].toString(), szilverClassic) == true){
                    System.out.println("Read oke\n\n");
                }
            }
        }

        System.out.println("PROGRAM EXIT");

    }
}


