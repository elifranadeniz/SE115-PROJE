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

                    if (parts.length < 3) continue;

                    try {
                        int day = Integer.parseInt(parts[0].trim()) - 1;
                        String commodity = parts[1].trim();
                        int profit = Integer.parseInt(parts[2].trim());

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

                    } catch (NumberFormatException e) {
                        continue;
                    }
                }

                br.close();

            } catch (IOException e) {

            }
        }
    }

// ======== 10 REQUIRED METHODS (Students fill these) ========

public static String mostProfitableCommodityInMonth(int month) {
    if (month < 0 || month > 11)
        return "INVALID_MONTH";

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
        if (month < 0 || month > 11)
            return -1;

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


    public static String bestMonthForCommodity(String comm) {
        int cIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                cIndex = i;
                break;
            }
        }
        if (cIndex == -1)
            return "INVALID_COMMODITY";

        int maxProfit = Integer.MIN_VALUE;
        int bestMonth = -1;

        for (int m = 0; m < MONTHS; m++) {
            int monthlyTotal = 0;

            for (int d = 0; d < DAYS; d++) {
                monthlyTotal += profitData[m][d][cIndex];
    }

            if (monthlyTotal > maxProfit) {
                maxProfit = monthlyTotal;
                bestMonth = m;
            }
        }
        return months[bestMonth];
    }



    public static int consecutiveLossDays(String comm) {
int cIndex = -1;
for (int i =0; i < COMMS; i++) {
    if (commodities[i].equals(comm)) {
        cIndex = i;
        break;
    }
}
if (cIndex == -1)
    return -1;

int currentStreak = 0;
int maxStreak = 0;

for (int m = 0; m < MONTHS; m++) {
    for (int d = 0; d < DAYS; d++) {

        if (profitData[m][d][cIndex] < 0) {
            currentStreak++;

            if (currentStreak > maxStreak) {
                maxStreak = currentStreak;
            }

        } else {
            currentStreak = 0;
        }
    }
}
return maxStreak;
    }


    public static int daysAboveThreshold(String comm, int threshold) {
        int cIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                cIndex = i;
                break;
            }
        }
        if (cIndex == -1)
            return -1;

        int count = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profitData[m][d][cIndex] > threshold) {
                    count++;
                }
            }
        }
        return count;
    }



        public static int biggestDailySwing(int month) {
            if (month < 0 || month > 11)
                return -99999;

            int maxSwing = 0;
            int prevTotal = 0;

            for (int day = 0; day < DAYS; day++) {
                int total = 0;
                for (int c = 0; c < COMMS; c++) {
                    total += profitData[month][day][c];
                }
                if (day > 0) {
                    int diff = Math.abs(total - prevTotal);
                    if (diff > maxSwing) {
                        maxSwing = diff;
                    }
                }
                prevTotal = total;
            }
            return maxSwing;
        }


    public static String compareTwoCommodities(String c1, String c2) {
        int cIndex1 = -1;
        int cIndex2 = -1;

for (int i = 0; i < COMMS; i++) {
    if (commodities[i].equals(c1)) cIndex1 = i;
    if (commodities[i].equals(c2)) cIndex2 = i;
}

if (cIndex1 == -1 || cIndex2 == -1) return "INVALID_COMMODITY";

int total1 = 0;
int total2 = 0;

for (int m = 0; m < MONTHS; m++) {
    for (int d = 0; d < DAYS; d++) {
        total1 += profitData[m][d][cIndex1];
        total2 += profitData[m][d][cIndex2];
    }
}
if (total1 > total2)
    return c1 + " is better by " + (total1 - total2);
if (total2 > total1)
    return c2 + " is better by " + (total2 - total1);
return "Equal";
    }



    public static String bestWeekOfMonth(int month) {
        if ( month < 0 || month > 11)
           return "INVALID_MONTH";
int bestWeek = 1;
        int maxProfit = Integer.MIN_VALUE;

for (int week = 1; week <= 4; week++) {

int total = 0;
int startDay = (week -1) * 7;
int endDay = startDay + 6;

for ( int d = startDay; d <= endDay; d++) {
    for (int c = 0; c < COMMS; c++) {
        total += profitData[month][d][c];
    }
}
if (total > maxProfit) {
    maxProfit = total;
    bestWeek = week;
        }
 }
return "Week " + bestWeek;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}











