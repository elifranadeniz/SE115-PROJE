// Main.java — Students version
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {


    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;

    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    static int[][][] profitData = new int[MONTHS][DAYS][COMMS];

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {

        for (int m = 0; m < MONTHS; m++) {
            String fileName = "Data_Files/" + months[m] + ".txt";

            try {
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                String line;

                while ((line = br.readLine()) != null) {

                    String[] parts = line.split(",");

                    int day = Integer.parseInt(parts[0]) - 1;
                    String commodity = parts[1];
                    int profit = Integer.parseInt(parts[2]);

                    int cIndex = -1;
                    for (int i = 0; i < COMMS; i++) {
                        if (commodities[i].equals(commodity)) {
                            cIndex = i;
                            break;
                        }
                    }

                    if (day >= 0 && day < DAYS && cIndex != -1) {
                        profitData[m][day][cIndex] = profit;
                    }
                }

                br.close();

            } catch (IOException e) {

            }
        }
    }

// ======== 10 REQUIRED METHODS (Students fill these) ========

public static String mostProfitableCommodityInMonth(int month) {
    if (month < 0 || month > 11) return "INVALID_MONTH";

    int[] totals = new int[COMMS];

    for (int day = 0; day < DAYS; day++) {
        for (int c = 0; c < COMMS; c++) {
            totals[c] += profitData[month][day][c];
        }
    }

    int bestIndex = 0;
    for (int i = 1; i < COMMS; i++) {
        if (totals[i] > totals[bestIndex]) {
            bestIndex = i;
        }
    }

    return commodities[bestIndex] + " " + totals[bestIndex];
}



    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month > 11 || day < 1 || day > 28) {
            return -99999;
        }

        int total = 0;
        for (int c = 0; c < COMMS; c++) {
            total += profitData[month][day - 1][c];
        }

        return total;
    }


    public static int commodityProfitInRange(String commodity, int fromDay, int toDay) {
       if ( fromDay < 1 || toDay > 28 || fromDay > toDay )
           return -99999;

       int cIndex = -1;
       for (int i = 0; i < COMMS; i++) {
           if (commodities[i].equals(commodity)) {
               cIndex = i;
               break;
           }
       }
        if (cIndex == -1)
            return -99999;

        int total = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = fromDay - 1; d <= toDay - 1; d++) {
                total += profitData [m][d][cIndex];
            }
        }
return total;
}



    public static int bestDayOfMonth(int month) {
        if (month < 0 || month > 11) return -1;

        int bestDay = 1;
        int maxProfit = Integer.MIN_VALUE;

        for (int day = 1; day <= DAYS; day++) {

            int total = 0;

            for (int c = 0; c < COMMS; c++) {
                total += profitData[month][day - 1][c];
            }

            if (total > maxProfit) {
                maxProfit = total;
                bestDay = day;
            }
        }

        return bestDay;
    }




}
