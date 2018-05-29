package pro.jeong.molithackathon2018.core.indexing;

import pro.jeong.molithackathon2018.data.indexer.Indexer;

import java.io.File;

public class IndexingTask {
    public static void main(String[] ar) {
        File indexOnFolder = new File("J:/BusData/");
        Indexer indexer = new Indexer(indexOnFolder, Indexer.DataSortingMethod.BY_BUS);
        indexer.startIndexing();
    }
}
