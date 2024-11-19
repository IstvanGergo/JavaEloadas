package istvangergo.javaeloadas.Controller;
import istvangergo.javaeloadas.Controller.XMLParser;
import istvangergo.javaeloadas.Model.MNB;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static istvangergo.javaeloadas.Controller.XMLParser.parseAll;

public class SOAPClientWindow {
    public void getAll() throws Exception {
        Scanner olvas = new Scanner(new File("C:/adatok/MNB.txt"));
        String sor = "";
        while (olvas.hasNextLine()) {
            sor = olvas.nextLine();

        }
        List<MNB> data = parseAll(sor);

    }
}
