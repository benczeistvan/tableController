public class HibasTanulo {
    String azonosito;
    String nev;
    String anyanev;
    String szuletes;
    int lapszam;
    int sorszam;

    public HibasTanulo() {
        this.azonosito = "";
        this.nev = "";
        this.anyanev = "";
        this.szuletes = "";
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
