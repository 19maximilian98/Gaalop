package de.gaalop.tba.generatedTests;

import java.util.HashMap;

public class GPSOnlyVars implements GAProgram {
	// input variables
	private float d1$0;
	private float d2$0;
	private float d3$0;
	private float sat3x$0;
	private float sat3y$0;
	private float sat3z$0;
	private float sat2y$0;
	private float sat1z$0;
	private float sat2x$0;
	private float sat1y$0;
	private float sat1x$0;
	private float sat2z$0;

	// output variables
	private float rc2N$3;
	private float z32$0;
	private float z12$0;
	private float z11$0;
	private float rc2N$1;
	private float rc2N$2;
	private float z22$0;
	private float z31$0;
	private float rc1N$2;
	private float rc1N$3;
	private float rc1N$1;
	private float z21$0;

	@Override
	public float getValue(String varName) {
		if (varName.equals("rc2N$3")) return rc2N$3;
		if (varName.equals("z32$0")) return z32$0;
		if (varName.equals("z12$0")) return z12$0;
		if (varName.equals("z11$0")) return z11$0;
		if (varName.equals("rc2N$1")) return rc2N$1;
		if (varName.equals("rc2N$2")) return rc2N$2;
		if (varName.equals("z22$0")) return z22$0;
		if (varName.equals("z31$0")) return z31$0;
		if (varName.equals("rc1N$2")) return rc1N$2;
		if (varName.equals("rc1N$3")) return rc1N$3;
		if (varName.equals("rc1N$1")) return rc1N$1;
		if (varName.equals("z21$0")) return z21$0;
		return 0.0f;
	}

	@Override
	public HashMap<String,Float> getValues() {
		HashMap<String,Float> result = new HashMap<String,Float>();
		result.put("rc2N$3",rc2N$3);
		result.put("z32$0",z32$0);
		result.put("z12$0",z12$0);
		result.put("z11$0",z11$0);
		result.put("rc2N$1",rc2N$1);
		result.put("rc2N$2",rc2N$2);
		result.put("z22$0",z22$0);
		result.put("z31$0",z31$0);
		result.put("rc1N$2",rc1N$2);
		result.put("rc1N$3",rc1N$3);
		result.put("rc1N$1",rc1N$1);
		result.put("z21$0",z21$0);
		return result;
	}
	@Override
	public boolean setValue(String varName, float value) {
		if (varName.equals("d1$0")) { d1$0 = value; return true; }
		if (varName.equals("d2$0")) { d2$0 = value; return true; }
		if (varName.equals("d3$0")) { d3$0 = value; return true; }
		if (varName.equals("sat3x$0")) { sat3x$0 = value; return true; }
		if (varName.equals("sat3y$0")) { sat3y$0 = value; return true; }
		if (varName.equals("sat3z$0")) { sat3z$0 = value; return true; }
		if (varName.equals("sat2y$0")) { sat2y$0 = value; return true; }
		if (varName.equals("sat1z$0")) { sat1z$0 = value; return true; }
		if (varName.equals("sat2x$0")) { sat2x$0 = value; return true; }
		if (varName.equals("sat1y$0")) { sat1y$0 = value; return true; }
		if (varName.equals("sat1x$0")) { sat1x$0 = value; return true; }
		if (varName.equals("sat2z$0")) { sat2z$0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		sat1$4 = (0.5f * ((((sat1z$0 * sat1z$0) + (sat1y$0 * sat1y$0)) + (sat1x$0 * sat1x$0)))); // einf;
		sat2$4 = (0.5f * ((((sat2z$0 * sat2z$0) + (sat2y$0 * sat2y$0)) + (sat2x$0 * sat2x$0)))); // einf;
		sat3$4 = (0.5f * ((((sat3z$0 * sat3z$0) + (sat3y$0 * sat3y$0)) + (sat3x$0 * sat3x$0)))); // einf;
		sph1$4 = (sat1$4 - (0.5f * (d1$0 * d1$0))); // einf;
		sph2$4 = (sat2$4 - (0.5f * (d2$0 * d2$0))); // einf;
		sph3$4 = (sat3$4 - (0.5f * (d3$0 * d3$0))); // einf;
		rcPp$16 = ((((((sat1x$0 * sat2y$0) - (sat1y$0 * sat2x$0))) * sat3z$0) - ((((sat1x$0 * sat2z$0) - (sat1z$0 * sat2x$0))) * sat3y$0)) + ((((sat1y$0 * sat2z$0) - (sat1z$0 * sat2y$0))) * sat3x$0)); // e1^e2^e3;
		rcPp$17 = ((((((sat1x$0 * sat2y$0) - (sat1y$0 * sat2x$0))) * sph3$4) + (sat3x$0 * (((sat1y$0 * sph2$4) - (sat2y$0 * sph1$4))))) - (sat3y$0 * (((sat1x$0 * sph2$4) - (sat2x$0 * sph1$4))))); // e1^e2^einf;
		rcPp$18 = ((((-((((sat1x$0 - sat2x$0)) * sat3y$0))) + (((sat1y$0 - sat2y$0)) * sat3x$0)) + (sat1x$0 * sat2y$0)) - (sat1y$0 * sat2x$0)); // e1^e2^e0;
		rcPp$19 = ((((((sat1x$0 * sat2z$0) - (sat1z$0 * sat2x$0))) * sph3$4) + (sat3x$0 * (((sat1z$0 * sph2$4) - (sat2z$0 * sph1$4))))) - (sat3z$0 * (((sat1x$0 * sph2$4) - (sat2x$0 * sph1$4))))); // e1^e3^einf;
		rcPp$20 = ((((-((((sat1x$0 - sat2x$0)) * sat3z$0))) + (((sat1z$0 - sat2z$0)) * sat3x$0)) + (sat1x$0 * sat2z$0)) - (sat1z$0 * sat2x$0)); // e1^e3^e0;
		rcPp$21 = ((((-((((sat1x$0 - sat2x$0)) * sph3$4))) + (sat1x$0 * sph2$4)) + (sat3x$0 * ((sph1$4 - sph2$4)))) - (sat2x$0 * sph1$4)); // e1^einf^e0;
		rcPp$22 = ((((((sat1y$0 * sat2z$0) - (sat1z$0 * sat2y$0))) * sph3$4) + (sat3y$0 * (((sat1z$0 * sph2$4) - (sat2z$0 * sph1$4))))) - (sat3z$0 * (((sat1y$0 * sph2$4) - (sat2y$0 * sph1$4))))); // e2^e3^einf;
		rcPp$23 = ((((-((((sat1y$0 - sat2y$0)) * sat3z$0))) + (((sat1z$0 - sat2z$0)) * sat3y$0)) + (sat1y$0 * sat2z$0)) - (sat1z$0 * sat2y$0)); // e2^e3^e0;
		rcPp$24 = ((((-((((sat1y$0 - sat2y$0)) * sph3$4))) + (sat1y$0 * sph2$4)) + (sat3y$0 * ((sph1$4 - sph2$4)))) - (sat2y$0 * sph1$4)); // e2^einf^e0;
		rcPp$25 = ((((-((((sat1z$0 - sat2z$0)) * sph3$4))) + (sat1z$0 * sph2$4)) + (sat3z$0 * ((sph1$4 - sph2$4)))) - (sat2z$0 * sph1$4)); // e3^einf^e0;
		len$0 = (float) Math.sqrt((float) Math.abs((((((((rcPp$25 * rcPp$25) + (rcPp$24 * rcPp$24)) + ((2.0f * rcPp$22) * rcPp$23)) + (rcPp$21 * rcPp$21)) + ((2.0f * rcPp$19) * rcPp$20)) + ((2.0f * rcPp$17) * rcPp$18)) - (rcPp$16 * rcPp$16)))); // 1.0;
		rcPpDual$6 = (-rcPp$25); // e1^e2;
		rcPpDual$8 = (-rcPp$22); // e1^einf;
		rcPpDual$10 = (-rcPp$21); // e2^e3;
		rcPpDual$12 = (-rcPp$20); // e2^e0;
		rcPpDual$13 = (-rcPp$17); // e3^einf;
		rc1$1 = (((((rcPpDual$12 * rcPpDual$6) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))) + ((rcPp$18 * rcPp$24) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((rcPp$16 * rcPp$23) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) + ((len$0 * rcPp$23) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e1;
		rc1$2 = ((((-(((rcPp$23 * rcPpDual$6) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))))) - ((rcPp$16 * rcPpDual$12) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) + ((len$0 * rcPpDual$12) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) + ((rcPp$18 * rcPpDual$10) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e2;
		rc1$3 = ((((-(((rcPpDual$10 * rcPpDual$12) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))))) - ((rcPp$23 * rcPp$24) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((rcPp$16 * rcPp$18) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) + ((len$0 * rcPp$18) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e3;
		rc1$4 = (((((-(((rcPp$23 * rcPpDual$8) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))))) - ((rcPp$18 * rcPpDual$13) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((rcPp$19 * rcPpDual$12) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((rcPp$16 * rcPp$16) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) + ((len$0 * rcPp$16) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // einf;
		rc1$5 = (((-(((rcPpDual$12 * rcPpDual$12) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))))) - ((rcPp$23 * rcPp$23) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((rcPp$18 * rcPp$18) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e0;
		rc1$16 = ((((rcPp$18 * rcPpDual$6) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))) - ((rcPp$24 * rcPpDual$12) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) + ((rcPp$23 * rcPpDual$10) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e1^e2^e3;
		rc1$17 = (((-(((rcPpDual$12 * rcPpDual$8) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))))) + ((rcPp$16 * rcPpDual$6) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) + ((rcPp$19 * rcPp$23) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e1^e2^einf;
		rc1$18 = 0.0f; // e1^e2^e0;
		rc1$19 = (((-(((rcPp$18 * rcPpDual$8) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))))) + ((rcPp$23 * rcPpDual$13) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) + ((rcPp$16 * rcPp$24) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e1^e3^einf;
		rc1$20 = 0.0f; // e1^e3^e0;
		rc1$21 = 0.0f; // e1^einf^e0;
		rc1$22 = ((((rcPpDual$12 * rcPpDual$13) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))) + ((rcPp$16 * rcPpDual$10) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((rcPp$18 * rcPp$19) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e2^e3^einf;
		rc1$23 = 0.0f; // e2^e3^e0;
		rc1$24 = 0.0f; // e2^einf^e0;
		rc1$25 = 0.0f; // e3^einf^e0;
		rc1N$1 = ((((((((rc1$21 * rc1$5) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18)))) + ((rc1$1 * rc1$5) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$20 * rc1$3) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) + ((rc1$20 * rc1$25) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) + ((rc1$18 * rc1$24) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$16 * rc1$23) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$18 * rc1$2) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))); // e1;
		rc1N$2 = ((((((((rc1$24 * rc1$5) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18)))) + ((rc1$2 * rc1$5) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$23 * rc1$3) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) + ((rc1$23 * rc1$25) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$18 * rc1$21) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) + ((rc1$16 * rc1$20) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) + ((rc1$1 * rc1$18) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))); // e2;
		rc1N$3 = ((((((((rc1$3 * rc1$5) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18)))) + ((rc1$25 * rc1$5) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$23 * rc1$24) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) + ((rc1$2 * rc1$23) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$20 * rc1$21) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) + ((rc1$1 * rc1$20) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$16 * rc1$18) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))); // e3;
		rc1N$4 = (((((((((((rc1$4 * rc1$5) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18)))) + ((rc1$25 * rc1$3) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$25 * rc1$25) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$24 * rc1$24) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) + ((rc1$2 * rc1$24) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$22 * rc1$23) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$21 * rc1$21) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) + ((rc1$1 * rc1$21) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$19 * rc1$20) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$17 * rc1$18) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))); // einf;
		rc1N$5 = (((((rc1$5 * rc1$5) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18)))) - ((rc1$23 * rc1$23) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$20 * rc1$20) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))) - ((rc1$18 * rc1$18) / (((((rc1$5 * rc1$5) + (rc1$23 * rc1$23)) + (rc1$20 * rc1$20)) + (rc1$18 * rc1$18))))); // e0;
		rc2$1 = (((((rcPpDual$12 * rcPpDual$6) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))) + ((rcPp$18 * rcPp$24) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((rcPp$16 * rcPp$23) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((len$0 * rcPp$23) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e1;
		rc2$2 = ((((-(((rcPp$23 * rcPpDual$6) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))))) - ((rcPp$16 * rcPpDual$12) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((len$0 * rcPpDual$12) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) + ((rcPp$18 * rcPpDual$10) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e2;
		rc2$3 = ((((-(((rcPpDual$10 * rcPpDual$12) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))))) - ((rcPp$23 * rcPp$24) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((rcPp$16 * rcPp$18) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((len$0 * rcPp$18) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e3;
		rc2$4 = (((((-(((rcPp$23 * rcPpDual$8) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))))) - ((rcPp$18 * rcPpDual$13) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((rcPp$19 * rcPpDual$12) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((rcPp$16 * rcPp$16) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((len$0 * rcPp$16) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // einf;
		rc2$5 = (((-(((rcPpDual$12 * rcPpDual$12) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))))) - ((rcPp$23 * rcPp$23) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((rcPp$18 * rcPp$18) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e0;
		rc2$16 = ((((rcPp$18 * rcPpDual$6) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))) - ((rcPp$24 * rcPpDual$12) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) + ((rcPp$23 * rcPpDual$10) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e1^e2^e3;
		rc2$17 = (((-(((rcPpDual$12 * rcPpDual$8) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))))) + ((rcPp$16 * rcPpDual$6) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) + ((rcPp$19 * rcPp$23) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e1^e2^einf;
		rc2$18 = 0.0f; // e1^e2^e0;
		rc2$19 = (((-(((rcPp$18 * rcPpDual$8) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))))) + ((rcPp$23 * rcPpDual$13) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) + ((rcPp$16 * rcPp$24) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e1^e3^einf;
		rc2$20 = 0.0f; // e1^e3^e0;
		rc2$21 = 0.0f; // e1^einf^e0;
		rc2$22 = ((((rcPpDual$12 * rcPpDual$13) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18)))) + ((rcPp$16 * rcPpDual$10) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))) - ((rcPp$18 * rcPp$19) / ((((rcPpDual$12 * rcPpDual$12) + (rcPp$23 * rcPp$23)) + (rcPp$18 * rcPp$18))))); // e2^e3^einf;
		rc2$23 = 0.0f; // e2^e3^e0;
		rc2$24 = 0.0f; // e2^einf^e0;
		rc2$25 = 0.0f; // e3^einf^e0;
		rc2N$1 = ((((((((rc2$21 * rc2$5) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18)))) + ((rc2$1 * rc2$5) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$20 * rc2$3) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) + ((rc2$20 * rc2$25) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) + ((rc2$18 * rc2$24) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$16 * rc2$23) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$18 * rc2$2) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))); // e1;
		rc2N$2 = ((((((((rc2$24 * rc2$5) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18)))) + ((rc2$2 * rc2$5) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$23 * rc2$3) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) + ((rc2$23 * rc2$25) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$18 * rc2$21) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) + ((rc2$16 * rc2$20) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) + ((rc2$1 * rc2$18) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))); // e2;
		rc2N$3 = ((((((((rc2$3 * rc2$5) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18)))) + ((rc2$25 * rc2$5) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$23 * rc2$24) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) + ((rc2$2 * rc2$23) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$20 * rc2$21) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) + ((rc2$1 * rc2$20) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$16 * rc2$18) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))); // e3;
		rc2N$4 = (((((((((((rc2$4 * rc2$5) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18)))) + ((rc2$25 * rc2$3) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$25 * rc2$25) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$24 * rc2$24) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) + ((rc2$2 * rc2$24) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$22 * rc2$23) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$21 * rc2$21) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) + ((rc2$1 * rc2$21) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$19 * rc2$20) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$17 * rc2$18) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))); // einf;
		rc2N$5 = (((((rc2$5 * rc2$5) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18)))) - ((rc2$23 * rc2$23) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$20 * rc2$20) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))) - ((rc2$18 * rc2$18) / (((((rc2$5 * rc2$5) + (rc2$23 * rc2$23)) + (rc2$20 * rc2$20)) + (rc2$18 * rc2$18))))); // e0;
		z11$0 = ((1.4142135f * (float) Math.sqrt((((((sat1$4 * rc1N$5) + rc1N$4) - (sat1z$0 * rc1N$3)) - (sat1y$0 * rc1N$2)) - (sat1x$0 * rc1N$1)))) - d1$0); // 1.0;
		z12$0 = ((1.4142135f * (float) Math.sqrt((((((sat1$4 * rc2N$5) + rc2N$4) - (sat1z$0 * rc2N$3)) - (sat1y$0 * rc2N$2)) - (sat1x$0 * rc2N$1)))) - d1$0); // 1.0;
		z21$0 = ((1.4142135f * (float) Math.sqrt((((((sat2$4 * rc1N$5) + rc1N$4) - (sat2z$0 * rc1N$3)) - (sat2y$0 * rc1N$2)) - (sat2x$0 * rc1N$1)))) - d2$0); // 1.0;
		z22$0 = ((1.4142135f * (float) Math.sqrt((((((sat2$4 * rc2N$5) + rc2N$4) - (sat2z$0 * rc2N$3)) - (sat2y$0 * rc2N$2)) - (sat2x$0 * rc2N$1)))) - d2$0); // 1.0;
		z31$0 = ((1.4142135f * (float) Math.sqrt((((((sat3$4 * rc1N$5) + rc1N$4) - (sat3z$0 * rc1N$3)) - (sat3y$0 * rc1N$2)) - (sat3x$0 * rc1N$1)))) - d3$0); // 1.0;
		z32$0 = ((1.4142135f * (float) Math.sqrt((((((sat3$4 * rc2N$5) + rc2N$4) - (sat3z$0 * rc2N$3)) - (sat3y$0 * rc2N$2)) - (sat3x$0 * rc2N$1)))) - d3$0); // 1.0;
	}

	private float rc2$1;
	private float rc2$2;
	private float rc1$4;
	private float rc1$5;
	private float rc2N$4;
	private float rc2N$5;
	private float rc1$3;
	private float rc2$18;
	private float rc1$2;
	private float rc2$19;
	private float rc1$1;
	private float rc2$16;
	private float rc2$17;
	private float rc1$19;
	private float rc1$17;
	private float rc1$18;
	private float rc2$5;
	private float rc2$4;
	private float rc1$16;
	private float rc2$3;
	private float rcPpDual$6;
	private float sph2$4;
	private float sph1$4;
	private float rc2$23;
	private float rc2$24;
	private float rc2$25;
	private float rc2$20;
	private float rc2$21;
	private float rc2$22;
	private float rcPp$22;
	private float rcPp$21;
	private float rcPp$20;
	private float rcPp$25;
	private float rcPp$24;
	private float rcPp$23;
	private float len$0;
	private float sat1$4;
	private float rcPpDual$8;
	private float sph3$4;
	private float rcPpDual$13;
	private float rcPpDual$12;
	private float rcPp$17;
	private float rcPp$16;
	private float rcPp$19;
	private float rcPpDual$10;
	private float rcPp$18;
	private float rc1$20;
	private float sat3$4;
	private float rc1$21;
	private float rc1$22;
	private float rc1$23;
	private float rc1$24;
	private float sat2$4;
	private float rc1$25;
	private float rc1N$4;
	private float rc1N$5;

}
