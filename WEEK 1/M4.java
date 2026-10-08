import java.util.Scanner;

public class M4 {

    static void analyzeInventory(int[] sectionA, int[] sectionB) {

        int totalA = 0;
        int totalB = 0;

        
        for (int i = 0; i < sectionA.length; i++) {
            totalA += sectionA[i];
        }

        for (int i = 0; i < sectionB.length; i++) {
            totalB += sectionB[i];
        }

        String status;

        if (totalA == totalB) {
            status = "Balanced";
        } 
        else {
            status = "Not Balanced";
        }

        int max = sectionA[0];
        String maxSection = "Section A";
        int maxIndex = 0;

      
        for (int i = 1; i < sectionA.length; i++) {

            if (sectionA[i] > max) {
                max = sectionA[i];
                maxSection = "Section A";
                maxIndex = i;
            }
        }

        for (int i = 0; i < sectionB.length; i++) {

            if (sectionB[i] > max) {
                max = sectionB[i];
                maxSection = "Section B";
                maxIndex = i;
            }
        }

        System.out.println("Section A Total: " + totalA
                + " | Section B Total: " + totalB
                + " | Status: " + status);

        System.out.println("Highest Quantity: " + max
                + " (" + maxSection
                + ", Item " + (maxIndex + 1) + ")");
    }

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            
            int[] sectionA = new int[n];
            int[] sectionB = new int[n];
            
            for (int i = 0; i < n; i++) {
                sectionA[i] = sc.nextInt();
            }
            
            for (int i = 0; i < n; i++) {
                sectionB[i] = sc.nextInt();
            }
            
            analyzeInventory(sectionA, sectionB);
        }
    }
}