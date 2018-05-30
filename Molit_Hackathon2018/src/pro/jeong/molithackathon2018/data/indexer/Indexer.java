package pro.jeong.molithackathon2018.data.indexer;

import pro.jeong.molithackathon2018.data.indexer.by.ByBusIndexer;
import pro.jeong.molithackathon2018.data.indexer.by.ByTripIDIndexer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Indexer {
    public static enum DataSortingMethod {BY_BUS, BY_REGION, BY_BUS_TYPE, BY_TRIP_ID}
    String directoryPath = "./Index/";
    DataSortingMethod methodToPerform = null;
    File indexOn = null;
    String rawDate = "";

    public Indexer(File indexOn, DataSortingMethod method, String date) {
        this.rawDate = date;
        this.indexOn = indexOn;

        if(method == DataSortingMethod.BY_BUS) {
            directoryPath += method.toString() + "/";
            File folder = new File(directoryPath);
            if(folder.exists()) {
                folder.mkdir();
            }
            this.methodToPerform = DataSortingMethod.BY_BUS;
        } else if(method == DataSortingMethod.BY_REGION) {
            directoryPath += method.toString() + "/";
            File folder = new File(directoryPath);
            if(folder.exists()) {
                folder.mkdir();
            }
            this.methodToPerform = DataSortingMethod.BY_REGION;
        } else if(method == DataSortingMethod.BY_BUS_TYPE) {
            directoryPath += method.toString() + "/";
            File folder = new File(directoryPath);
            if(folder.exists()) {
                folder.mkdir();
            }
            this.methodToPerform = DataSortingMethod.BY_BUS_TYPE;
        } else if(method == DataSortingMethod.BY_TRIP_ID) {
            directoryPath += method.toString() + "/";
            File folder = new File(directoryPath);
            if(folder.exists()) {
                folder.mkdir();
            }
            this.methodToPerform = DataSortingMethod.BY_TRIP_ID;
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
            case BY_TRIP_ID:
                indexByTripID();
                break;

            default:
                System.out.println("Data Sorting Method Is Not Specified");
        }
    }

    private void indexByBus() {
        String[] date = parseDate();
        ByBusIndexer indexer = new ByBusIndexer(indexOn, date);
        indexer.startIndexing();
    }

    private void indexByRegion() {

    }

    private void indexByBusType() {

    }

    private void indexByTripID() {
        String[] date = parseDate();
        ByTripIDIndexer indexer = new ByTripIDIndexer(indexOn, date);
        indexer.startIndexing();
    }

    private String[] parseDate() {
        String[] split = rawDate.split("-");
        if(split[0].length() == 4) {
            split[0] = split[0].substring(2);
        }
        return split;
    }
}
