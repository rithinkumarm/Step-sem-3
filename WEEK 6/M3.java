import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        PremiumMember p = new PremiumMember("MEM5", 2000, "Coach Riya");

        p.chargeLateFee(200);

        System.out.println(p.getTotalLateFees());

        int[] history = p.getLateFeeHistory();
        history[0] = 999;

        System.out.println(Arrays.toString(p.getLateFeeHistory()));
    }
}

class GymMember {
    private String memberId;
    private int monthlyFee;
    private int[] lateFeeHistory = new int[10];
    private int lateFeeCount;
    private int totalLateFees;

    public GymMember(String memberId, int monthlyFee) {
        if (memberId == null || memberId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        if (monthlyFee <= 0) {
            throw new IllegalArgumentException("Invalid monthly fee");
        }

        this.memberId = memberId;
        this.monthlyFee = monthlyFee;
    }

    protected void chargeLateFee(int amount) {
        if (lateFeeCount >= lateFeeHistory.length) {
            throw new IllegalStateException("Late fee history is full");
        }

        lateFeeHistory[lateFeeCount++] = amount;
        totalLateFees += amount;
    }

    public int[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, lateFeeCount);
    }

    public int getTotalLateFees() {
        return totalLateFees;
    }
}

class PremiumMember extends GymMember {
    private String trainerName;

    public PremiumMember(String memberId, int monthlyFee, String trainerName) {
        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }

    @Override
    protected void chargeLateFee(int amount) {
        super.chargeLateFee(amount / 2);
    }
}