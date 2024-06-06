public class Program {
    private int codProgram;
    private String codFacultate;
    private String denumire;
    private int nrLocuri;

    public int getCodProgram() {
        return codProgram;
    }

    public void setCodProgram(int codProgram) {
        this.codProgram = codProgram;
    }

    public String getCodFacultate() {
        return codFacultate;
    }

    public void setCodFacultate(String codFacultate) {
        this.codFacultate = codFacultate;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public Program(int codProgram, String codFacultate, String denumire, int nrLocuri) {
        this.codProgram = codProgram;
        this.codFacultate = codFacultate;
        this.denumire = denumire;
        this.nrLocuri = nrLocuri;
    }

    public Program() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Program{");
        sb.append("codProgram=").append(codProgram);
        sb.append(", codFacultate='").append(codFacultate).append('\'');
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", nrLocuri=").append(nrLocuri);
        sb.append('}');
        return sb.toString();
    }
}
