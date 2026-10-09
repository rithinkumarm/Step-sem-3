
public class M1 {
    public static void main(String[] args) {
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");

        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        m1.startWash(asha, new QuickWash());
        m1.startWash(ravi, new HeavyWash());
        m2.startWash(ravi, new HeavyWash());

        m1.completeWash();
        m1.startWash(neha, new NormalWash());
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

abstract class WashType {
    public abstract int getDuration();
    public abstract double getCharge();
    public abstract String getName();
}

class QuickWash extends WashType {
    public int getDuration() {
        return 30;
    }

    public double getCharge() {
        return 20;
    }

    public String getName() {
        return "Quick";
    }
}

class NormalWash extends WashType {
    public int getDuration() {
        return 45;
    }

    public double getCharge() {
        return 30;
    }

    public String getName() {
        return "Normal";
    }
}

class HeavyWash extends WashType {
    public int getDuration() {
        return 60;
    }

    public double getCharge() {
        return 45;
    }

    public String getName() {
        return "Heavy";
    }
}

class WashCycle {
    private final Student student;
    private final WashType type;

    public WashCycle(Student student, WashType type) {
        this.student = student;
        this.type = type;
    }

    public Student getStudent() {
        return student;
    }

    public WashType getType() {
        return type;
    }
}

class WashingMachine {
    private final String id;
    private WashCycle currentCycle;

    public WashingMachine(String id) {
        this.id = id;
    }

    public void startWash(Student student, WashType type) {
        if (currentCycle != null) {
            System.out.println("Machine " + id + " is currently busy.");
            return;
        }

        if (student == null || type == null) {
            System.out.println("Cannot start wash: invalid student or wash type.");
            return;
        }

        currentCycle = new WashCycle(student, type);

        System.out.printf(
                "%s wash started on %s for %s (%d min). Charge: ₹%.2f.%n",
                type.getName(), id, student.getName(),
                type.getDuration(), type.getCharge()
        );
    }

    public void completeWash() {
        if (currentCycle == null) {
            System.out.println("Machine " + id + " has no active wash.");
            return;
        }

        System.out.println(id + " cycle completed.");
        currentCycle = null;
        System.out.println(id + " is now free.");
    }
}