package pro.jeong.molithackathon2018.test;

import pro.jeong.molithackathon2018.data.parser.BusParser;

public class BusParserTest {
    public static void main(String[] ar) {
        BusParser parser = new BusParser("C5649558817080115300000");
        parser.findBusInformation(false, "0801");
    }
}
