import java.io.*;
import java.util.*;

public class Triangletest {
  public static void main(String[] args) throws FileNotFoundException {

    /**
     * Process the data from the files, store in time ordered arrays 
     * For each curreny pair
     */
    File fxdata1 = new File("./fxdata1.txt");
    Scanner data1Scanner = new Scanner(fxdata1);
    File fxdata2 = new File("./fxdata2.txt");
    Scanner data2Scanner = new Scanner(fxdata2);
    Double[] usdGbp = new Double[999];
    Double[] eurGbp = new Double[999];
    Double[] cnyGbp = new Double[999];
    Double[] usdEur = new Double[999];
    Double[] cnyEur = new Double[999];
    data1Scanner.nextLine(); // Header
    data1Scanner.nextLine(); // Table Cols
    int index = 0;
    while(data1Scanner.hasNextLine()) {
      String nextLine = data1Scanner.nextLine();
      Scanner lineScan =  new Scanner(nextLine);
      lineScan.next(); //day
      lineScan.next(); //date
      lineScan.next(); //weekday
      Double usdGbpNext = lineScan.nextDouble();
      Double eurGbpNext = lineScan.nextDouble();
      Double cnyGbpNext = lineScan.nextDouble();
      usdGbp[index] = usdGbpNext;
      eurGbp[index] = eurGbpNext;
      cnyGbp[index] = cnyGbpNext;
      index++;
      lineScan.close();
    }
    data2Scanner.nextLine(); // Header
    data2Scanner.nextLine(); // Table Cols
    index = 0;
    while(data2Scanner.hasNextLine()) {
      String nextLine = data2Scanner.nextLine();
      Scanner lineScan =  new Scanner(nextLine);
      lineScan.next(); //day
      lineScan.next(); //date
      lineScan.next(); //weekday
      Double usdEurNext = lineScan.nextDouble();
      Double cnyEurNext = lineScan.nextDouble();
      usdEur[index] = usdEurNext;
      cnyEur[index] = cnyEurNext;
      index++;
      lineScan.close();
    }
    data2Scanner.close();
    data1Scanner.close();
    double[] results1 = new double[999];
    double[] results2 = new double[999];

    /* 
    At this point we have 5 arrays represnting the exchange rates between 
    our different currency pairs, which are:
    usdGbp - US Dollar to British Pound
    eurGbp - Euro to British Pound
    cnyGbp - Chinese rmb to British Pound
    usdEur - US Dollar to Euro
    cnyEur - Chinese rmb to Euro
    */

    /*
    Testing Triangle Arbitrage between Pound - Dollar - Euro
    */
    for(int i = 0; i < usdGbp.length; i++) {
      double startingPounds = 1000000.0;
      double toDollars = startingPounds * usdGbp[i];
      double toEuros = toDollars / usdEur[i];
      double toPounds = toEuros / eurGbp[i];
      results1[i] = toPounds;
      if(startingPounds - toPounds > 0.0001) {
        System.out.println("Triangle Arbitrage at: " + i);
        System.out.println("Starting Pounds: " + startingPounds);
        System.out.println("Ending Pounds: " + toPounds);
      }
    }

    /*
    Testing Triangle Arbitrage between Pound - RMB - Euro
    */
    for(int i = 0; i < usdGbp.length; i++) {
      double startingPounds = 1000000.0;
      double toRMB = startingPounds * cnyGbp[i];
      double toEuros = toRMB / cnyEur[i];
      double toPounds = toEuros / eurGbp[i];
      results2[i] = toPounds;
      if(startingPounds - toPounds > 0.0001) {
        System.out.println("Triangle Arbitrage at: " + i);
        System.out.println("Starting Pounds: " + startingPounds);
        System.out.println("Ending Pounds: " + toPounds);
      }
    }

    /*for (int i = 0; i< results1.length; i++) {
      if(results1[i] > 1000090) {
        System.out.println(results1[i]);
        System.out.println(i);
      }
    }
    System.out.println("/////////////");
    System.out.println(usdGbp[812]);
    System.out.println(usdEur[812]);
    System.out.println(eurGbp[812]);
    System.out.println("/////////////");*/

    Arrays.sort(results1);
    Arrays.sort(results2);
    System.out.println("First Results");
    System.out.println(results1[998]);
    System.out.println(results1[997]);
    System.out.println(results1[996]);
    System.out.println(results1[995]);
    System.out.println(results1[994]);
    System.out.println(results1[0]);
    System.out.println(results1[1]);
    System.out.println(results1[2]);
    System.out.println(results1[3]);
    System.out.println(results1[4]);
    System.out.println("Second Results");
    System.out.println(results2[998]);
    System.out.println(results2[997]);
    System.out.println(results2[996]);
    System.out.println(results2[995]);
    System.out.println(results2[994]);
    System.out.println(results2[0]);
    System.out.println(results2[1]);
    System.out.println(results2[2]);
    System.out.println(results2[3]);
    System.out.println(results2[4]);


    /*
    Finding the Variance and standard deviation between pounds and USD/Euro/RMB
    */
    double usdGBPMean = 0.0;
    double euroGBPMean = 0.0;
    double rmbGBPMean = 0.0;
    for(int i = 0; i < usdGbp.length; i++) {
      usdGBPMean += usdGbp[i];
      euroGBPMean += eurGbp[i];
      rmbGBPMean += cnyGbp[i];
    }
    usdGBPMean /= usdGbp.length;
    euroGBPMean /= eurGbp.length;
    rmbGBPMean /= cnyGbp.length;
    System.out.println("usdGBPMean: " + usdGBPMean);
    System.out.println("euroGBPMean: " + euroGBPMean);
    System.out.println("rmbGBPMean: " + rmbGBPMean);
    double usdGbpSquaredSums = 0.0;
    double euroGBPSquaredSums = 0.0;
    double rmbGBPSquaredSums = 0.0;
    for(int i = 0; i < usdGbp.length; i++) {
      usdGbpSquaredSums += ((usdGbp[i] - usdGBPMean) * (usdGbp[i] - usdGBPMean));
      euroGBPSquaredSums += ((eurGbp[i] - euroGBPMean) * (eurGbp[i] - euroGBPMean));
      rmbGBPSquaredSums += ((cnyGbp[i] - rmbGBPMean) * (cnyGbp[i] - rmbGBPMean));
    }
    double usdGBPVariance = usdGbpSquaredSums / usdGbp.length;
    double euroGBPVariance = euroGBPSquaredSums / eurGbp.length;
    double cnyGBPVariance = rmbGBPSquaredSums / cnyGbp.length;
    System.out.println("usdGBPVariance: " + usdGBPVariance);
    System.out.println("euroGBPVariance: " + euroGBPVariance);
    System.out.println("rmbGBPVariance: " + cnyGBPVariance);
    double usdGBPStandarDeviation = Math.sqrt(usdGBPVariance);
    double euroGBPStandarDeviation = Math.sqrt(euroGBPVariance);
    double cnyGBPStandarDeviation = Math.sqrt(cnyGBPVariance);
    System.out.println("usdGBPStandarDeviation: " + usdGBPStandarDeviation);
    System.out.println("euroGBPStandarDeviation: " + euroGBPStandarDeviation);
    System.out.println("cnyGBPStandarDeviation: " + cnyGBPStandarDeviation);
    double usdGBPCoefficientVariation = usdGBPStandarDeviation / usdGBPMean * 100;
    double euroGBPCoefficientVariation = euroGBPStandarDeviation / euroGBPMean * 100;
    double cnyGBPCoefficientVariation = cnyGBPStandarDeviation / rmbGBPMean * 100;
    System.out.println("usdGBPCoefficientVariation: " + usdGBPCoefficientVariation);
    System.out.println("euroGBPCoefficientVariation: " + euroGBPCoefficientVariation);
    System.out.println("cnyGBPCoefficientVariation: " + cnyGBPCoefficientVariation);

    /**
     * Finding the running Coefficient of Variation 
     */
    double usdGBPCoefRunningSum = 0.0;
    double euroGBPCoefRunningSum = 0.0;
    double cnyGBPCoefRunningSum = 0.0;
    int sumCount = 0;
    int start = 0;
    int end = 20;
    while(end < usdGbp.length) {
      // Calculate the mean of the next group of 21 numbers
      double usdMean = 0.0;
      double euroMean = 0.0;
      double cnyMean = 0.0;
      for(int i = start; i <= end; i++) {
        usdMean += usdGbp[i];
        euroMean += eurGbp[i];
        cnyMean += cnyGbp[i];
      }
      usdMean = usdMean / 21;
      euroMean = euroMean / 21;
      cnyMean = cnyMean / 21;

      // Get the coefficient of variations over this 21 day time span
      double usdCoef = 0.0;
      double euroCoef = 0.0;
      double cnyCoef = 0.0;
      for(int i = start; i <= end; i++) {
        usdCoef += ((usdGbp[i] - usdMean) * (usdGbp[i] - usdMean));
        euroCoef += ((eurGbp[i] - euroMean) * (eurGbp[i] - euroMean));
        cnyCoef += ((cnyGbp[i] - cnyMean) * (cnyGbp[i] - cnyMean));
      }
      // add the coefficient of variaton for this time span to the running sum
      double usdDev = Math.sqrt(usdCoef / 21);
      double eruoDev = Math.sqrt(euroCoef / 21);
      double cnyDev = Math.sqrt(cnyCoef / 21);
      /*System.out.println("Each USD Deviation: " + usdDev);
      System.out.println("Each Euro Deviation: " + eruoDev);
      System.out.println("Each CNY Deviation: " + cnyDev);*/
      /*System.out.println("USD Mean: " + usdMean);
      System.out.println("Euro Mean: " + euroMean);
      System.out.println("CNY Mean: " + cnyMean);*/
      usdGBPCoefRunningSum += (Math.sqrt(usdCoef / 21) / usdMean) * 100;
      euroGBPCoefRunningSum += (Math.sqrt(euroCoef / 21) / euroMean) * 100;
      cnyGBPCoefRunningSum += (Math.sqrt(cnyCoef / 21) / cnyMean) * 100;
      /*System.out.println("Each USD Coef of Variation: " + (Math.sqrt(usdCoef / 21) / usdMean) * 100);
      System.out.println("Each Euro Coef of Variation: " + (Math.sqrt(euroCoef / 21) / euroMean) * 100);
      System.out.println("Each CNY Coef of Variation: " + (Math.sqrt(cnyCoef / 21) / cnyMean) * 100);*/
      sumCount++;
      start++;
      end++;
    }
    if(sumCount != usdGbp.length - 20) {
      System.out.println("You dun fucked up");
    }
    /*System.out.println("Running Sum USD: " + usdGBPCoefRunningSum);
    System.out.println("Running Sum Euro: " + euroGBPCoefRunningSum);
    System.out.println("Running Sum CNY: " + cnyGBPCoefRunningSum);*/
    double usdGBPCoefRunningAvergage = usdGBPCoefRunningSum / sumCount;
    double euroGBPCoefRunningAvergage = euroGBPCoefRunningSum / sumCount;
    double cnyGBPCoefRunningAvergage = cnyGBPCoefRunningSum / sumCount;
    System.out.println("usdGBPCoefRunningAvergage: " + usdGBPCoefRunningAvergage);
    System.out.println("euroGBPCoefRunningAvergage: " + euroGBPCoefRunningAvergage);
    System.out.println("cnyGBPCoefRunningAvergage: " + cnyGBPCoefRunningAvergage);

  }
}