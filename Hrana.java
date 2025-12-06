import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Hrana {
    private Vrchol v1;
    private Vrchol v2;
    private Line line;
    
    public Hrana(Vrchol vrchol1, Vrchol vrchol2) {
        this.v1 = vrchol1;
        this.v2 = vrchol2;
        this.v1.addHrana(this);
        this.v2.addHrana(this);
        
        this.line = new Line(
        v1.getX(),
        v1.getY(),
        v2.getX(),
        v2.getY()
        );
        
        this.line.setStrokeWidth(3);
        this.line.setStroke(Color.BLACK);
    }
    
    public Line getLine() {
        return this.line;
    }
    
    public void update() {
        this.line.setStartX(v1.getX());
        this.line.setStartY(v1.getY());
        this.line.setEndX(v2.getX());
        this.line.setEndY(v2.getY());
    }
    
    public Vrchol getV1() {
        return this.v1;
    }
    
    public Vrchol getV2() {
        return this.v2;
    }
}
