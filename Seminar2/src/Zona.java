//Enum este o clasa in care avem deja setate instantele ( DOROBANTI, AVIATIEI, etc )
public enum Zona {
    DOROBANTI(1), AVIATIEI(1), DRUMUL_TABEREI(6), BERCENI(4), RAHOVA(5), TEI(2);
    private int sector;

    Zona(int sector) {
        this.sector = sector;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

}
