package ro.ase.acs.interfaces;

// functionally interface
//abstraction of binary operator
@FunctionalInterface
public interface BinaryOperation {
    double compute(double x, double y);
}
