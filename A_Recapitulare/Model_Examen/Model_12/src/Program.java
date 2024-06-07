public class Program {
    public int codProgram;
    public String numeProgram;
    public int numarLocuri;

    public int getCodProgram() {
        return codProgram;
    }

    public void setCodProgram(int codProgram) {
        this.codProgram = codProgram;
    }

    public String getNumeProgram() {
        return numeProgram;
    }

    public void setNumeProgram(String numeProgram) {
        this.numeProgram = numeProgram;
    }

    public int getNumarLocuri() {
        return numarLocuri;
    }

    public void setNumarLocuri(int numarLocuri) {
        this.numarLocuri = numarLocuri;
    }

    public Program(int codProgram, String numeProgram, int numarLocuri) {
        this.codProgram = codProgram;
        this.numeProgram = numeProgram;
        this.numarLocuri = numarLocuri;
    }

    public Program() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Program{");
        sb.append("codProgram=").append(codProgram);
        sb.append(", numeProgram='").append(numeProgram).append('\'');
        sb.append(", numarLocuri=").append(numarLocuri);
        sb.append('}');
        return sb.toString();
    }
}
