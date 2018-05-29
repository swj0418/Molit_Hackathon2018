package pro.jeong.molithackathon2018.data.parser;

import pro.jeong.molithackathon2018.data.datatype.Bus;
import pro.jeong.molithackathon2018.data.datatype.BusLocation;
import pro.jeong.molithackathon2018.utils.Creators;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BusParser {
    private String busID = "";
    private Bus bus = new Bus();

    public BusParser(String busID) {
        this.busID = busID;
        this.bus = new Bus(busID);

        findBusInformation(true, "0801");
    }

    public BusParser(String busID, String date) {
        this.busID = busID;
        this.bus = new Bus(busID);

        findBusInformation(false, date);
    }

    public void findBusInformation(boolean testmode, String date) {
        if(testmode) {
            String datasetPath = setDatasetPath(testmode);
            findBusInformationPartial(datasetPath);
        } else {
            ArrayList<String> datasetPath = setDatasetPathContinous(date);
            findBusInformationFull(datasetPath);
        }
    }

    private void findBusInformationFull(ArrayList<String> datasetPath) {
        ArrayList<String> datapaths = datasetPath;
        int processedCount = 0;
        for(String datum : datapaths) {
            BufferedReader reader = Creators.createBufferedReader(datum, "UTF-8");

            String line = "";
            try {
                System.out.println("Processing file : " + processedCount + "   " + datum);
                readBusInformation(reader);
                reader.close();
                processedCount++;
            } catch(IOException e) {

            }
        }
    }

    private void findBusInformationPartial(String dataFilePath) {
        BufferedReader reader = Creators.createBufferedReader(dataFilePath, "UTF-8");
        try {
            readBusInformation(reader);
            reader.close();
        } catch(IOException e) {

        }
    }

    private void readBusInformation(BufferedReader reader) {
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                ArrayList<String> lineDatum = separateLineData(line);
                if(lineDatum.get(0).equals(busID)) {
                    //System.out.println(lineDatum.get(21) + " Brake : " + lineDatum.get(11) + " X: " + lineDatum.get(12) + " / Y: " + lineDatum.get(13) + " Angle : " + lineDatum.get(14));
                    BusLocation location = new BusLocation(lineDatum.get(21), lineDatum.get(12), lineDatum.get(13), lineDatum.get(14));
                    System.out.println(line);
                    bus.appendBusLocation(location);
                }
            }
        } catch(IOException e) {

        }
    }

    private String setDatasetPath(Boolean testmode) {
        if(testmode) {
            return "J:/BusData/17/0801/part-r-00000/part-r-00000";
            // C-14841428117080103031300
            // C-26290102517080104210500
            // C-124261537317080105420000
        } else {
            return "J:/Datasets/";
        }
    }

    private ArrayList<String> setDatasetPathContinous(String date) {
        ArrayList<String> retArray = new ArrayList<>();
        String rootPath = "J:/BusData/17/" + date + "/";
        String[] individualFileRootdir = new File(rootPath).list();
        int count = 0;
        for(String path : individualFileRootdir) {
            String filePath = rootPath + path + "/" + new File(rootPath + path).list()[0];
            retArray.add(filePath);
            count++;
        }
        return retArray;
    }

    private ArrayList<String> setDatasetPathContinous(String date, int count) {
        ArrayList<String> retArray = new ArrayList<>();
        String rootPath = "J:/" + date + "/";
        String[] individualFileRootdir = new File(rootPath).list();
        int internalCount = 0;
        for(String path : individualFileRootdir) {
            if(internalCount == count) {
                break;
            }
            String filePath = rootPath + path + "/" + new File(rootPath + path).list()[0];
            retArray.add(filePath);
            internalCount++;
        }
        return retArray;
    }



    private ArrayList<String> separateLineData(String line) {
        ArrayList<String> retArray = new ArrayList<>();
        String[] tmpList = line.split("\\|");
        for(int idx = 0; idx < tmpList.length; idx++) {
            retArray.add(tmpList[idx]);
        }
        return retArray;
    }

    public Bus getBus() {
        return bus;
    }
}
