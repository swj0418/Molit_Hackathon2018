package pro.jeong.molithackathon2018.data.indexer;
import pro.jeong.molithackathon2018.utils.Creators;
import pro.jeong.molithackathon2018.utils.Utilities;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ByBusIndexer implements ByMethodIndexer {
    private File indexOnRoot = null;

    private ArrayList<String> indexOnDates = new ArrayList<>();
    // SHARED
    private HashMap<String, ArrayList<String>> sharedBusIDMap = new HashMap<>();
    private File BusID_FileLocation = new File("Index/MappedBusID.csv");
    static int uniqueBusCounter = 0;
    //

    private final String databaseUrlBase = "jdbc:sqlite:" + indexFolder + "BBUS/";

    public ByBusIndexer(File indexOn, ArrayList<String> indexOnDates) {
        super();
        indexOnRoot = indexOn;
        this.indexOnDates = indexOnDates;

        System.out.println("Indexing by bus");

        setSQLiteDriver();

        checkIndexFolder();
    }

    private void setSQLiteDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void checkIndexFolder() {
        if(!new File(indexFolder + "BBUS/").exists()) {
            new File(indexFolder + "BBUS/").mkdir();
        }
    }

    private void createFileForNewBusID(String BusID) {
        try(Connection conn = DriverManager.getConnection(databaseUrlBase + BusID + ".db")) {
            if(conn != null) {
                DatabaseMetaData metaData = conn.getMetaData();
                Statement statement = conn.createStatement();
                statement.execute(SQLStatementCreator.createTupleTableForABus("FILEINDEX",
                        "FILEPATH", "TEXT", "LINE", "INT"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void appendFileLocationForBus(String BusID, String filePath, int line) {
        try {
            Connection conn = DriverManager.getConnection(databaseUrlBase + BusID + ".db");

            String queryStatement = "INSERT OR IGNORE INTO FileIndex (filePath, line) VALUES(?,?);";
            PreparedStatement pstmt = conn.prepareStatement(queryStatement);

            pstmt.setString(1, filePath);
            pstmt.setInt(2, line);

            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void startIndexing() {
        System.out.println("Starting indexing on root directory : " + indexOnRoot);
        sequentialIndexing();
    }

    private void threadedIndexing() {
        ArrayList<String> targetFiles = findTagetFiles();

        ArrayList<Thread> taskList = new ArrayList<>();
        for(int i = 0; i < targetFiles.size(); i++) {
            taskList.add(new Thread(new ThreadedTask(targetFiles.get(i))));
        }

        for(int i = 0; i < targetFiles.size(); i++) {
            taskList.get(i).start();
        }
    }

    private void sequentialIndexing() {
        ArrayList<String> targetFiles = findTagetFiles();
        for(int i =0; i < targetFiles.size(); i++) {
            SequentialTask task = new SequentialTask(targetFiles.get(i));
            task.work();
        }
    }

    private ArrayList<String> findTagetFiles() {
        File rootDir = null;
        ArrayList<String> retArr = new ArrayList<>();
        try {
            rootDir = indexOnRoot;
            /**
             * Testing Purpose. Not a complete implementation
             */
            //retArr.addAll(Utilities.listEndPointDirectories(new File("J:/BusData/17/0801")));
            //retArr.addAll(Utilities.listEndPointDirectories(new File("J:/BusData/17/0802")));
            retArr = Utilities.listEndPointDirectories("J:/busData/17/0802");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return retArr;
    }

    class ThreadedTask implements Runnable {
        File workOnFile = null;
        ThreadedTask(String workOnFile) {
            this.workOnFile = new File(workOnFile);
        }

        @Override
        public void run() {
            System.out.println("======================================= Working on File : " + workOnFile.getName() +
                                        " =======================================");

            BufferedReader reader = Creators.createBufferedReader(workOnFile.getAbsolutePath(), "UTF-8");
            String line = "";
            try {
                int lineCount = 0;
                while((line = reader.readLine()) != null) {
                    String[] split = line.split("\\|");
                    String busIDtoLookUp = split[5];
                    String filePath = workOnFile.getAbsolutePath();
                    if(!sharedBusIDMap.containsKey(busIDtoLookUp)) { // If a shared hashmap does not contain this busID.
                        String busIDtoAppend = busIDtoLookUp;
                        System.out.println("Found new bus to append : " + busIDtoAppend);

                        ArrayList<String> initArrayList = new ArrayList<>();
                        initArrayList.add(filePath);

                        sharedBusIDMap.put(busIDtoAppend, initArrayList);

                        createFileForNewBusID(busIDtoAppend);
                        try {
                            Thread.sleep(100);
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                        appendFileLocationForBus(busIDtoAppend, workOnFile.getAbsolutePath(), lineCount);

                        lineCount++;
                    } else if(sharedBusIDMap.containsKey(busIDtoLookUp)) {
                        if(!sharedBusIDMap.get(busIDtoLookUp).contains(filePath)) {
                            appendFileLocationForBus(busIDtoLookUp, workOnFile.getAbsolutePath(), lineCount);
                        }
                        lineCount++;
                    } else {
                        lineCount++;
                    }
                    //System.out.println("File : " + workOnFile.getName() + "   Line : " + lineCount);
                }
                reader.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    class SequentialTask {
        File workOnFile = null;
        ArrayList<String> fileOnMemeory = new ArrayList<>();
        Object[] fileOnMemoryPrimitive = null;

        SequentialTask(String workOnFile) {
            this.workOnFile = new File(workOnFile);
        }

        private void work() {
            loadFileOnMemory();
            System.out.println("======================================= Working on File : " + workOnFile.getName() +
                    " =======================================");

            int lineCount = 0;
            HashMap<String, ArrayList<String>> busID_individual = new HashMap<>();

            for(int i = 0; i < fileOnMemoryPrimitive.length; i++) {
                String busIDtoLookUp = (String)fileOnMemoryPrimitive[i];
                String filePath = workOnFile.getAbsolutePath();
                if(!busID_individual.containsKey(busIDtoLookUp)) { // If an individual hashmap does not contain this busID.
                    String busIDtoAppend = busIDtoLookUp;
                    System.out.println("Found new bus to append : " + busIDtoAppend);

                    ArrayList<String> initArrayList = new ArrayList<>();
                    initArrayList.add(filePath + "|" + lineCount);

                    busID_individual.put(busIDtoAppend, initArrayList);

                    lineCount++;
                } else {
                    lineCount++;
                }
                System.out.println("File : " + workOnFile.getName() + "   Line : " + lineCount);
            }
            saveIndividualFile(busID_individual);
        }

        private void loadFileOnMemory() {
            BufferedReader reader = Creators.createBufferedReader(workOnFile.getAbsolutePath(), "UTF-8");
            String line = "";
            try {
                int lineCount = 0;
                while((line = reader.readLine()) != null) {
                    String[] split = line.split("\\|");
                    String busID = split[5];
                    fileOnMemeory.add(busID);
                }
                fileOnMemoryPrimitive = fileOnMemeory.toArray();
                reader.close();
                System.out.println("File : " + workOnFile.getName() + " Loading Complete");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        private void saveIndividualFile(HashMap<String, ArrayList<String>> busID_Individual) {
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("Index/BBUS/" + workOnFile.getName() + ".csv"))));
                Object[] keyArray = busID_Individual.keySet().toArray();
                for(int i = 0; i < keyArray.length; i++) {
                    writer.write(keyArray[i] + ",");
                    for(int k = 0; k < busID_Individual.get(keyArray[i]).size(); k++) {
                        Object[] split = busID_Individual.get(keyArray[i]).get(k).split("\\|");
                        writer.write(split[0] + "," + split[1]);
                    }
                    writer.write("\n");
                }
                writer.close();
            } catch(IOException e) {

            }
        }
    }
}
