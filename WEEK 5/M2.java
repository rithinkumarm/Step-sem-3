
public class M2 {

    static String classifyAccess(String fieldModifier,
                                 String accessorContext) {

        return switch (fieldModifier) {
            case "private" -> accessorContext.equals("SAME_CLASS")
                    ? "ALLOWED" : "DENIED";

            case "default" -> (accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE"))
                    ? "ALLOWED" : "DENIED";

            case "protected" -> (accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE")
                    || accessorContext.equals(
                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"))
                    ? "ALLOWED" : "DENIED";

            case "public" -> "ALLOWED";

            default -> "DENIED";
        };
    }

    static String firstDeniedAttempt(String[][] attempts) {
        for (int i = 0; i < attempts.length; i++) {
            String modifier = attempts[i][0];
            String context = attempts[i][1];

            if (classifyAccess(modifier, context).equals("DENIED")) {
                return modifier + " via " + context
                        + " (attempt #" + (i + 1) + ")";
            }
        }

        return "None Denied";
    }

    public static void main(String[] args) {
        String[][] attempts = {
            {"public", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"},
            {"protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"},
            {"protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"}
        };

        System.out.println(firstDeniedAttempt(attempts));
    }
}