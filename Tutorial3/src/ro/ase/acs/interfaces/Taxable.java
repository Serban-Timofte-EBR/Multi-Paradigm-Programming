package ro.ase.acs.interfaces;
//orice clasa derivata poate fi taxabila/impozitabila
    //oricine deriveaza clasa este obligat sa suprascrie computeTax pentru a calcula taxa
public interface Taxable {
    public static final int MIN_TAX = 50;   //final means const
    public double computeTax();

}
