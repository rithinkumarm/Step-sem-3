
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class M2 {
    public static void main(String[] args) {
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");

        Assignment coding = new CodingAssignment(
                "Linked List Lab", 50, LocalDate.of(2026, 3, 10));
        Assignment written = new WrittenAssignment(
                "Design Essay", 50, LocalDate.of(2026, 3, 12));

        Submission s1 = new Submission(asha, coding);
        Submission s2 = new Submission(ravi, written);

        s1.submit(LocalDate.of(2026, 3, 10));
        s2.submit(LocalDate.of(2026, 3, 14));

        s1.grade(45);
        s2.grade(40);

        s1.submit(LocalDate.of(2026, 3, 11));
    }
}

class Student {
    private final String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

abstract class Assignment {
    private final String title;
    private final int maxMarks;
    private final LocalDate dueDate;

    public Assignment(String title, int maxMarks, LocalDate dueDate) {
        if (title == null || title.trim().isEmpty()
                || maxMarks <= 0 || dueDate == null) {
            throw new IllegalArgumentException("Invalid assignment details.");
        }

        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public abstract double getDailyPenaltyRate();

    public double applyPenalty(double marks, long lateDays) {
        double penalty = getDailyPenaltyRate() * lateDays;
        return Math.max(0, marks * (1 - penalty));
    }
}

class CodingAssignment extends Assignment {
    public CodingAssignment(String title, int maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    public double getDailyPenaltyRate() {
        return 0.10;
    }
}

class WrittenAssignment extends Assignment {
    public WrittenAssignment(String title, int maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    public double getDailyPenaltyRate() {
        return 0.20;
    }
}

class Submission {
    private final Student student;
    private final Assignment assignment;
    private LocalDate submissionDate;
    private String status = "Not Submitted";

    public Submission(Student student, Assignment assignment) {
        if (student == null || assignment == null) {
            throw new IllegalArgumentException("Student and assignment are required.");
        }

        this.student = student;
        this.assignment = assignment;
    }

    public void submit(LocalDate date) {
        if (status.equals("Graded")) {
            System.out.println("Cannot resubmit: '" +
                    assignment.getTitle() + "' has already been graded.");
            return;
        }

        if (status.equals("Submitted")) {
            System.out.println("Cannot resubmit: submission already received.");
            return;
        }

        if (date == null) {
            System.out.println("Cannot submit: invalid date.");
            return;
        }

        submissionDate = date;
        status = "Submitted";

        long lateDays = Math.max(0,
                ChronoUnit.DAYS.between(assignment.getDueDate(), date));

        System.out.println(student.getName() + "'s submission for '" +
                assignment.getTitle() + "' received (" +
                (lateDays == 0 ? "on time" : lateDays + " days late") +
                "). Status: Submitted.");
    }

    public void grade(double marks) {
        if (!status.equals("Submitted")) {
            System.out.println("Cannot grade: work has not been submitted.");
            return;
        }

        if (marks < 0 || marks > assignment.getMaxMarks()) {
            System.out.println("Invalid marks.");
            return;
        }

        long lateDays = Math.max(0,
                ChronoUnit.DAYS.between(
                        assignment.getDueDate(), submissionDate));

        double finalMarks = assignment.applyPenalty(marks, lateDays);
        status = "Graded";

        System.out.printf("%s graded: %.0f/%d",
                student.getName(), finalMarks, assignment.getMaxMarks());

        if (lateDays > 0) {
            double penaltyPercent = Math.min(
                    100, assignment.getDailyPenaltyRate() * lateDays * 100);

            System.out.printf(" after %.0f%% late penalty", penaltyPercent);
        }

        System.out.println(". Status: Graded.");
    }
}