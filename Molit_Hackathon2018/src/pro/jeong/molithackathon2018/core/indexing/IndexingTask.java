package pro.jeong.molithackathon2018.core.indexing;

import pro.jeong.molithackathon2018.data.indexer.Indexer;
import pro.jeong.molithackathon2018.data.indexer.task.IndexMerger;

import java.io.File;

public class IndexingTask {
    public static void main(String[] ar) {
        File indexOnFolder = new File("J:/BusData/");
        //Indexer indexer = new Indexer(indexOnFolder, Indexer.DataSortingMethod.BY_TRIP_ID, "2017-08-02");
        //indexer.startIndexing();
        IndexMerger merger = new IndexMerger("F:\\PUBLIC\\Project\\Molit_Hackathon2018\\Molit_Hackathon2018\\Index\\BY_TRIP_ID\\17\\0801\\", "TRIPID");
        merger.startMerger();
    }
}
