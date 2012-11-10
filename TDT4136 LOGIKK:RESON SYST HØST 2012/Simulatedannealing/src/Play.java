
public class Play {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		Carton c = new Carton(120,120,1);
		show("\n N = " + c.n + " M = " + c.m + " K = " + c.k);
		long begin = System.currentTimeMillis();
		c.runSA(c.carton);
		long end = System.currentTimeMillis();
		show("\n This operations took: " + (end - begin) + " ms");
	}

	public static void show(String o) {
		System.out.println(o);
	}

}
