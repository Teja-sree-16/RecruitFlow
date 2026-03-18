package Recruitflow;

import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;

public class RecruitFlow {

    static Student[] students = new Student[100];
    static int studentCount = 0;

    static Node head = null;

    static Student[] interviewQueue = new Student[100];
    static int front = 0;
    static int rear = -1;

    static CompanyDrive drive;
    static CircularSlotQueue slotQueue;

    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== RecruitFlow Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. Create Drive");
            System.out.println("3. Apply for Drive");
            System.out.println("4. Conduct Test & Shortlist");
            System.out.println("5. Schedule Interviews");
            System.out.println("6. Generate Final Report");
            System.out.println("7. Exit");

            int ch = sc.nextInt();

            switch (ch) {

                case 1: addStudent(); break;
                case 2: createDrive(); break;
                case 3: apply(); break;
                case 4: conductTestAndShortlist(); break;
                case 5: scheduleInterviews(); break;
                case 6: generateReport(); break;
                case 7: System.exit(0);
            }
        }
    }

    // ================= ADD STUDENT =================
    static void addStudent() {

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter CGPA: ");
        double cgpa = sc.nextDouble();

        students[studentCount++] = new Student(id, name, cgpa);

        System.out.println("Student Added Successfully.");
    }

    // ================= CREATE DRIVE (RUNTIME) =================
    static void createDrive() {

        sc.nextLine();

        System.out.print("Enter Company Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Minimum CGPA Required: ");
        double cgpa = sc.nextDouble();

        System.out.print("Enter Minimum Test Score Required: ");
        int testScore = sc.nextInt();

        System.out.print("Enter Total Interview Slots: ");
        int slots = sc.nextInt();

        drive = new CompanyDrive(name, cgpa, testScore, slots);

        System.out.println("Drive created successfully for " + drive.companyName);
    }

    // ================= APPLY =================
    static void apply() {

        if (drive == null) {
            System.out.println("Create Drive First!");
            return;
        }

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        for (int i = 0; i < studentCount; i++) {

            if (students[i].id == id) {

                if (students[i].cgpa >= drive.minCGPA) {

                    students[i].companyApplied = drive.companyName;
                    addApplicant(students[i]);

                    System.out.println("Applied Successfully.");
                } else {
                    System.out.println("Student not eligible (Low CGPA).");
                }
                return;
            }
        }

        System.out.println("Student not found.");
    }

    // ================= LINKED LIST ADD =================
    static void addApplicant(Student s) {

        Node newNode = new Node(s);

        if (head == null)
            head = newNode;
        else {
            Node temp = head;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newNode;
        }
    }

    // ================= TEST + SHORTLIST =================
    static void conductTestAndShortlist() {

        if (head == null) {
            System.out.println("No applicants available.");
            return;
        }

        Node temp = head;
        int index = 0;

        slotQueue = new CircularSlotQueue(drive.totalSlots);

        while (temp != null && index < drive.totalSlots) {

            int score = rand.nextInt(51) + 50;
            temp.data.testScore = score;

            System.out.println(temp.data.name + " scored " + score);

            if (score >= drive.minTestScore) {

                drive.selected[index] = temp.data;
                interviewQueue[++rear] = temp.data;
                index++;
            }

            temp = temp.next;
        }

        System.out.println("Shortlisting Completed.");
    }

    // ================= INTERVIEW SCHEDULING =================
    static void scheduleInterviews() {

        if (front > rear) {
            System.out.println("No shortlisted students.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter Interview Date (DD-MM-YYYY): ");
        String date = sc.nextLine();

        System.out.print("Enter Start Time (HH:MM): ");
        String time = sc.nextLine();

        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);

        System.out.println("\nInterview Schedule:");

        while (front <= rear) {

            String slot =
                    date + " " +
                    String.format("%02d:%02d", hour, minute);

            System.out.println(
                    interviewQueue[front].name + " -> " + slot);

            slotQueue.add(slot);

            front++;

            minute += 30;

            if (minute >= 60) {
                hour++;
                minute -= 60;
            }
        }
    }

    // ================= FINAL REPORT =================
    static void generateReport() {

        try {

            FileWriter fw = new FileWriter("final_report.csv");

            fw.write("ID,Name,CGPA,TestScore,Company\n");

            for (Student s : drive.selected) {
                if (s != null)
                    fw.write(s.toString() + "\n");
            }

            fw.close();

            System.out.println("Final Report Generated Successfully.");

        } catch (Exception e) {
            System.out.println("Error generating report.");
        }
    }
}