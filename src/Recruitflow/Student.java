package Recruitflow;

public class Student {

    int id;
    String name;
    double cgpa;
    int testScore;
    String companyApplied;

    public Student(int id, String name, double cgpa) {
        this.id = id;
        this.name = name;
        this.cgpa = cgpa;
        this.testScore = -1;
        this.companyApplied = "";
    }

    public String toString() {
        return id + "," + name + "," + cgpa + "," + testScore + "," + companyApplied;
    }
}