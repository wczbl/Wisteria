package game.gfx.weather;

public class Weather {

	private boolean isRain;
	private boolean isThunder;
	
	public Weather(boolean rain, boolean thunder) {
		this.isRain = rain;
		this.isThunder = thunder;
	}
	
	public Weather() {}
	
	public void setThunder(boolean isThunder) { this.isThunder = isThunder; }
	public void setRain(boolean isRain) { this.isRain = isRain; }
	public boolean isThunder() { return this.isThunder; }
	public boolean isRain() { return this.isRain; }
}