package de.gaalop.tba.generatedTests;

import java.util.HashMap;

public class CircleOnlyVars implements GAProgram {
	// input variables
	private float y1$0;
	private float y2$0;
	private float y3$0;
	private float x2$0;
	private float x3$0;
	private float x1$0;

	// output variables
	private float m$1;
	private float r$0;
	private float m$2;

	@Override
	public float getValue(String varName) {
		if (varName.equals("m$1")) return m$1;
		if (varName.equals("r$0")) return r$0;
		if (varName.equals("m$2")) return m$2;
		return 0.0f;
	}

	@Override
	public HashMap<String,Float> getValues() {
		HashMap<String,Float> result = new HashMap<String,Float>();
		result.put("m$1",m$1);
		result.put("r$0",r$0);
		result.put("m$2",m$2);
		return result;
	}
	@Override
	public boolean setValue(String varName, float value) {
		if (varName.equals("y1$0")) { y1$0 = value; return true; }
		if (varName.equals("y2$0")) { y2$0 = value; return true; }
		if (varName.equals("y3$0")) { y3$0 = value; return true; }
		if (varName.equals("x2$0")) { x2$0 = value; return true; }
		if (varName.equals("x3$0")) { x3$0 = value; return true; }
		if (varName.equals("x1$0")) { x1$0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		p1$4 = ((0.5f * (y1$0 * y1$0)) + (0.5f * (x1$0 * x1$0))); // einf;
		p2$4 = ((0.5f * (y2$0 * y2$0)) + (0.5f * (x2$0 * x2$0))); // einf;
		p3$4 = ((0.5f * (y3$0 * y3$0)) + (0.5f * (x3$0 * x3$0))); // einf;
		c$7 = ((((((p1$4 - p2$4)) * y3$0) - (p1$4 * y2$0)) - (p3$4 * ((y1$0 - y2$0)))) + (p2$4 * y1$0)); // e1^e3;
		c$10 = ((((-((((p1$4 - p2$4)) * x3$0))) + (p1$4 * x2$0)) + (p3$4 * ((x1$0 - x2$0)))) - (p2$4 * x1$0)); // e2^e3;
		c$13 = ((((((p2$4 * x1$0) - (p1$4 * x2$0))) * y3$0) - (p3$4 * (((x1$0 * y2$0) - (x2$0 * y1$0))))) - (x3$0 * (((p2$4 * y1$0) - (p1$4 * y2$0))))); // e3^einf;
		c$14 = ((((-((((x1$0 - x2$0)) * y3$0))) + (x1$0 * y2$0)) + (x3$0 * ((y1$0 - y2$0)))) - (x2$0 * y1$0)); // e3^e0;
		mtmp$1 = ((2.0f * c$14) * c$7); // e1;
		mtmp$2 = ((2.0f * c$10) * c$14); // e2;
		mtmp$5 = (-((2.0f * (c$14 * c$14)))); // e0;
		mtmp$21 = 0.0f; // e1^einf^e0;
		mtmp$24 = 0.0f; // e2^einf^e0;
		m$1 = ((mtmp$21 / mtmp$5) + (mtmp$1 / mtmp$5)); // e1;
		m$2 = ((mtmp$24 / mtmp$5) + (mtmp$2 / mtmp$5)); // e2;
		r$0 = ((float) Math.sqrt((float) Math.abs((((c$7 * c$7) - ((2.0f * c$13) * c$14)) + (c$10 * c$10)))) / (float) Math.abs(c$14)); // 1.0;
	}

	private float p2$4;
	private float p1$4;
	private float c$7;
	private float mtmp$1;
	private float mtmp$21;
	private float mtmp$5;
	private float mtmp$2;
	private float c$14;
	private float mtmp$24;
	private float c$13;
	private float p3$4;
	private float c$10;

}
