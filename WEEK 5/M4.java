
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class LibraryMember {
    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswerHash;

    public LibraryMember() {
    }

    public LibraryMember(String membershipId, String name, boolean premiumMember, String securityAnswerHash) {
        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = premiumMember;
        this.securityAnswerHash = securityAnswerHash;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String id) {
        if (membershipId == null && id != null) {
            membershipId = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    public void setSecurityAnswer(String answer) {
        if (answer == null) {
            securityAnswerHash = null;
            return;
        }

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(
                answer.getBytes(StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();

            for (byte b : hash) {
                result.append(String.format("%02x", b & 0xff));
            }

            securityAnswerHash = result.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getSecurityAnswerHash() {
        return securityAnswerHash;
    }

    public void setSecurityAnswerHash(String securityAnswerHash) {
        this.securityAnswerHash = securityAnswerHash;
    }
}

public class Main {
    public static void main(String[] args) {
        LibraryMember m = new LibraryMember();

        m.setMembershipId("LIB-8841");
        m.setName("Priya Nair");
        m.setPremiumMember(true);
        m.setSecurityAnswer("BlueMountain");

        m.setMembershipId("FAKE-0000");

        System.out.println(m.getMembershipId());
        System.out.println(m.getName());
        System.out.println(m.isPremiumMember());
    }
}