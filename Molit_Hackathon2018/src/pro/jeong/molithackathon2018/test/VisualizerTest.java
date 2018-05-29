package pro.jeong.molithackathon2018.test;

import pro.jeong.molithackathon2018.core.tripVisualizer.Visualizer;
import pro.jeong.molithackathon2018.data.datatype.Bus;
import pro.jeong.molithackathon2018.data.parser.BusParser;

public class VisualizerTest {
    public static void main(String[] ar) {
        Bus bus = new BusParser("C2780509217080115460000", "0801").getBus();
        Visualizer visualizer = new Visualizer(bus);
        visualizer.visualizeBus();
    }
}
