class MembershipCard {
    String studentName;

    static {
        System.out.println("Library info loaded");
    }

    MembershipCard(String studentName) {
        this.studentName = studentName;
    }

    public static void main(String[] args) {
        String[] names = {
            "Ananya", "Rohan", "Priya", "Arjun", "Sneha"
        };

        for (String name : names) {
            MembershipCard card = new MembershipCard(name);
            System.out.println("Membership card issued: "
                    + card.studentName);
        }
    }
}