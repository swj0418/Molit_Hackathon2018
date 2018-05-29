package pro.jeong.molithackathon2018.data.datatype;

import java.util.ArrayList;
import java.util.HashMap;

public class Bus {
    ArrayList<String> signalGeneratedTime = new ArrayList<String>();

    private String tripKey = "";
    private String recorederModel = "DEFAULT_MODEL";
    private String busNumber_01 = ""; // 차대번호?
    private String busType = "";
    private String busID = "";
    private String companyID = "";
    private String driverID = "";
    private String dayDriveLength = "";
    private String accumulatedDriveLength = "";

    private ArrayList<String> busSpeed = new ArrayList<>();
    private ArrayList<String> rpm = new ArrayList<>();
    private ArrayList<String> brakeSignal = new ArrayList<>();

    private HashMap<String, BusLocation> location = new HashMap<>();

    public Bus() {

    }

    public Bus(String busID) {
        this.busID = busID;
    }

    public HashMap<String, BusLocation> getBusLocation() {
        return location;
    }

    public void appendBusLocation(BusLocation location) {
        this.location.put(location.getTime(), location);
    }

    public String getBusX(String time) {
        return location.get(time).getX();
    }

    public String getBusY(String time) {
        return location.get(time).getY();
    }

    public String getBusAngle(String time) {
        return location.get(time).getAngle();
    }


}
