public class Level {
    private int urovenLevelu;
    private int pocetVrcholov;
    
    public Level(int urovenLevelu) {
        this.urovenLevelu = urovenLevelu;
        this.pocetVrcholov = this.urovenLevelu + 3;
    }
    
    public void setLevel(int urovenLevelu) {
        this.urovenLevelu = urovenLevelu;
        this.pocetVrcholov = this.urovenLevelu + 3;
    }
    
    public int getUrovenLevelu() {
        return this.urovenLevelu;
    }
    
    public int getPocetVrcholov() {
        return this.pocetVrcholov;
    }
}
