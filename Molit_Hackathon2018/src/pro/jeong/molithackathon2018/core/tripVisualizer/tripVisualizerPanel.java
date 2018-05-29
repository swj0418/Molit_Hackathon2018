package pro.jeong.molithackathon2018.core.tripVisualizer;

import pro.jeong.molithackathon2018.data.datatype.Bus;
import pro.jeong.molithackathon2018.utils.Generators;

import javax.swing.*;
import javax.xml.stream.Location;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math.*;
import java.util.Set;

public class tripVisualizerPanel extends JPanel {
    Bus bus = null;
    ArrayList<String> timeLine = new ArrayList<>();
    tripVisualizerPanel(Bus bus) {
        this.bus = bus;
        //timeLine = Generators.generateTime("15300300", "17300000");
        Set<String> timeAvailable = this.bus.getBusLocation().keySet();
        for(String str : timeAvailable) {
            timeLine.add(str);
        }
        // 000000000
        setSize(new Dimension(10000, 10000));
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for(int i = 0; i < timeLine.size(); i++) {
            //String toGet = "170801" + timeLine.get(i);
            String toGet = timeLine.get(i);
            //int x = Integer.parseInt(bus.getBusX(toGet).substring(6));
            int x = logTransformLocation(bus.getBusX(toGet));
            //int y = Integer.parseInt(bus.getBusY(toGet).substring(6));
            int y = logTransformLocation(bus.getBusY(toGet));
            System.out.println(x + "          " + y);
            g.fillOval(x, y, 10, 10);
        }
    }

    private int logTransformLocation(String loc) {
        double location = (double)Integer.valueOf(loc.substring(3));
        location = location / 200;
        location = Math.ceil(location);
        int retInt = (int)location;
        return retInt;
    }

    private int transformLocation(String loc) {
        int retInt = Integer.valueOf(loc.substring(5));
        return retInt;
    }
}
