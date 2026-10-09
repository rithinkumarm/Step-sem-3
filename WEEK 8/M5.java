
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class M5 {
    public static void main(String[] args) {
        Student asha = new Student("Asha", "CSE");
        asha.addChannel(new EmailChannel());
        asha.addChannel(new AppChannel());

        Student ravi = new Student("Ravi", "ECE");
        ravi.addChannel(new SmsChannel());

        NoticeBoard board = new NoticeBoard();
        board.addStudent(asha);
        board.addStudent(ravi);

        board.postNotice(new Notice(
            "Lab Closed Tomorrow",
            Arrays.asList("CSE")
        ));

        board.postNotice(new Notice(
            "Fee Deadline Extended",
            Arrays.asList("CSE", "ECE")
        ));

        board.postNotice(new Notice(
            "Sports Day",
            Collections.emptyList()
        ));
    }
}

class Student {
    private String name;
    private String department;
    private List<NotificationChannel> channels;

    public Student(String name, String department) {
        if (name == null || name.trim().isEmpty()
                || department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Student name and department are required."
            );
        }

        this.name = name;
        this.department = department;
        this.channels = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public void addChannel(NotificationChannel channel) {
        if (channel != null) {
            channels.add(channel);
        }
    }

    public List<NotificationChannel> getChannels() {
        return Collections.unmodifiableList(channels);
    }
}

class Notice {
    private String title;
    private Set<String> departments;

    public Notice(String title, List<String> departments) {
        this.title = title;
        this.departments = new LinkedHashSet<>();

        if (departments != null) {
            for (String department : departments) {
                if (department != null
                        && !department.trim().isEmpty()) {
                    this.departments.add(department);
                }
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getDepartments() {
        return Collections.unmodifiableSet(departments);
    }

    public boolean isValid() {
        return title != null
            && !title.trim().isEmpty()
            && !departments.isEmpty();
    }
}

interface NotificationChannel {
    void send(Student student, Notice notice);
}

class EmailChannel implements NotificationChannel {
    public void send(Student student, Notice notice) {
        System.out.println(
            "[Email → " + student.getName() + "] "
            + notice.getTitle()
        );
    }
}

class SmsChannel implements NotificationChannel {
    public void send(Student student, Notice notice) {
        System.out.println(
            "[SMS → " + student.getName() + "] "
            + notice.getTitle()
        );
    }
}

class AppChannel implements NotificationChannel {
    public void send(Student student, Notice notice) {
        System.out.println(
            "[App → " + student.getName() + "] "
            + notice.getTitle()
        );
    }
}

class NoticeBoard {
    private List<Student> students;

    public NoticeBoard() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        if (student != null) {
            students.add(student);
        }
    }

    public void postNotice(Notice notice) {
        if (notice == null || !notice.isValid()) {
            System.out.println(
                "Cannot post notice: At least one target department is required."
            );
            return;
        }

        System.out.println(
            "Notice '" + notice.getTitle()
            + "' posted to "
            + String.join(", ", notice.getDepartments()) + "."
        );

        for (Student student : students) {
            if (notice.getDepartments().contains(
                    student.getDepartment())) {

                for (NotificationChannel channel
                        : student.getChannels()) {
                    channel.send(student, notice);
                }
            }
        }
    }
}