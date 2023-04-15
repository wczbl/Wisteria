package game.entity.particle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import game.entity.Entity;
import game.gfx.core.Screen;

public class ParticleSystem extends Entity {

	private static final double[] COS = new double[360];
	private static final double[] SIN = new double[360];
	
	private List<Particle> particles = new ArrayList<Particle>();
	private double wind;
	private double gravity;
	private long lastTime;
	private int delay;
	
	private Comparator<Particle> particleComparator = new Comparator<Particle>() {
		public int compare(Particle p0, Particle p1) {
			if (p1.getTime() < p0.getTime()) return 1;
			if (p1.getTime() > p0.getTime()) return -1;
			return 0;
		}
	};
	
	
	@SuppressWarnings("deprecation")
	public ParticleSystem(Class<? extends Particle> clazz, int count, double wind, double gravity, int delay) throws IllegalAccessException, InstantiationException {
		this.wind = wind;
		this.gravity = gravity;
		this.delay = delay;
		initSinCos();
		for(int i = 0; i < count; i++) {
			Particle p = clazz.newInstance();
			p.setRemoved(true);
			this.particles.add(p);
		}
	}
	
	public void createExplosion(int x, int y, double xa, double ya, int count) {
		count = Math.min(this.particles.size(), count);
		while(count > 0) {
			count--;
			int angle = this.random.nextInt(360);
			updateDeadParticle(x + this.random.nextInt(9) - 4, y + this.random.nextInt(5) - 2, xa + COS[angle], ya + SIN[angle]);
		}
	}
	
	public void tick() {
		if(System.currentTimeMillis() - this.lastTime > this.delay) {
			for (Particle p : this.particles) {
				if (p.isRemoved()) continue;
				p.tick();
				p.add(this.wind, gravity);
			}
			
			Collections.sort(this.particles, this.particleComparator);
			this.lastTime = System.currentTimeMillis();
		}
	}
		
	public void render(Screen screen) {
		for(Particle p : this.particles) {
			if(p.isRemoved()) continue;
			
			p.render(screen);
		}
	}

	private void updateDeadParticle(int x, int y, double xa, double ya) {
		for(Particle p : this.particles) {
			if(p.isRemoved()) {
				p.setRemoved(false);
				p.initParticle(x, y, xa, ya);
				break;
			}
		}
	}
	
	private void initSinCos() {
		for(int i = 0; i < 360; i++) {
			COS[i] = Math.cos(Math.toRadians(i));
			SIN[i] = Math.sin(Math.toRadians(i));
		}
	}
}