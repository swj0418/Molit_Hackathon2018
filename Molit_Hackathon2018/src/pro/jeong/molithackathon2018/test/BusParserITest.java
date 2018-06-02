package pro.jeong.molithackathon2018.test;

import pro.jeong.molithackathon2018.core.tripVisualizer.Visualizer;
import pro.jeong.molithackathon2018.data.datatype.Bus;
import pro.jeong.molithackathon2018.data.parser.BusParserI;

public class BusParserITest {
    public static void main(String[] ar) {
        BusParserI parser = new BusParserI();
        Thread pThread = new Thread(parser);
        pThread.start();
        try {
            pThread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        Bus bus = parser.getBus();
        System.out.println(bus.getBusType());

        Visualizer visualizer = new Visualizer(bus);
        visualizer.visualizeBus();
    }
}
