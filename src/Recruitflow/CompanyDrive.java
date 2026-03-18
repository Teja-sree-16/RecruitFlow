package Recruitflow;

public class CompanyDrive {

    String companyName;
    double minCGPA;
    int minTestScore;
    int totalSlots;

    Student[] selected;

    public CompanyDrive(String name, double cgpa, int test, int slots) {
        companyName = name;
        minCGPA = cgpa;
        minTestScore = test;
        totalSlots = slots;

        selected = new Student[slots];
    }
}