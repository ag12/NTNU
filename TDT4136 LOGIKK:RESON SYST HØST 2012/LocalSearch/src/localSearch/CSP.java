package localSearch;

import helper.Diagonal;
import helper.Helper;
import helper.Horizontal;
import helper.Vertical;

import java.util.ArrayList;
import java.util.Random;

import entity.Empty;
import entity.Queen;

public class CSP {

	public int K;
	public Object[][] Chessboard;
	public int ATTEMPT;
	public static int SWAPS;

	public CSP(int k) {
		K = k;
		Chessboard = new Object[K][K];
		Chessboard = OnCreate(Chessboard, K);
		Chessboard = OnAttack(Chessboard);

	}

	/***
	 * 
	 * @param board
	 *            er en random generert bord med k-queens
	 * 
	 *            Her kan vi enten kjøre X antall ganger og håpe på å finne
	 *            riktig svar eller kjøre så lenge man ikke har funnet det
	 *            "perfekte" bordet.
	 * 
	 *            Har både sine fordeler og ulemper, da noen ganger kan
	 *            algoritmen finne svar selv om antall forsøk er veldig lavt
	 *            eller at den finne "nesten perfekt svar" i løpet av bare noen
	 *            forsøk på å finne den( veldig lav operativ tid, som er en
	 *            fordel)
	 * 
	 *            Men hvis man ønsker et perfekt svar så tar det lengere tid.
	 *            Det er blandt annet ikke sikkert at det gjøres et swap for
	 *            hver forsøk og det kan man se på resultat utskriften f.eks for
	 *            et bord 120 X 120 med 120 -Queen There was available 1458
	 *            attempts to swap queens. The real count for swaps is: 410
	 *            swaps in this board Det vil si at det ble forsøkt å swape
	 *            elementer 1458 ganger men kun 410 av dem var mulig
	 * 
	 * 
	 *            2.Repeat for either a maximum number of steps or until a
	 *            violation-free solution is found:
	 * 
	 * 
	 */
	public void RunLocalSearchCSP(Object[][] board) {

		Object[][] Copy = Helper.CopyArray(board);
		Helper.show("START BOARD");
		Helper.show(toString(board));
		F1: while (ISCOMPELETE(Copy) != 0) {
			ATTEMPT++;
			if (ISCOMPELETE(Copy) == 0) {
				break F1;
			}
			Copy = MIN_CONFLICTS(Copy);
		}

		Helper.show("\nEND BOARD");
		Helper.show(toString(Copy));
		Helper.show("\nCOUNT CSP's " + ISCOMPELETE(Copy));
		Helper.show("\nThere was " + ATTEMPT
				+ " attempts to swap queens. The real count for swaps is: "
				+ SWAPS );

	}

	/***
	 * 
	 * @param board
	 * @return 
	 * 
	 * 
	 *         2.a Randomly choose a queen.
	 *         Her velges det en random queen, men det gjøres et sjekk først om dens attack variabel er større en 0 eller ikke 
	 *         Kanskje unødvendig å prøve å swpe den, da den allerede er på minste plass? 
	 *         Det kan kun være en 0 per rad/clonne
	 */
	public Object[][] MIN_CONFLICTS(Object[][] board) {

		Random Random = new Random();
		Queen Queen = null;
		while (Queen == null) {
			int RandomX = Random.nextInt(K);
			int RandomY = Random.nextInt(K);
			if (board[RandomX][RandomY] instanceof Queen) {
				
				Queen = (Queen) board[RandomX][RandomY];
			}
		}

		if (Queen.Attacks > 0) {
			board = CONFLICTS(board, Queen);
		}

		return board;

	}

	/***
	 * 
	 * @param board
	 * @param queen
	 *            den random valgte dronningen som har en attack på n;
	 * @return Enten det samme bordet( Hvis vi ikke finner positioner som
	 *         inneholder, mindre angrep enn nå værende position)
	 * 
	 *         Bytter på to positioner mellom en tom og en dronnings object.
	 *         hvis og bare hvis det tomme objectet har attacks som er mindre en
	 *         den utvalgte dronningen. Hvis det finnes andre positioner som
	 *         inneholder nøyaktig lik antall attakcs så velges en av dem og
	 *         gjøres et swap på de positionene
	 */
	public Object[][] CONFLICTS(Object[][] board, Queen queen) {

		Object[][] Copy = Helper.CopyArray(board);
		/*
		 * For å holde oversikt over de som er mindre og de som er like
		 */
		ArrayList<Empty> CurrentRowMin = new ArrayList<Empty>(), CurrentRowEqual = new ArrayList<Empty>();

		int TempX = queen.Row;
		for (int y = 0; y < Copy.length; y++) {
			if (Copy[TempX][y] instanceof Empty) {
				Empty Empty = (Empty) Copy[TempX][y];

				if (Empty.Attacks < queen.Attacks) {
					CurrentRowMin.add(Empty);
				}
				if (Empty.Attacks == queen.Attacks) {
					CurrentRowEqual.add(Empty);
				}
			}
		}
		if (CurrentRowMin.size() > 0) {
			/*
			 * Dette finnes n positioner som har n mindre attack en nå , så
			 * derfor bytter vi plasse
			 */

			Empty minimumValue = null;
			ArrayList<Empty> localMinimumValues = new ArrayList<Empty>();
			;
			int Attacks = Integer.MAX_VALUE;
			/*
			 * Loope gjennom alle de minste 
			 */
			for (Empty mins : CurrentRowMin) {

				if (mins.Attacks < Attacks) {
					Attacks = mins.Attacks;
					minimumValue = mins;

				} else if (minimumValue != null
						&& mins.Attacks == minimumValue.Attacks) {

					localMinimumValues.add(mins);
					/*
					 *hvis det er flere som er samtidig minst og like 
					 */
					if (!mins.equals(minimumValue)) {
						localMinimumValues.add(minimumValue);
					}
				}

			}
			/*
			 * Sjekker først om det finnes minst to som er minimum like
			 */
			if (localMinimumValues.size() > 1) {
				/**
				 * Her har vi minst to elemnter med lik minimumverdi
				 * velger en random.
				 */
				int RandomIndex = new Random().nextInt(localMinimumValues
						.size());
				Empty Empty = localMinimumValues.get(RandomIndex);
				Copy = SWAPELEMENTS(Copy, queen, Empty);
				Copy = OnAttack(Copy);
				return Copy;
			}
			/**
			 * har vi kommet hit? vel da har vi den miste position i minimumValue
			 */
			Copy = SWAPELEMENTS(Copy, queen, minimumValue);
			Copy = OnAttack(Copy);

			return Copy;

		}
		if (CurrentRowEqual.size() > 0) {
			/*
			 * Dete finnes rader som har lik attak, og vi har ikke klart å finne
			 * positioner som har mindre attack
			 * 
			 * "1. Assume that column(qi) is the current column assignment of
			 * the ith queen, and it is involved in M 9 queen attacks. Also
			 * assume that all other column placements for this queen will
			 * produce M or more queen attacks, with Z of these producing
			 * exactly M attacks, where Z ≥ 1. Then, you should move the queen
			 * to one of these Z columns even though the change does not
			 * immediately improve the state evaluation."
			 */
			int RandomIndex = new Random().nextInt(CurrentRowEqual.size());
			Empty Empty = CurrentRowEqual.get(RandomIndex);
			Copy = SWAPELEMENTS(Copy, queen, Empty);
			Copy = OnAttack(Copy);
			return Copy;

		}
		/**
		 * Har vi kommet hit så betyr det at det ikke var mulig å flytte på den utvalgte dronningen 
		 */
		return Copy;
	}

	/***
	 * 
	 * @param board
	 *            currentboard som vi jobber på
	 * @param queen
	 *            dronningen som skal flytte seg til et tomt plass
	 * @param empty
	 *            den tomme plassen som skal fylles av en dronning
	 * @return etter at objectene har byttet plass retunerer vi bordet som den
	 *         var ellers
	 */
	private Object[][] SWAPELEMENTS(Object[][] board, Queen queen, Empty empty) {

		SWAPS++;
		/*
		 * Temp variable som skal holde attributtene til en dronning
		 */
		int TempRow = queen.Row;
		int TempColumn = queen.Column;
		int TempAttacks = queen.Attacks;

		// Veksler
		queen.Row = empty.Row;
		queen.Column = empty.Column;
		queen.Attacks = empty.Attacks;

		// Bruker tempverdier
		empty.Row = TempRow;
		empty.Column = TempColumn;
		empty.Attacks = TempAttacks;

		// Plass endring
		board[queen.Row][queen.Column] = queen;
		board[empty.Row][empty.Column] = empty;

		return board;
	}

	/**
	 * 
	 * @param board
	 *            is a empty board
	 * @param count
	 *            queens to place inside this board
	 * @return a randomly generated Chessboard whit N Queens
	 * 
	 *         1. Initialize the board by randomly choosing columns for each of
	 *         the K queens.
	 */
	public Object[][] OnCreate(Object[][] board, int n) {

		Object[][] Copy = Helper.CopyArray(board);
		for (int x = 0; x < Copy.length; x++) {
			for (int y = 0; y < Copy[x].length; y++) {
				Copy[x][y] = new Empty(x, y, 0);
			}
		}
		Random Random = new Random();
		int Queens = K;
		for (int i = 0; i < Queens;) {
			int RandomY = Random.nextInt(K);
			if (Copy[i][RandomY] instanceof Empty) {

				Copy[i][RandomY] = new Queen(i, RandomY);
				i++;
			}
		}
		return Copy;
	}

	/***
	 * 
	 * @param board
	 *            is a randomly generated Chessboard whit N Queens and the rest
	 *            is Empty
	 * @return the same board, but counts the count of all attacks for each
	 *         [row, column]
	 */
	public Object[][] OnAttack(Object[][] board) {

		Object[][] Copy = Helper.CopyArray(board);
		for (int x = 0; x < Copy.length; x++) {
			for (int y = 0; y < Copy[x].length; y++) {
				Copy[x][y] = GetAttacks(Copy, Copy[x][y]);
			}
		}
		return Copy;

	}

	/***
	 * 
	 * @param board
	 *            er en currentbord som vi jobber med, har K Queens
	 * @param object
	 *            er den "utvalgte" positionen som vi skal sjekke og telle alle
	 *            mulige angrep for( Kan være enten Queen eller bare et tompt
	 *            object)
	 * @return etter at vi har telt alle mulige angrep denne positionen kan ha,
	 *         summerer vi dem og retunere den
	 */
	public Object GetAttacks(Object[][] board, Object object) {

		Diagonal Diagonal = new Diagonal();
		Horizontal Horizontal = new Horizontal();
		Vertical Vertical = new Vertical();

		if (object instanceof Empty) {

			Empty Empty = (Empty) object;

			Empty.Attacks = Diagonal.CheckLeftSideDiagonal(board, Empty.Row,
					Empty.Column);
			Empty.Attacks += Diagonal.CheckRightSideDiagonal(board, Empty.Row,
					Empty.Column);
			Empty.Attacks += Horizontal.CheckHorizontal(board, Empty.Row,
					Empty.Column);
			Empty.Attacks += Vertical.CheckVertical(board, Empty.Row,
					Empty.Column);
			object = Empty;
		} else {

			Queen Queen = (Queen) object;

			Queen.Attacks = Diagonal.CheckLeftSideDiagonal(board, Queen.Row,
					Queen.Column);
			Queen.Attacks += Diagonal.CheckRightSideDiagonal(board, Queen.Row,
					Queen.Column);
			Queen.Attacks += Horizontal.CheckHorizontal(board, Queen.Row,
					Queen.Column);
			Queen.Attacks += Vertical.CheckVertical(board, Queen.Row,
					Queen.Column);
			object = Queen;
		}

		return object;
	}

	/**
	 * 
	 * @param board
	 *            en random bord som har dronninger plassert på alle radene sine
	 * @return sant om ingen av dem angriper hverandre
	 */
	public int ISCOMPELETE(Object[][] board) {
		return Helper.GetCSP(board);
	}

	public String toString(Object[][] board) {
		return Helper.toString(board, 2);
	}

}
