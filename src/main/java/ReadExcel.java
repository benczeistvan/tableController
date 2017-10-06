import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.*;

/**
 * Created by lbene on 17.08.2017.
 */
public class ReadExcel {

    public Tanulo tanulo[] = new Tanulo[3400];
    public int index;
    //public String DEST = "/Users/istvan/Documents/kir/Telephelyek/CLASSIC/CSONGRÁD  Kossuth tér 6.         2017-2018. tanév.xls";
    //public String DEST_CLASSIC = "/Users/istvan/GitHub/tableController/src/main/java/CLASSIC.xls";
    //public String DEST_SZILVER = "/Users/istvan/GitHub/tableController/src/main/java/SZILVER.xls";
    public int rossz;
    public int nincsMeg;
    public boolean egyoszlop = true;


        public static final int sajatNev = 2;

        int sajatAnya = 8;
        int sajatSzuletes = 7;
        int sajatAzonosito = 5;

        public boolean datumcsere = false;

    public boolean read(String DEST, String szilverClassic) throws IOException {
        rossz = 0;
        nincsMeg = 0;

        if (egyoszlopos(DEST)){
            System.out.println("Egy oszlopos");
            sajatAnya = 7;
            sajatSzuletes = 6;
            sajatAzonosito = 4;
        }else{
            System.out.println("Ket oszlopos");
            sajatAnya = 8;
            sajatSzuletes = 7;
            sajatAzonosito = 5;
        }

        WriteExcel writeExcel = new WriteExcel();

        try {

            //
            FileInputStream file = new FileInputStream(new File(DEST));
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            FileInputStream fileExport = new FileInputStream(new File(szilverClassic));
            HSSFWorkbook workbookExport = new HSSFWorkbook(fileExport);
            HSSFSheet sheetExport = workbookExport.getSheetAt(0);

            //HSSFSheet sheet = workbook.getSheetAt(1);

            //System.out.println(workbook.getNumberOfSheets());

            for (int i = 0; i <3400; i++){
                tanulo[i] = new Tanulo();
            }

            int i = -1;
            //outerloop:
            do {
                start:
                i++;
                HSSFSheet sheet = workbook.getSheetAt(i);
                //System.out.println(workbook.getSheetName(i).toString());

                if (workbook.getSheetName(i).contentEquals("Ki2017.")){
                    return true;
                }

                index = 0;
                int j = 0;
                int kiszur = 5;
                String sorszam = ""; //HA NULLA MARAD AZ HIBA
                //////////////
                /////VEGIG MEGYEK AZ EXCELLEN
                ////////////
                Row row;
                //outerloop:
                for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator.hasNext();) {
                    row = rowIterator.next();
                    index++;
                    j = 0;
                    if (index > kiszur){
                        if (kiszur == 5) {
                            index = 1;
                        }
                        kiszur = 0;


                        if (row.cellIterator().hasNext()) {

                            if (row.cellIterator().next().toString().contentEquals("")) {
                                //System.out.println(row.cellIterator().next().toString());
                                break;
                            }
                            String ellenorzo = row.cellIterator().next().toString();
                            ellenorzo = ellenorzo.replace(".","");
                            if (!isInteger(ellenorzo)){
                                //System.out.println(row.cellIterator().next().toString());
                                //System.out.println("BREAK");
                                break;
                            }

                        }else{

                            break;
                        }





                        ///////////VEGIG MEGYEK A SAJAT EXCEL sorain
                        /////////////
                        for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext(); ) {
                            Cell cellData = cellIterator.next();
 //                            todo work with the data
                            j++;

//                                case 1:
//                                    if (cellData.toString() == ""){
//                                        System.out.println("BREAK " + index);
//                                        break outerloop;
//                                    }
//                                    break;
                                if (j == sajatAzonosito) {
                                    String string = cellData.toString();
                                    if (string.indexOf('.') != 0) {
                                        string = string.replace(".", "");
                                        string = string.replace("E10", "");
                                        string = string.replace("E9", "");
                                    }


                                    while (string.length() != 11) {
                                        string += "0";
                                    }

                                    if (string.indexOf('7') != 0) {
                                        System.out.println("HIBA!!!: " + string);
                                        System.out.println(tanulo[index].getNev() + "\n");
                                    }

                                    if (letezikeMar(string, index)) {
                                        System.out.println("Ez az OM már létezik!: " + string + "\nIndex: " + index);
                                        System.out.println("Nev: " + tanulo[i].getNev() + "\n" + "\n");
                                        //string += "HIBA";
                                        tanulo[index].setAzonosito(string);
                                    } else {
                                        tanulo[index].setAzonosito(string);
                                    }
                                }

                                if (j == sajatNev) {
                                    String neve = cellData.toString();
                                    neve = replaceAtTheEnd(neve);
                                    tanulo[index].setNev(neve);
                                }

                                if (j == sajatAnya) {
                                    tanulo[index].setAnyanev(cellData.toString());
                                }

                                if (j == sajatSzuletes) {
                                    tanulo[index].setSzuletes(cellData.toString());
                                }

                        }

                        //////VEGIG MEGYEK A KIR sorain
                        boolean megvan = false;
                        for (Iterator<Row> rowExportIterator = sheetExport.iterator(); rowExportIterator.hasNext();){
                            Row rowExport = rowExportIterator.next();

                            String azonositoExport = rowExport.cellIterator().next().toString();

                            //string.indexOf('a')


                            if (azonositoExport.contentEquals(tanulo[index].getAzonosito())){
                                megvan = true;
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
                                                System.out.println("\nHibás név:");
                                                System.out.println("KIR: " + kirAdat + "\nGABI: " + tanulo[index].getNev());
                                                System.out.println("Nem egyezik: " + tanulo[index].getAzonosito() + "\n");
                                                //kirAdat += " JAVITVA";
                                                //System.out.println(index);
                                                //Az indexhez annyit kell hozzaadni amennyivel csuszik a sorszam az excelhez kepest
                                                writeExcel.write(kirAdat, index + 5, sajatNev, i, DEST);
                                                ///IDE KELL BETENNI A VALTOZTATAS A WRITEEXLCELT
                                            }
                                            break;

                                        case 3:
                                            if (!cellExportData.toString().contentEquals(tanulo[index].getAnyanev())) {
                                                rossz++;
                                                String kirAdat = cellExportData.toString();
                                                System.out.println("\nHibás Anyja neve:");
                                                System.out.println("KIR: " + kirAdat + "\nGABI: " + tanulo[index].getAnyanev());
                                                System.out.println("Nem egyezik: " + tanulo[index].getAzonosito() + "\n");
                                                //kirAdat += " JAVITVA";
                                                //System.out.println(index);
                                                //Az indexhez annyit kell hozzaadni amennyivel csuszik a sorszam az excelhez kepest
                                                writeExcel.write(kirAdat, index + 5, sajatAnya, i, DEST);
                                            }
                                            break;

                                        case 4:
//                                                String datum = cellExportData.toString();
//                                                String nap;
//                                                String honap;
//                                                String ev;
//                                                String datum_KIR_jo;
//
//                                                ev = datum.substring(0, datum.indexOf('.'));
//                                                datum = datum.replace(ev + ".", "");
//
//                                                honap = datum.substring(1, datum.indexOf('.'));
//                                                datum = datum.replace(ev + ".", "");
//
//                                                nap = datum.substring(1, datum.indexOf('.'));
//
//                                                datum_KIR_jo = ev + "/" + honap + "/" + nap;
//                                                //System.out.println(datum_KIR_jo + " " + (index+5) + " " + i);
                                                //System.out.print(".");
                                                if (datumcsere  && cellExportIterator.hasNext()) {
                                                    writeExcel.write(cellExportData.toString(), index + 5, sajatSzuletes, i, DEST);
                                                    //System.out.println(".");
                                                }

                                            break;

                                    }
                                }

                            }else{
                               // System.out.println("");
                            }


                                //if (cellExportData.toString() != tanulo[index].get)
                        }


                        if (!megvan){
                            nincsMeg++;
                            System.out.println("Nem talaltam meg az exportba: \n" + tanulo[index].getNev() +
                                    "\n" + tanulo[index].getAzonosito() + "\n");
                        }

                        // System.out.println(tanulo[index].getNev());
                    }

                }

                //System.out.println(i);
                if (i == workbook.getNumberOfSheets() - 1){
                    if (!workbook.getSheetName(i).contentEquals("Ö.2017.")) {
                        System.out.println("Ebben az excelbe nem létezik Ö.2017. lap");
                        System.out.println("Az utolso lap neve: " + workbook.getSheetName(i));
                    }
                    break;
                }


            }while(!workbook.getSheetName(i).contentEquals("Ö.2017."));
            //for (int i = 0; i < workbook.getNumberOfSheets(); i++){








            //System.out.println("na: " + tanulo[1].getNev());
            System.out.println("\n\nEnnyi nem egyezik: " + rossz);
            System.out.println("Ennyit nem találtam meg az exportba: " + nincsMeg);
            file.close();
            fileExport.close();
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

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }


    public boolean egyoszlopos(String DEST) throws IOException {
        FileInputStream file = new FileInputStream(new File(DEST));
        HSSFWorkbook workbook = new HSSFWorkbook(file);
        HSSFSheet sheet = workbook.getSheetAt(0);
        boolean az = false;
        int kiszur = 5;
        int sajatIndex= 0;

        overloop:
        for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator.hasNext();) {
            Row row = rowIterator.next();
            int j = 0;
            sajatIndex++;
            if (sajatIndex > kiszur) {
                if (kiszur == 5) {
                    sajatIndex= 1;
                }
                kiszur = 0;


                for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext(); ) {
                    j++;
                    Cell cellData = cellIterator.next();
                    if (j == 4) {
                        if (cellData.toString().length() > 4) {
                            //System.out.println(cellData.toString());
                            az = true;
                            return az;
                        }
                        return az;
                    }
                }
            }
        }
        file.close();

        if (az){
            return true;
        }else{
            return false;
        }
    }

}

