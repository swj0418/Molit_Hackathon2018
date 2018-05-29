package pro.jeong.molithackathon2018.data.indexer;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Indexer {
    public static enum DataSortingMethod {BY_BUS, BY_REGION, BY_BUS_TYPE}
    String directoryPath = "";
    DataSortingMethod methodToPerform = null;
    File indexOn = null;

    public Indexer(File indexOn, DataSortingMethod method) {
        if(method == DataSortingMethod.BY_BUS) {
            directoryPath = "./Index/";
            this.indexOn = indexOn;
            File folder = new File(directoryPath);
            if(folder.exists()) {
                folder.mkdir();
            }
            this.methodToPerform = DataSortingMethod.BY_BUS;
        } else if(method == DataSortingMethod.BY_REGION) {
            directoryPath = "./Index/";
            this.indexOn = indexOn;
            File folder = new File(directoryPath);
            if(folder.exists()) {
                folder.mkdir();
            }
            this.methodToPerform = DataSortingMethod.BY_REGION;
        } else if(method == DataSortingMethod.BY_BUS_TYPE) {
            directoryPath = "./Index/";
            this.indexOn = indexOn;
            File folder = new File(directoryPath);
            if(folder.exists()) {
                folder.mkdir();
            }
            this.methodToPerform = DataSortingMethod.BY_BUS_TYPE;
        }
    }

    public void startIndexing() {
        switch(methodToPerform) {
            case BY_BUS:
                indexByBus();
                break;
            case BY_REGION:
                indexByRegion();
                break;
            case BY_BUS_TYPE:
                indexByBusType();
                break;

            default:
                System.out.println("Data Sorting Method Is Not Specified");
        }
    }

    private void indexByBus() {
        ArrayList<String> dates = indexingDateRecognizer();
        ByBusIndexer indexer = new ByBusIndexer(indexOn, dates);
        indexer.startIndexing();
    }

    private void indexByRegion() {

    }

    private void indexByBusType() {

    }

    private ArrayList<String> indexingDateRecognizer() {
        ArrayList<String> retArray = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        System.out.print("Index From (yyyy-MM-dd) : ");
        //String from = in.nextLine();
        String from = "2018-08-01";
        System.out.print("Index To (yyyy-MM-dd) : ");
        //String to = in.nextLine();
        String to = "2018-08-02";
        retArray = extendDateFromTo(from , to);
        System.out.println();
        return retArray;
    }

    private ArrayList<String> extendDateFromTo(String from, String to) {
        ArrayList<String> retArray = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate dateFrom = null;
        LocalDate dateTo = null;

        try {
            dateFrom = LocalDate.of(Integer.parseInt(from.split("-")[0]), Integer.parseInt(from.split("-")[1]), Integer.parseInt(from.split("-")[2]));
            dateTo = LocalDate.of(Integer.parseInt(to.split("-")[0]), Integer.parseInt(to.split("-")[1]), Integer.parseInt(to.split("-")[2]));
        } catch(Exception e) {
            e.printStackTrace();
        }

        do {
            retArray.add(dateFrom.toString());
            dateFrom = dateFrom.plusDays(1);
        } while(!dateFrom.equals(dateTo.plusDays(1)));

        return retArray;
    }
}
