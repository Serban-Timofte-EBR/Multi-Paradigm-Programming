package ro.ase.acs.classes;

public final class Student {  //cannot extend it
    private String name;
    private float grade;
    private final int MINIMUM_PASS_GRADE = 5;
    private final String UNIVERSITY;

    public Student() {
        UNIVERSITY = "ASE";
    }

    public Student(String name, float grade) {
        this.name = name;
        this.grade = grade;
        UNIVERSITY = "ASE";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    @Override
    public final String toString() {    //cannot override anymore
        return "Student{" +
                "name='" + name + '\'' +
                ", grade=" + grade +
                '}';
    }
}
