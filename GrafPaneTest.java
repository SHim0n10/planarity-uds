import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;

public class GrafPaneTest extends Application {
    private ArrayList<Vrchol> vrcholy;
    private ArrayList<Hrana> hrany;

    @Override
    public void start(Stage stage) {
        vrcholy = new ArrayList<Vrchol>();
        hrany = new ArrayList<Hrana>();
        

        // --- NÁŠ PLÁTNO (Pane) ---
        Pane root = new Pane();

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

        // --- SCÉNA ---
        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Test vykreslenia Hrana + Vrchol");
        stage.setScene(scene);
        stage.show();
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
}
