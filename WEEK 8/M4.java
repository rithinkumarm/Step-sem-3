
public class M4 {
    public static void main(String[] args) {
        Member asha = new Member("Asha");
        Member ravi = new Member("Ravi");

        Membership a = asha.buyMembership(new QuarterlyPlan());
        Membership r = ravi.buyMembership(new MonthlyPlan());

        a.checkIn();
        a.freeze();
        a.checkIn();

        r.expire();
        r.freeze();
    }
}

class Member {
    private String name;

    public Member(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Membership buyMembership(MembershipPlan plan) {
        if (plan == null) {
            throw new IllegalArgumentException("Plan cannot be null.");
        }

        Membership membership = new Membership(this, plan);

        System.out.printf(
            "%s membership created for %s. Fee: ₹%.2f. Status: Active.%n",
            plan.getName(), name, membership.getFee()
        );

        return membership;
    }
}

interface MembershipPlan {
    int getMonths();
    double getDiscount();
    String getName();

    default double calculateFee() {
        return 1000.0 * getMonths() * (1.0 - getDiscount());
    }
}

class Membership {
    private Member member;
    private MembershipPlan plan;
    private String status;

    public Membership(Member member, MembershipPlan plan) {
        if (member == null || plan == null) {
            throw new IllegalArgumentException(
                "Member and plan are required."
            );
        }

        this.member = member;
        this.plan = plan;
        this.status = "Active";
    }

    public double getFee() {
        return plan.calculateFee();
    }

    public String getStatus() {
        return status;
    }

    public void checkIn() {
        if (status.equals("Active")) {
            System.out.println(
                member.getName() + " checked in successfully."
            );
        } else {
            System.out.println(
                "Check-in denied: " + member.getName()
                + "'s membership is " + status + "."
            );
        }
    }

    public void freeze() {
        switch (status) {
            case "Expired" -> System.out.println("Cannot freeze an Expired membership.");
            case "Frozen" -> System.out.println("Membership is already Frozen.");
            default -> {
                status = "Frozen";
                System.out.println(
                        member.getName() + "'s membership frozen."
                );  System.out.println("Status: Frozen.");
            }
        }
    }

    public void unfreeze() {
        switch (status) {
            case "Expired" -> System.out.println("Cannot unfreeze an Expired membership.");
            case "Frozen" -> {
                status = "Active";
                System.out.println(
                        member.getName() + "'s membership unfrozen."
                );  System.out.println("Status: Active.");
            }
            default -> System.out.println("Membership is already Active.");
        }
    }

    public void expire() {
        if (status.equals("Expired")) {
            System.out.println("Membership is already Expired.");
            return;
        }

        status = "Expired";
        System.out.println(
            member.getName() + "'s membership expired."
        );
        System.out.println("Status: Expired.");
    }
}