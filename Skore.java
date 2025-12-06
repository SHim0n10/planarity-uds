public class Skore {
    private int maximalneSkore;
    private int aktSkore;
    private int optimalnyPocetTahov;
    private int aktPocetTahov;
    public Skore(int urovenLevelu, int optimalnyPocetTahov) {
        this.maximalneSkore = urovenLevelu * 1000;
        this.optimalnyPocetTahov = optimalnyPocetTahov;
        this.aktSkore = this.maximalneSkore;
        this.aktPocetTahov = 0;
    }
    
    public void vypocitajSkore() {
        if (this.aktPocetTahov <= this.optimalnyPocetTahov) {
            this.aktSkore = this.maximalneSkore;
        } else {
            int rozdielTahov = this.aktPocetTahov - this.optimalnyPocetTahov;
            int i = 50 * (rozdielTahov - 1);
            this.aktSkore = rozdielTahov * 200 + i; 
        }
    }
    
    public int getAktualneSkore() {
        return this.aktSkore;
    }
    
    public int getMaximalneSkore() {
        return this.maximalneSkore;
    }
    
    public int getAktualnyPocetTahov() {
        return this.aktPocetTahov;
    }
    
    public int getOptimalnyPocetTahov() {
        return this.optimalnyPocetTahov;
    }
    
    public void setAktualnyPocetTahov(int pocetTahov) {
        this.aktPocetTahov += pocetTahov;
    }
}
