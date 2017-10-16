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
    public HibasTanulo hibasTanulo[] = new HibasTanulo[300];
    public int index;
    //public String DEST = "/Users/istvan/Documents/kir/Telephelyek/CLASSIC/CSONGRÁD  Kossuth tér 6.         2017-2018. tanév.xls";
    //public String DEST_CLASSIC = "/Users/istvan/GitHub/tableController/src/main/java/CLASSIC.xls";
    //public String DEST_SZILVER = "/Users/istvan/GitHub/tableController/src/main/java/SZILVER.xls";
    public int rossz;
    public int nincsMeg;
    public boolean egyoszlop = true;

    public int ismetloIndex;
    public int hibasTanuloIndex = 0;


        public static final int sajatNev = 2;

        int sajatAnya = 8;
        int sajatSzuletes = 7;
        int sajatAzonosito = 5;

        public boolean datumcsere = true;

    ///////////////////////////READ METÓDUS///////////////////////////////
    //////////////////////////////////////////////////////////////////////
    public boolean read(String DEST, String szilverClassic) throws IOException {
        rossz = 0;
        nincsMeg = 0;
        hibasTanuloIndex = 0;


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



            int i = -1; ///AZ i az a lapok indexe
            //outerloop:
            do {
                start:
                i++; //lapszam
                for (int z = 0; z <3400; z++){
                    tanulo[z] = new Tanulo();
                }

                for (int z = 0; z < 300; z++){
                    hibasTanulo[z] = new HibasTanulo();
                }
                HSSFSheet sheet = workbook.getSheetAt(i);
                //System.out.println(workbook.getSheetName(i).toString());


                index = 0; //tanulo indexe
                // Ez egy lapon beluli sor indexe de 6-ot levag mert onnan kezdodnek a diakok ezert majd a diakok sorszama lesz
                int j = 0; //ez az oszlop indexe
                int kiszur = 5;
                String sorszam = ""; //HA NULLA MARAD AZ HIBA
                //////////////
                /////VEGIG MEGYEK AZ EXCELLEN
                ////////////
                Row row;
                //outerloop:
                for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator.hasNext();) {
                    row = rowIterator.next();
                    boolean hibasOM = false;
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
                                        //System.out.println("HIBA!!!: " + string);
                                        //System.out.println(tanulo[index].getNev() + "\n");
                                    }

//                                    if (string.contentEquals("00000000000")){
//                                        System.out.println("Hianyzó OM");
//                                        System.out.println(tanulo[index].getNev() + "\n");
//                                    }

                                    if (letezikeMar(string, index) && !string.contentEquals("00000000000")) {
                                        System.out.println("Ez az OM már létezik!: " + string + "\nIndex: " + index);
                                        System.out.println("lap: " + i);
                                        //System.out.println("Nev: " + tanulo[index].getNev() + "\n" + "\n");
                                        //string += "HIBA";
                                        //tanulo[index].setAzonosito(string);

                                        //ITT MASOLOM AZ ISMETLODOT A ROSSZBA MERT NEM TUDHATOM HOGY MELYIK A ROSSZ
                                        atmasolRosszba(i, ismetloIndex);
                                        tanulo[ismetloIndex].setHibas(true);
                                        hibasTanuloIndex++;

                                        //System.out.println("hibasTanuloIndex++1");

                                        //IDE TESZEM A MOSTANIT AKIVEL KAPTAM EGYFORMAT
                                        hibasTanulo[hibasTanuloIndex].setAzonosito(string);
                                        tanulo[index].setHibas(true);
                                        hibasTanulo[hibasTanuloIndex].setNev(tanulo[index].getNev());
                                        hibasTanulo[hibasTanuloIndex].setSorszam(index);
                                        hibasTanulo[hibasTanuloIndex].setLapszam(i);

                                        System.out.println("Ennek a két tanulónak ugyanaz az OM számja: " );
                                        System.out.println(tanulo[ismetloIndex].getNev());
                                        System.out.println(hibasTanulo[hibasTanuloIndex].getNev() + "\n\n");

                                    } else
                                        if (string.contentEquals("00000000000")){
                                            System.out.println("Hianyzó OM");
                                            System.out.println(tanulo[index].getNev() + "\n");

                                            tanulo[index].setHibas(true);
                                            hibasTanulo[hibasTanuloIndex].setNev(tanulo[index].getNev());
                                        }
                                        else{
                                        tanulo[index].setAzonosito(string);
                                    }
                                }

                                if (j == sajatNev) {
                                    String neve = cellData.toString();
                                    neve = replaceAtTheEnd(neve);
                                    tanulo[index].setNev(neve);
                                    tanulo[index].setSorszam(index);
                                    tanulo[index].setLapszam(i);
                                }

                                if (j == sajatAnya) {
                                    if (!tanulo[index].isHibas()) {
                                        tanulo[index].setAnyanev(cellData.toString());
                                    }else{
                                        hibasTanulo[hibasTanuloIndex].setAnyanev(cellData.toString());
                                    }
                                }

                                if (j == sajatSzuletes) {
                                    if (!tanulo[index].isHibas()) {
                                        tanulo[index].setSzuletes(cellData.toString());
                                    }else{
                                        hibasTanulo[hibasTanuloIndex].setSzuletes(cellData.toString());
                                    }
                                }

                        }
                        if (tanulo[index].isHibas()){
                            hibasTanuloIndex++;
                            //System.out.println("hibasTanuloIndex++2");
                        }
                        //////VEGIG MEGYEK A KIR sorain
                        KIRellenorzes(sheetExport, writeExcel, i, DEST);

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

                if (workbook.getSheetName(i).contentEquals("Ki2017.")){
                    break;
                }


            }while(!workbook.getSheetName(i).contentEquals("Ö.2017.") && !workbook.getSheetName(i).contentEquals("Ö.2017"));
            //for (int i = 0; i < workbook.getNumberOfSheets(); i++){








            //System.out.println("na: " + tanulo[1].getNev());
            System.out.println("\n\nEnnyi nem egyezik: " + rossz);
            System.out.println("Ennyit nem találtam meg az exportba: " + nincsMeg);
            System.out.println("Ennyinek hibás az OM számja: " + hibasTanuloIndex);



            file.close();
            fileExport.close();
            return true;
        }
        catch (Exception exception) {

            System.out.println("PROGRAM HIBA: " + exception);
        }
        return false;
    }
    ///////////////////////////////////////////////////////////////
    ////////////////////////READ VÉGE//////////////////////////////
    ///////////////////////////////////////////////////////////////

    public static String replaceAtTheEnd(String input){
        input = input.replaceAll("\\s+$", "");
        return input;
    }

    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    // LETEZIK MAR

    public boolean letezikeMar(String string, int index){
        boolean nemLetezik =  true;

        for (int i = 1; i < index; i++){
            if (tanulo[i].getAzonosito().toString().contentEquals(string) &&
                    !tanulo[index].getNev().toString().contentEquals(tanulo[i].getNev().toString())){
                ismetloIndex = i;
                nemLetezik = false;
            }
        }

        if (!nemLetezik){
            return true;
        }else{
            return false;
        }
    }

    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    //  ATMASOLROSSZBA

    public boolean atmasolRosszba(int tanuloIndex, int hibasTanuloIndex){
        //System.out.println(tanulo[tanuloIndex].getNev());
        hibasTanulo[hibasTanuloIndex].setNev(tanulo[tanuloIndex].getNev());
        hibasTanulo[hibasTanuloIndex].setAzonosito(tanulo[tanuloIndex].getAzonosito());
        hibasTanulo[hibasTanuloIndex].setAnyanev(tanulo[tanuloIndex].getAnyanev());
        hibasTanulo[hibasTanuloIndex].setSzuletes(tanulo[tanuloIndex].getSzuletes());
        hibasTanulo[hibasTanuloIndex].setSorszam(tanulo[tanuloIndex].getSorszam());
        hibasTanulo[hibasTanuloIndex].setLapszam(tanulo[tanuloIndex].getLapszam());
        return true;
    }

    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    ///// ISINTEGER


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

    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    ////   EGYOSZLOPOS


    public boolean egyoszlopos(String DEST) throws IOException {
        FileInputStream file = new FileInputStream(new File(DEST));
        HSSFWorkbook workbook = new HSSFWorkbook(file);
        HSSFSheet sheet = workbook.getSheetAt(0);
        boolean az = false;
        int kiszur = 7;
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

    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    ///// KIRELLENORZES

    public boolean KIRellenorzes(HSSFSheet sheetExport, WriteExcel writeExcel, int i, String DEST) throws IOException {
        boolean megvan = false;
        for (Iterator<Row> rowExportIterator = sheetExport.iterator(); rowExportIterator.hasNext();){
            Row rowExport = rowExportIterator.next();

            String azonositoExport = rowExport.cellIterator().next().toString();

            //string.indexOf('a')

            //////ITT KELL ELLENORIZNI HOGY HIBASE
            if (azonositoExport.contentEquals(tanulo[index].getAzonosito()) && !tanulo[index].isHibas()){
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


        if (!megvan && !tanulo[index].isHibas()){
            nincsMeg++;
            tanulo[index].setHibas(true);
            atmasolRosszba(index, hibasTanuloIndex);
            //hibasTanuloIndex++;
            System.out.println("Nem talaltam meg az exportba: \n" + tanulo[index].getNev() +
                    "\n" + tanulo[index].getAzonosito() + "\n");
        }
        return true;
    }

    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    /////   JAVIT HIBAS TANULO

    public boolean javitHibasTanulo(HSSFSheet sheetExport, WriteExcel writeExcel, String DEST ){

        ////LOOP AMI VEGIG MEGY A HIBAS TANULOKON
        for (int hibasTanulo_i = 0; hibasTanulo_i < hibasTanuloIndex; hibasTanulo_i++){

            boolean megvanNev = false;
            boolean megvanAnya = false;

            for (Iterator<Row> rowExportIterator = sheetExport.iterator(); rowExportIterator.hasNext();){
                    Row rowExport = rowExportIterator.next();
                    String nevExport = rowExport.cellIterator().next().toString();

                    //////ITT KELL ELLENORIZNI HOGY HIBASE
                        //System.out.println(index + " " + tanulo[index].getAzonosito());
                        int k = 0;
                        String ujAzonosito ="";
                        for (Iterator<Cell> cellExportIterator = rowExport.cellIterator(); cellExportIterator.hasNext(); ) {
                            Cell cellExportData = cellExportIterator.next();
                            k++;
                            switch (k) {
                                case 0:
                                    ujAzonosito = cellExportData.toString();
                                    break;

                                case 1:
                                    if (hibasTanulo[hibasTanulo_i].getNev().contentEquals(cellExportData.toString())){
                                        megvanNev = true;
                                    }
                                    break;

                                case 2:
                                    if (hibasTanulo[hibasTanulo_i].getAnyanev().contentEquals(cellExportData.toString())){
                                        megvanAnya = true;
                                    }
                                    break;

                                case 3:
                                    if (megvanNev && megvanAnya){
                                        System.out.println("Megtaláltam ennek a diák azonosítóját:" + hibasTanulo[hibasTanulo_i].getNev());
                                        System.out.println("Akinek az anyja: " + hibasTanulo[hibasTanulo_i].getAnyanev());
                                        hibasTanulo[hibasTanulo_i].setAzonosito(ujAzonosito);
                                        hibasTanulo[hibasTanulo_i].setSzuletes(cellExportData.toString());

                                                //KELL WRITEOLNI IS

                                        //////////////////KIR ELLENORZES (BORZASZTO MEGOLDAS...)
                                        /////////////////////////////////////////////////////

                                        //////ITT MOST NEM KELL VEGIG MENJEK ELEG HA MEGTUDOM HOGY HOL VAN ES ONNAN KIMASOLOM AZ ADATOKAT
                                        //////DE IGAZABOL CSAK A DATUMAT TUDOM KICSERELNI.... 2 adat kell beazanositani es 3-at javitok...
                                        ////max az azonositojat tudom betenni meg igaz akkor 2-ot javitok


                                        ///////////////
                                        //////////////////KIR ELLENORZES (BORZASZTO MEGOLDAS...)VEGE
                                        /////////////////////////////////////////////////////

                                    }
                            }
                        }
            }

        }
        return true;
    }

    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
}

