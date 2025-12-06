import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

public class Vrchol {

    private double x;
    private double y;
    private double offsetY;
    private double offsetX;
    private Random nahoda;
    
    private ArrayList<Hrana> hrany;
    private Circle circle;

    public Vrchol(double x, double y) {
        this.x = x;
        this.y = y;
        this.hrany = new ArrayList<Hrana>();
        this.nahoda = new Random();

        // vytvoríme grafický bod
        this.circle = new Circle(x, y, 7); // polomer 7 px
        this.circle.setFill(Color.BLUE);
        
        circle.setOnMousePressed(e -> {
            offsetX = e.getSceneX() - circle.getCenterX();
            offsetY = e.getSceneY() - circle.getCenterY();
        });

        // Pri ťahaní aktualizujeme pozíciu kruhu
        circle.setOnMouseDragged(e -> {
            this.x = e.getSceneX() - offsetX;
            this.y = e.getSceneY() - offsetY;
            circle.setCenterX(this.x);
            circle.setCenterY(this.y);
            for (Hrana hrana : hrany) {
                hrana.update();
            }
        });
    }

    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Circle getCircle() {
        return circle;
    }
    
    public void addHrana(Hrana hrana) {
        this.hrany.add(hrana);
    }
    
    public void randomPostition(int maxX, int maxY) {
        this.x = nahoda.nextInt(maxX);
        this.y = nahoda.nextInt(maxY);
        circle.setCenterX(this.x);
        circle.setCenterY(this.y);
        for (Hrana hrana : hrany) {
            hrana.update();
        }
    }
}
