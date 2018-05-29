package pro.jeong.molithackathon2018.core.tripVisualizer;

import pro.jeong.molithackathon2018.data.datatype.Bus;

public class Visualizer {
    Bus bus = null;

    private tripVisualizerFrame frame = null;
    private tripVisualizerPanel panel = null;

    public Visualizer(Bus bus) {
        this.bus = bus;
    }

    public void visualizeBus() {
        tripVisualizerPanel panel = new tripVisualizerPanel(bus);
        new tripVisualizerFrame(panel);
    }
}
