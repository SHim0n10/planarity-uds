public class Level {
    private int urovenLevelu;
    private int pocetVrcholov;
    
    public Level(int urovenLevelu) {
        this.urovenLevelu = urovenLevelu;
        this.pocetVrcholov = this.urovenLevelu + 2;
    }
    
    public int getUrovenLevelu() {
        return this.urovenLevelu;
    }
}
