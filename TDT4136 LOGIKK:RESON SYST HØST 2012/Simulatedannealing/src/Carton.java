import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Carton {

	private double T;
	private double Tmax = 15;
	private double Q;
	private double P;
	private double Pmax;

	private ArrayList<int[][]> neighbours;
	private int[][] bestBoard;

	public int n;
	public int m;
	public int k;
	public int[][] carton;
	public int eggs;

	public Carton(int n, int m, int k) {

		this.T = 0;
		this.Q = 0;
		this.P = 0;
		this.Pmax = 0;
		this.neighbours = new ArrayList<int[][]>();
		this.bestBoard = new int[n][m];

		this.n = n;
		this.m = m;
		this.k = k;

		if (n >= m) {
			this.eggs = m * k;
		} else {
			this.eggs = n * k;
		}
		this.carton = new int[n][m];

		// Begin at a start point P (either user-selected or
		// randomly-generated).
		this.carton = putEggsInCarton(this.carton);

	}

	public void runSA(int[][] board) {

		System.out.print("\n START BOARD WITH A TEMPERATURE " + this.Tmax
				+ "°C\n" + this.toString(board) + "\n");
		// Set the temperature, T, to it’s starting value: Tmax
		this.T = this.Tmax;
		// Evaluate P with an objective function, F. This yields the value F(P).
		this.P = objectiveFunction(board);

		while (this.T >= 0) {

			/*
			 * If F (P ) ≥ Ftarget then EXIT and return P as the solution; else
			 * continue. F(target) må være lik 1 for at løsningen skal være
			 * perfekt
			 */
			if (this.P == 1.0 && checkAll(board)) {
				System.out.println("\n END BOARD \n" + toString(board));
				System.out.println("Search ended on a temp " + this.T);
				return;
			}

			else {
				// Generate n neighbors of P in the search space: (P1, P2, ...,
				// Pn).
				Random random = new Random();
				int r = random.nextInt(n * m) + (n * m);

				this.neighbours = GenerateRandomlyNeighbors(board, r);
				/*
				 * Evaluate each neighbor, yielding (F (P1), F (P2), ..., F
				 * (Pr)). Let Pmax be the neighbor with the highest evaluation.
				 */
				this.bestBoard = EvaluateEachNeighbor(this.neighbours);

				this.Pmax = objectiveFunction(this.bestBoard);

				if (this.Pmax == 1.0 && checkAll(this.bestBoard)) {
					System.out.println("\n END BOARD WITH A TEMPERATURE "
							+ this.T + "°C\n" + toString(this.bestBoard));
					System.out
							.println("\n THIS SEARCH ENDED AFTER TEMPERATUR CHANGED "
									+ (this.Tmax - this.T) + "°C");
					System.out.println("\n WITH A SCORE VALUE OF "
							+ objectiveFunction(this.bestBoard));
					return;
				}
				/*
				 * Let q = F(Pmax) minus F(P)/ F(P)
				 */
				this.Q = (Pmax - P) / P;

				double tempExpr = Math.exp(((-this.Q) / this.T));
				tempExpr = convertToStandardDecimal(tempExpr);

				this.P = Math.min(1, tempExpr);
				this.P = convertToStandardDecimal(this.P);
				double X = random.nextDouble();
				X = convertToStandardDecimal(X);
				/*
				 * Ifx>pthenP Pmax ;;(Exploiting)
				 */
				if (X > this.P) {
					this.P = this.Pmax;
				} else if (X <= this.P) {

					// else P a random choice among the n neighbors. ;;
					// (Exploring)
					// this.bestBoard = EvaluateEachNeighbor(this.neighbours);

					this.P = objectiveFunction(this.bestBoard);
					// T T dT

				}
				this.T -= 0.001;
			}
		}

		System.out
				.print("\nSøket endte uten et korrekt svar , beste resultat ble");
		System.out.println("\n" + toString(this.bestBoard));
		System.out.println("\n WITH A SCORE VALUE OF "
				+ objectiveFunction(this.bestBoard));

	}

	public int[][] EvaluateEachNeighbor(ArrayList<int[][]> neighbours) {

		// kunne også ha brukt Double.MIN_VALUE
		double low = -10000;
		int[][] highChild = null;
		for (int i = 0; i < neighbours.size(); i++) {

			double tempLow = objectiveFunction(neighbours.get(i));
			// Første kan vil denne slå til uansett hva så vil tempLow være
			// større en 0

			if (tempLow >= low) {

				low = tempLow;

				/*
				 * Ok, nå har vi den med "riktig verdi", endre low slik at neste
				 * kan bli riktig evaluert highChild blir det barnet som har
				 * høyest p verdi, som er nærmest "target"
				 */

				highChild = neighbours.get(i);
				if (low == 1) {

					/*
					 * vi har målet vårt, ingen grunn til å gå gjennom de
					 * resterende naboene
					 */

					// System.out.println("BEST SCORE 'HIGH SCORE' " + low +
					// "\n");

					return highChild;
				}

			}
		}

		return highChild;

	}

	/*
	 * Bruker den siste beste bordet til å lage nye barn av selve
	 * RandomizeCarton er forklart i Helper
	 */
	public ArrayList<int[][]> GenerateRandomlyNeighbors(int[][] parent,
			int random) {

		ArrayList<int[][]> neighbours = new ArrayList<int[][]>();
		// Nædvendig slik at vi ikke endre parent
		int[][] currentBoard = new Helper().CopyMaskin(parent, n, m);
		for (int i = 0; i < random; i++) {

			// Endre på to av radene i parent
			currentBoard = new Helper().RandomizeCarton(currentBoard, n, m,
					eggs);

			// gjøres 3 ganger ...øker hastigheten og sansynligheten for å nå
			// riktig brett
			for (int c = 0; c < 3; c++) {

				// Oppdatere den horisontalt
				currentBoard = UpdateHorizontally(currentBoard);

				// Oppdaterer den vertikalt
				currentBoard = UpdateVertically(currentBoard);

			}
			neighbours.add(currentBoard);

		}
		return neighbours;

	}

	/*
	 * denne metoden starter med å sjekke om board er et perfekt resultat hvis
	 * den er så retunerer den 1 som vil si at bordet er perfekt hvis ikke så
	 * vet
	 */
	public double objectiveFunction(int[][] board) {

		/*
		 * Heter ut antall egg som står i feil position hvis null så har vi
		 * target og dens p = 1;
		 */
		double misplacedEgg = CheckAllRows(board);
		/*
		 * ikke nødvendig da det nedenfor vil resultere til det samme. men
		 * tilfelle vi har svaret så slipper vi konvertere osv. Bare å retunere
		 * 1 med en gang men 1.0 /(1+0) == 1 også..
		 */
		if (misplacedEgg == 0) {
			// Dvs perfekt bret
			return 1;
		}

		double oFp = 1.0 / (1 + misplacedEgg);
		return convertToStandardDecimal(oFp);
	}

	/*
	 * Denne metoden blir brukt i objectiveFunction(int[][] board), den får til
	 * bake double som forteller hvor mange egg er feil plassert, denn sjekker
	 * alle diagonalene og vertikalene og horisontalene og summere antall
	 * feilplasserte egg i hver av disse
	 */
	public double CheckAllRows(int[][] board) {

		return CheckLeftToRightDialog(board) + CheckLeftToLeftDialog(board)
				+ CheckRightToLeftDialog(board)
				+ CheckRightToRightDialog(board) + CheckHorizontals(board)
				+ CheckVerticals(board);
	}

	/*
	 * Denne metoden putter egg i vår brett, det velges en ramdom x,y som brukes
	 * som n m i en todimensjonal array
	 */
	private int[][] putEggsInCarton(int[][] board) {

		Random random = new Random();
		for (int i = 0; i < this.eggs;) {

			// Plukker random
			int x = random.nextInt(this.n);
			int y = random.nextInt(this.m);
			// Hvis plassen er tom
			if (board[x][y] == 0) {
				// Fyll plassen
				board[x][y] = 1;
				i++;
			}

		}
		return board;
	}

	/*
	 * Denne metoden sjekker om vi bryter constraints( mer enn k egg i
	 * vertikalen f.esk)
	 */
	private int EmptySpaceVertically(int[][] board, int y) {

		int countEggs = 0;
		for (int x = 0; x < n; x++) {
			// System.out.print(" (" + x + ", " + y + ") ");
			if (board[x][y] == 1) {
				countEggs++;
			}
		}
		return countEggs;
	}

	/*
	 * Denne metoden blir brukt til å legge til et egg i vertikalen den første
	 * vertikale raden som vi finner( dvs den raden som har plass til k egg som
	 * igjen betyr at raden har 0 eller 1 egg fra før og ikke k eller mer en k
	 * ). vi putter et egg opp i den og retunerer etter denne operasjonen
	 */
	private int[][] AddEggVertically(int[][] board, int y) {

		for (int x = 0; x < n; x++) {
			if (board[x][y] == 0) {
				board[x][y] = 1;
				/*
				 * System.out.println("Adding(Vertically) a egg to (" + x + ", "
				 * + y + ")");
				 */
				return board;

			}
		}
		return board;
	}

	/*
	 * Denne metoden blir kalt etter at vi har lagt et nytt egg til carton(ant
	 * egg er da k+1) så vi må fjerne det ekstra egget. isteden for å fjerne den
	 * random så fjerner vi et egg fra et vertikalt rad som faktik her mer enn k
	 * egg.
	 * 
	 * Hvis denne metoden blir kalt før AddEggVertically så må vi huske å kalle
	 * AddEggVertically slik at vi alltid har k og bare k egg i kurven
	 */
	private int[][] RemovEggVertically(int[][] board, int y) {

		for (int x = 0; x < n; x++) {
			if (board[x][y] == 1) {
				board[x][y] = 0;
				/*
				 * System.out.println("Removing(Vertically) a egg from (" + x +
				 * ", " + y + ")");
				 */
				return board;
			}
		}
		return board;
	}

	/*
	 * bruker alle metodene over som omhandler Vertikale rader og velger en rad
	 * som har mer enn k egg, tar ut det egget også legger den et nytt egg i en
	 * rad som mangler en egg og visa-vi dvs at vi sjekker om det er mer eller
	 * mindere enn k egg, kanskje dette øker hastigheten?? m = "y"
	 */
	public int[][] UpdateVertically(int[][] board) {
		F1: for (int y = 0; y < m; y++) {

			if (EmptySpaceVertically(board, y) > k) {

				board = RemovEggVertically(board, y);
				F2: for (int newY = 0; newY < m; newY++) {
					if (EmptySpaceVertically(board, newY) < k) {
						board = AddEggVertically(board, newY);
						break F2;
					}
				}

				break F1;
			} else if (EmptySpaceVertically(board, y) < k) {

				// Do the change, we are missing egg/s
				board = AddEggVertically(board, y);
				F3: for (int newY = 0; newY < m; newY++) {
					if (EmptySpaceVertically(board, newY) > k) {
						board = RemovEggVertically(board, newY);
						break F3;
					}
				}// F3
				break F1;
			}

		}
		return board;
	}

	/*
	 * metoden sjekker det motsatte av EmptySpaceVertically(int[][] board, int
	 * y) se forklaring der
	 */
	private int EmptySpaceHorizontally(int[][] board, int x) {

		int countEggs = 0;
		for (int y = 0; y < m; y++) {
			if (board[x][y] == 1) {
				countEggs++;
			}
		}
		return countEggs;
	}

	/*
	 * Metoden gjør det motsatte av AddEggVertically(int[][] board, int y) se
	 * forklaring der
	 */
	private int[][] AddEggHorizontally(int[][] board, int x) {

		F1: for (int y = 0; y < m; y++) {
			if (board[x][y] == 0) {
				board[x][y] = 1;
				break F1;
			}
		}

		return board;
	}

	/*
	 * Metoden gjør det motsatte av RemovEggVertically se forklaring der
	 */
	private int[][] RemoveEggHorizontally(int[][] board, int x) {
		F1: for (int y = 0; y < m; y++) {
			if (board[x][y] == 1) {
				board[x][y] = 0;
				/*
				 * System.out.println("Removing(Horizontally) a egg from (" + x
				 * + ", " + y + ")");
				 */
				break F1;
			}
		}
		return board;
	}

	public int[][] UpdateHorizontally(int[][] board) {

		F1: for (int x = 0; x < n; x++) {
			/*
			 * Dvs at vi har mer enn k egg i x.horisontal da fjerner vi den
			 */
			if (EmptySpaceHorizontally(board, x) > k) {
				board = RemoveEggHorizontally(board, x);
				F2: for (int newX = 0; newX < n; newX++) {
					if (EmptySpaceHorizontally(board, newX) < k) {
						board = AddEggHorizontally(board, newX);
						break F2;
					}
				}

				break F1;
			}
			/*
			 * Nå har vi mindre enn k egg så vi legger til en egg i første
			 * ledige plass
			 */
			else if (EmptySpaceHorizontally(board, x) < k) {

				board = AddEggHorizontally(board, x);
				F3: for (int newX = 0; newX < n; newX++) {
					if (EmptySpaceHorizontally(board, newX) > k) {
						board = RemoveEggHorizontally(board, newX);
						break F3;
					}
				}// F3
				break F1;
			}
		}

		return board;
	}

	/*
	 * Denne metoden sjekker om det er mer en k egg i horisontalen
	 */
	public int CheckHorizontals(int[][] board) {

		int penalty = 0;
		for (int x = 0; x < n; x++) {
			int counter = 0;
			// System.out.print("\n");
			for (int y = 0; y < m; y++) {
				// System.out.print("(" + x + ", " + y + ")");
				if (board[x][y] == 1) {
					counter++;
					if (counter > k) {
						penalty++;
					}
				}
			}
		}
		return penalty;
	}

	/*
	 * Denne metoden sjekker om det er mer en k enn i vertikalen
	 */
	public int CheckVerticals(int[][] board) {

		int penalty = 0;
		for (int y = 0; y < m; y++) {
			int counter = 0;
			// System.out.print("\n");
			for (int x = 0; x < n; x++) {
				// System.out.print("(" + x + ", " + y + ")");
				if (board[x][y] == 1) {
					counter++;
					if (counter > k) {
						penalty++;
					}
				}
			}
		}
		return penalty;
	}

	// ***************** FROM LEFT SIDE
	/*
	 * metoden begynner på venstre side av arrayen (0,0) og sjekker ned over
	 * diagonaler etter første runde går den til neste element som er på (0,1)
	 * og nedover der metoden som faktisk sjekker om plassen er tom eller ikke
	 * er den private metoden med samme navn som går gjennom hver rad denne
	 * metoden øker y, altså går fra 0 og bort over(høyre)
	 */
	public int CheckLeftToRightDialog(int[][] board) {
		// System.out.print("FromLeftToRight\n");
		int start = 0;
		int counter = 0;
		while (start != m) {
			counter += checkLeftToRightDialog(board, start);
			start++;
		}
		return counter;
	}

	/*
	 * blir matet av metoden ovenfor med y kordinater
	 */
	private int checkLeftToRightDialog(int[][] board, int g) {

		int counter = 0;
		int penalty = 0;
		// System.out.print("\n");
		for (int x = 0; x < board.length; x++) {
			int y = x + g;
			if (y < m) {
				// System.out.print("(" + x + ", " + y + ")");
				if (board[x][y] == 1) {
					counter++;
					if (counter > this.k) {
						penalty++;
					}
				}
			}
		}
		// System.out.println("\nEggs : " + counter + " extra eggs: " +
		// penalty);
		return penalty;
	}

	public int CheckLeftToLeftDialog(int[][] board) {
		// System.out.print("FromLeftToLeft\n");
		int start = 0;
		int counter = 0;
		while (start != n) {
			counter += checkLeftToLeftDialog(board, start);
			start++;
		}
		return counter;
	}

	private int checkLeftToLeftDialog(int[][] board, int g) {

		int counter = 0;
		int penalty = 0;
		// System.out.print("\n");
		for (int x = 0; x < n; x++) {
			int y = x - g;
			if (y < m && y != x && y > -1) {
				// System.out.print("(" + x + ", " + y + ")");

				if (board[x][y] == 1) {
					counter++;
					if (counter > this.k) {
						penalty++;
					}
				}
			}
		}
		// System.out.println("\nEggs : " + counter + " extra eggs: " +
		// penalty);
		return penalty;
	}

	// ****************** FROM RIGHT SIDE

	public int CheckRightToLeftDialog(int[][] board) {

		int start = m - 1;
		int counter = 0;
		while (start != -1) {
			counter += checkRightToLeftDialog(board, start);
			start--;
		}
		return counter;
	}

	private int checkRightToLeftDialog(int[][] board, int g) {

		int counter = 0;
		int penalty = 0;
		for (int x = 0; x < board.length; x++) {
			int y = g - x;
			if (y < m && y > -1) {

				if (board[x][y] == 1) {
					counter++;
					if (counter > this.k) {
						penalty++;
					}
				}
			}
		}
		// System.out.println("\nEggs : " + counter + " extra eggs: " +
		// penalty);
		return penalty;
	}

	public int CheckRightToRightDialog(int[][] board) {

		int start = 1;
		int counter = 0;
		while (start < n) {
			counter += checkRightToRightDialog(board, start);
			start++;
		}
		return counter;
	}

	public int checkRightToRightDialog(int[][] board, int g) {

		int tempx = g;
		int tempy = m - 1;
		int counter = 0;
		int penalty = 0;
		while (tempx < n && tempy > -1) {

			if (board[tempx][tempy] == 1) {

				counter++;
			}

			if (counter > this.k) {
				penalty++;
			}

			tempx++;
			tempy--;
		}
		return penalty;
	}

	public String toString(int[][] board) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < board.length; i++) {

			sb.append("\n");
			for (int j = 0; j < board[i].length; j++) {
				// sb.append(" (" + i + ", " + j + ") ");
				sb.append(" [ " + board[i][j] + " ] ");
			}
		}
		return sb.toString();
	}

	/*
	 * Denne metoden bruker alle metodene som går gjennom matrisen og sjekker
	 * horisontaler, vertikaler og alle diagonaler( fra venstre ned over og til
	 * høyre. fra høyre til venstre og fra høyre nedover )
	 */
	public boolean checkAll(int[][] board) {

		ArrayList<Boolean> all = new ArrayList<Boolean>();

		all.add(this.CheckHorizontals(board) == 0);
		all.add(this.CheckVerticals(board) == 0);

		all.add(this.CheckLeftToLeftDialog(board) == 0);
		all.add(this.CheckLeftToRightDialog(board) == 0);

		all.add(this.CheckRightToLeftDialog(board) == 0);
		all.add(this.CheckRightToRightDialog(board) == 0);
		int b = 0;
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i)) {
				b++;
			}
		}
		return b == 6;
	}

	/*
	 * Brukes til å tall med mange siffere. f.eks 0.998388383838 = 0.99
	 */
	private double convertToStandardDecimal(double value) {

		if (value < Double.MAX_VALUE && value > Double.MIN_VALUE) {
			try {
				DecimalFormat df = new DecimalFormat("#.##");
				String fromatString = df.format(value);
				double d = Double.parseDouble(fromatString);
				return d;
			} catch (NumberFormatException e) {
				System.out.print("convert " + e.toString());
			}
		}
		return value;
	}

}
