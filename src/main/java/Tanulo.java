import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tanulo {
    String azonosito;
    String nev;
    String anyanev;
    String szuletes;
    String hely;
    String evfolyam;
    String beirasinaplo;
    String sornaploszam;
    boolean hibas;
    int lapszam;
    int sorszam;

    public Tanulo(){
        this.azonosito = "";
        this.nev = "";
        this.nev = "";
        this.anyanev = "";
        this.szuletes = "";
        this.hely = "";
        this.evfolyam = "";
        this.beirasinaplo = "";
        this.sornaploszam = "";
        this.hibas = false;
        this.lapszam = 0;
        this.sorszam = 0;

    }

    public String getAzonosito() {
        return azonosito;
    }

    public void setAzonosito(String azonosito) {
        this.azonosito = azonosito;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getAnyanev() {
        return anyanev;
    }

    public void setAnyanev(String anyanev) {
        this.anyanev = anyanev;
    }

    public String getSzuletes() {
        return szuletes;
    }

    public void setSzuletes(String szuletes) {
        this.szuletes = szuletes;
    }

    public String getHely() {
        return hely;
    }

    public void setHely(String hely) {
        this.hely = hely;
    }

    public String getEvfolyam() {
        return evfolyam;
    }

    public void setEvfolyam(String evfolyam) {
        this.evfolyam = evfolyam;
    }

    public String getBeirasinaplo() {
        return beirasinaplo;
    }

    public void setBeirasinaplo(String beirasinaplo) {
        this.beirasinaplo = beirasinaplo;
    }

    public String getSornaploszam() {
        return sornaploszam;
    }

    public void setSornaploszam(String sornaploszam) {
        this.sornaploszam = sornaploszam;
    }

    public boolean isHibas() {
        return hibas;
    }

    public void setHibas(boolean hibas) {
        this.hibas = hibas;
    }

    public int getLapszam() {
        return lapszam;
    }

    public void setLapszam(int lapszam) {
        this.lapszam = lapszam;
    }

    public int getSorszam() {
        return sorszam;
    }

    public void setSorszam(int sorszam) {
        this.sorszam = sorszam;
    }
}
