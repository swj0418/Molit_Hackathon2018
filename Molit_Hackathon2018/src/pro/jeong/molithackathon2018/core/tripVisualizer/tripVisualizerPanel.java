package pro.jeong.molithackathon2018.core.tripVisualizer;

import pro.jeong.molithackathon2018.data.datatype.Bus;
import pro.jeong.molithackathon2018.data.datatype.BusLocation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class tripVisualizerPanel extends JPanel {
    Bus bus = null;
    ArrayList<BusLocation> busLocations = new ArrayList<BusLocation>();
    tripVisualizerPanel(Bus bus) {
        this.bus = bus;
        busLocations = bus.getBusLocation();
        for(int i = 0; i < busLocations.size(); i++) {
            //System.out.println(busLocations.get(i).getX() + "   " + busLocations.get(i).getY() + "     at time : " + busLocations.get(i).getTime());
        }
        // 000000000
        setSize(new Dimension(1000, 1000));
    }

    public void paint(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D)g1;
        g.setColor(Color.BLACK);
        for(int i = 0; i < busLocations.size(); i++) {
            int x = TransformLocation(bus.getBusX(i));
            int y = TransformLocation(bus.getBusY(i));
            System.out.println(x + "          " + y);
            g.fillOval(x, y, 1, 1);
        }
    }

    private int TransformLocation(String loc) {
        double location = (double)Integer.valueOf(loc);
        //location = location / 2000;
        //location = Math.ceil(location);
        int retInt = (int)location;
        return retInt;
    }
}
