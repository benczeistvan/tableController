import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

/**
 * Created by lbene on 17.08.2017.
 */
public class ReadExcel {

    public Tanulo tanulo[] = new Tanulo[3400];
    public int index;
    public String DEST = "/Users/istvan/GitHub/BAKS.xls";
    public String DEST_CLASSIC = "/Users/istvan/GitHub/tableController/src/main/java/CLASSIC.xls";
    public String DEST_SZILVER = "/Users/istvan/GitHub/tableController/src/main/java/SZILVER.xls";
    public int rossz;


    public boolean read() {
        rossz = 0;

        WriteExcel writeExcel = new WriteExcel();

        try {

            //
            FileInputStream file = new FileInputStream(new File(DEST));
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            FileInputStream fileExport = new FileInputStream(new File(DEST_CLASSIC));
            HSSFWorkbook workbookExport = new HSSFWorkbook(fileExport);
            HSSFSheet sheetExport = workbookExport.getSheetAt(0);

            //HSSFSheet sheet = workbook.getSheetAt(1);

            //System.out.println(workbook.getNumberOfSheets());

            for (int i = 0; i <3400; i++){
                tanulo[i] = new Tanulo();
            }

            int i = -1;
            do {
                i++;
                HSSFSheet sheet = workbook.getSheetAt(i);
                //System.out.println(workbook.getSheetName(i).toString());

                index = 0;
                int j = 0;
                int kiszur = 5;
                String sorszam = ""; //HA NULLA MARAD AZ HIBA
                //////////////
                /////VEGIG MEGYEK AZ EXCELLEN
                ////////////
                outerloop:
                for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator.hasNext();) {
                    Row row = rowIterator.next();
                    index++;
                    j = 0;
                    if (index > kiszur){
                        if (kiszur == 5) {
                            index = 1;
                        }
                        kiszur = 0;

                        if (row.cellIterator().next().toString().contentEquals("")){
                            break;
                        }

                        for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext(); ) {
                            Cell cellData = cellIterator.next();
//                            if (cellData.toString() == ""){
//                                System.out.println("BREAK");
//                                break;
//                            }
                            // todo work with the data
                            j++;
                            switch (j) {
                                case 5:
                                    String string = cellData.toString();
                                    if (string.indexOf('.') != 0){
                                        string = string.replace(".","");
                                        string = string.replace("E10", "");
                                    }
                                    while (string.length() != 11){
                                        string += "0";
                                    }

                                    if (string.indexOf('7') !=0 ){
                                        System.out.println("HIBA!!!: " + string);
                                    }

                                    if (letezikeMar(string, index)){
                                        System.out.println("Ez az OM már létezik!: " + string + "\n");
                                        tanulo[index].setAzonosito("HIBA");
                                    }else {
                                        tanulo[index].setAzonosito(string);
                                    }
                                    break;
                                case 2:
                                    String neve = cellData.toString();
                                    neve = replaceAtTheEnd(neve);
                                    tanulo[index].setNev(neve);
                                    break;
                                case 8:
                                    tanulo[index].setAnyanev(cellData.toString());
                                    break;
                                case 7:
                                    tanulo[index].setSzuletes(cellData.toString());
                                    break;
                                case 6:
                                    tanulo[index].setHely(cellData.toString());
                                    break;



                                default:
                                    break;
                            }
                        }
                        //System.out.println(index + " " + tanulo[index].getAzonosito());

                        for (Iterator<Row> rowExportIterator = sheetExport.iterator(); rowExportIterator.hasNext();){
                            Row rowExport = rowExportIterator.next();

                            String azonositoExport = rowExport.cellIterator().next().toString();

                            //string.indexOf('a')


                            if (azonositoExport.contentEquals(tanulo[index].getAzonosito())){
                               //System.out.println(index + " " + tanulo[index].getAzonosito());
                                int k = 0;
                                for (Iterator<Cell> cellExportIterator = rowExport.cellIterator(); cellExportIterator.hasNext(); ) {
                                    Cell cellExportData = cellExportIterator.next();
                                    k++;

                                    switch (k) {
                                        case 2:

                                            if (!cellExportData.toString().contentEquals(tanulo[index].getNev())) {
                                                rossz++;
                                                String kirAdat = cellExportData.toString();
                                                System.out.println("KIR: " + kirAdat + "\nGABI: " + tanulo[index].getNev());
                                                System.out.println("Nem egyezik: " + tanulo[index].getAzonosito() + "\n");
                                                //kirAdat += " JAVITVA";
                                                //System.out.println(index);
                                                //Az indexhez annyit kell hozzaadni amennyivel csuszik a sorszam az excelhez kepest
                                                writeExcel.write(kirAdat, index + 5, 2, i, DEST);
                                                ///IDE KELL BETENNI A VALTOZTATAS A WRITEEXLCELT
                                            }
                                    }
                                }

                            }else{
                               // System.out.println("");
                            }


                                //if (cellExportData.toString() != tanulo[index].get)
                        }




                        // System.out.println(tanulo[index].getNev());
                    }

                }


            }while(!workbook.getSheetName(i).contentEquals("Ö.2017."));
            //for (int i = 0; i < workbook.getNumberOfSheets(); i++){








            //System.out.println("na: " + tanulo[1].getNev());
            System.out.println("\n\nEnnyi nem egyezik: " + rossz);
            return true;
        }
        catch (Exception exception) {

            System.out.println("HIBA BASSZAMEG: " + exception);
        }

        return false;
    }

    public static String replaceAtTheEnd(String input){
        input = input.replaceAll("\\s+$", "");
        return input;
    }


    public boolean letezikeMar(String string, int index){
        boolean nemLetezik =  true;

        for (int i = 1; i < index; i++){
            if (tanulo[i].getAzonosito().toString().contentEquals(string) &&
                    !tanulo[index].getNev().toString().contentEquals(tanulo[i].getNev().toString())){
                nemLetezik = false;
            }
        }

        if (!nemLetezik){
            return true;
        }else{
            return false;
        }
    }
}

