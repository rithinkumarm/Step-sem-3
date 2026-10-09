public class M5 {
    public static void main(String[] args) {
        GymMember m1 = new GymMember(1000);

        System.out.println(m1.membershipNumber);
        System.out.println(GymMember.getMembersEnrolled());

        System.out.println(GymMember.isValidReferralCode("G45B"));
        System.out.println(GymMember.isValidReferralCode("G4B"));
        System.out.println(GymMember.isValidReferralCode("X45B"));

        m1.payFee(500);
        m1.payFee(500, "UPI");

        System.out.println(m1.getFeesPaid());

        GymMember[] members = {
            new GroupClassMember(1500, "Zumba"),
            null,
            new GymMember(1000)
        };

        System.out.println(GymMember.processWeeklyCheckIn(members));
    }
}

class GymMember {
    private static int memberCounter = 0;
    private static int membersEnrolled = 0;

    public final String membershipNumber;

    private int feesPaid;

    public GymMember(int monthlyFee) {
        if (monthlyFee <= 0) {
            throw new IllegalArgumentException("Monthly fee must be positive");
        }

        memberCounter++;
        membersEnrolled++;
        membershipNumber = "GYM-" + (2000 + memberCounter);
    }

    public void payFee(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment must be positive");
        }

        feesPaid += amount;
    }

    public void payFee(int amount, String mode) {
        payFee(amount);
    }

    public int getFeesPaid() {
        return feesPaid;
    }

    public static int getMembersEnrolled() {
        return membersEnrolled;
    }

    public static boolean isValidReferralCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }

        return code.charAt(0) == 'G'
            && Character.isDigit(code.charAt(1))
            && Character.isDigit(code.charAt(2))
            && Character.isUpperCase(code.charAt(3));
    }

    public static String processWeeklyCheckIn(GymMember[] members) {
        int processed = 0;
        int nullSkipped = 0;
        int groupCount = 0;
        int individualCount = 0;

        for (GymMember member : members) {
            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (member instanceof GroupClassMember) {
                groupCount++;
            } else {
                individualCount++;
            }
        }

        return processed + " processed | "
            + nullSkipped + " null skipped | "
            + groupCount + " group | "
            + individualCount + " individual";
    }
}

class GroupClassMember extends GymMember {
    public GroupClassMember(int monthlyFee, String className) {
        super(monthlyFee);
    }
}