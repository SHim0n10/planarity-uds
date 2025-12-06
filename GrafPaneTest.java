import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import java.util.Random;

public class GrafPaneTest extends Application {
    private ArrayList<Vrchol> vrcholy;
    private ArrayList<Hrana> hrany;
    

    @Override
    public void start(Stage stage) {
        zacniHru(stage);
        stage.setTitle("Planarity");
        stage.show();
    }
    
    public void zacniHru(Stage stage) {
        System.out.println("----------------------------------------");
        vrcholy = new ArrayList<Vrchol>();
            hrany = new ArrayList<Hrana>();
            Random nahoda = new Random();
            
            // Vytvorenie dialógového okna
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Level difficulty");
            dialog.setHeaderText("Zadaj level náročnosti (1-99):");
            dialog.setContentText("Level:");
    
            // Čaká na input
            Optional<String> result = dialog.showAndWait();
            final Level level = new Level(0);
            // Spracovanie vstupu
            result.ifPresentOrElse(pozadovanyLevel -> {
                if (pozadovanyLevel.isBlank()) { // ak nezadal nič
                    System.out.println("Používateľ nezadal nič");
                    level.setLevel(5);
                } else { // ak zadal nejaký input
                    System.out.println("Používateľ zadal: " + pozadovanyLevel);
                    level.setLevel(Integer.parseInt(pozadovanyLevel));
                }
            },() -> {
                System.out.println("Používateľ stlačil cancel");
                level.setLevel(5);
            });
            
            
            //vytvorenie nahodnych bodov
            for (int v = 0; v < level.getPocetVrcholov(); v++) {
                vrcholy.add(new Vrchol(nahoda.nextInt(600), nahoda.nextInt(500)));
            }
            
            //vytvaranie hran nahodne (pouziva aktualne pozicie vrcholov na vytvaranie)
            for (int v = 0; v < level.getPocetVrcholov(); v++) {
                for (int i = v +1; i < level.getPocetVrcholov(); i++) {
                    Hrana temp = new Hrana(vrcholy.get(v), vrcholy.get(i));
                    if (!edgeIntersectAll(temp)) {
                        hrany.add(temp);
                    }
                }
            }
            
            //pomiesanie vrcholov po vytvoreni
            for (Vrchol v : vrcholy) {
                v.randomPostition(500, 400);
            }
            
            // --- NÁŠ PLÁTNO (Pane) ---
            Pane root = new Pane();
            
            for (Vrchol vrchol : vrcholy) {
                root.getChildren().add(vrchol.getCircle());
            }
            for (Hrana hrana : hrany) {
                root.getChildren().add(hrana.getLine());
            }
            
            /*
            // --- VYTVOR VRCHOLY ---
            Vrchol v1 = new Vrchol(100, 150);
            Vrchol v2 = new Vrchol(300, 220);
            Vrchol v3 = new Vrchol(500, 300);
            Vrchol v4 = new Vrchol(150, 150);
            
            vrcholy.add(v1);
            vrcholy.add(v2);
            vrcholy.add(v3);
            vrcholy.add(v4);
            
            // --- VYTVOR HRANU ---
            Hrana h1 = new Hrana(v1, v2);
            Hrana h2 = new Hrana(v3, v4);
            Hrana h3 = new Hrana(v1, v3);
            
            hrany.add(h1);
            hrany.add(h2);
            hrany.add(h3);
            
            // --- VYKRESLI NA PLÁTNO ---
            root.getChildren().addAll(
                    h1.getLine(),
                    h2.getLine(),
                    h3.getLine(),
                    v1.getCircle(),
                    v2.getCircle(),
                    v3.getCircle(),
                    v4.getCircle()
            );
            */
           
           
            // --- SCÉNA ---
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            
            if (1 == 2) {
                koniecHry(stage);
            }
    
    }
    
    private void koniecHry(Stage stage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Koniec hry");
        dialog.setHeaderText("Chceš hrať znova?");
        dialog.setContentText("Zadaj 'áno' alebo 'nie':");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(answer -> {
            if (answer.equalsIgnoreCase("áno")) {
                zacniHru(stage); // spustí hru znova
            } else {
                System.out.println("Hra skončila. Dovidenia!");
                stage.close();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public int spocitajPretnutia() {
        int total = 0;
        if (hrany.size() < 2) {
            return total;
        }
        for (int i = 0; i < hrany.size(); i++) {
            Hrana h1 = hrany.get(i);
            for (int j = i + 1; j < hrany.size(); j++) {
                Hrana h2 = hrany.get(j);
                
                // preskočiť ak zdieľajú vrchol (v planarity sa to neráta)
                if (h1.getV1() == h2.getV1() || h1.getV1() == h2.getV2() ||
                    h1.getV2() == h2.getV1() || h1.getV2() == h2.getV2())
                    continue;
    
                if (edgesIntersect(h1.getV1(), h1.getV2(), h2.getV1(), h2.getV2()))
                    total++;
            }
        }
        return total;
    }
    
    public int orient(Vrchol a, Vrchol b, Vrchol c) {
        double val = (b.getX() - a.getX()) * (c.getY() - a.getY()) -
                    (b.getY() - a.getY()) * (c.getX() - a.getX());

        if (val > 0) return 1;   // C je vľavo od A→B
        if (val < 0) return -1;  // C je vpravo od A→B
        return 0;                // kolineárne
    }
    
    public boolean edgesIntersect(Vrchol a, Vrchol b, Vrchol c, Vrchol d) {
        int o1 = orient(a, b, c);
        int o2 = orient(a, b, d);
        int o3 = orient(c, d, a);
        int o4 = orient(c, d, b);
    
        if (o1 != o2 && o3 != o4)
            return true;
    
        return false;
    }
    
    public boolean edgeIntersectAll(Hrana h) {
        if (hrany.size() == 0)
            return false;
        else {
            for (Hrana hrana : hrany) {
                if (h == hrana ) {
                    continue;
                }
                else if (h.getV1() == hrana.getV1() || h.getV1() == hrana.getV2() ||
                         h.getV2() == hrana.getV1() || h.getV2() == hrana.getV2()) {
                    continue;
                }
                else if (edgesIntersect(h.getV1(), h.getV2(), hrana.getV1(), hrana.getV2())){
                    System.out.println("temp h:");
                    System.out.println(h.getV1().getX()+ "  " +h.getV1().getY());
                    System.out.println(h.getV2().getX()+ "  " +h.getV2().getY());
                    System.out.println("nakreslena hrana:");
                    System.out.println(hrana.getV1().getX()+ "  " +hrana.getV1().getY());
                    System.out.println(hrana.getV2().getX()+ "  " +hrana.getV2().getY());
                    
                    return true;
                }
            }
            return false;
        }
    }
}
