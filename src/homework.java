import java.io.*;
import java.util.*;

public class homework {

	public static String type;
	public static int length = 8;
	public static String colorPlay;
	public static String colorTurn;
	public static double time;
	public static chess[][] inBoard = new chess[length][length];
	public static char[] letter = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
	public static boolean wWin = false, bWin = false, draw = false;
	public static boolean wWin1 = false, bWin1 = false;
	public static int count = 0;
	public static boolean changeC = false;
	public static int[][] counter = new int[length][length];
	public static double alpha = Double. NEGATIVE_INFINITY;
	public static double beta = Double. POSITIVE_INFINITY;

	static class act {
		int sr, sc, er, ec;
		ArrayList<point> path;
		ArrayList<point> skip;

		public act(int sr, int sc, int er, int ec, ArrayList<point> skip, ArrayList<point> path) {
			this.sr = sr;
			this.sc = sc;
			this.er = er;
			this.ec = ec;
			this.skip = skip;
			this.path = path;
		}

		public act(int sr, int sc, int er, int ec) {
			this.sr = sr;
			this.sc = sc;
			this.er = er;
			this.ec = ec;
			this.skip = new ArrayList<>();
			this.path = new ArrayList<>();
			point sNode = new point(sr, sc);
			point eNode = new point(er, ec);
			this.path.add(eNode);
			this.path.add(sNode);
		}
	}

	static class tuple {
		double d;
		mstuple ms;

		public tuple(double d, mstuple ms) {
			this.d = d;
			this.ms = ms;
		}
	}

	static class mstuple {
		chess[][] s;
		act m;

		public mstuple(act m, chess[][] s) {
			this.m = m;
			this.s = s;
		}
	}

	static class chess {
		int row;
		char col;
		int rRow;
		int rCol;
		String color;
		boolean king;

		public chess(int rRow, int rCol, int row, char col, String color, boolean king) {
			this.rRow = rRow;
			this.rCol = rCol;
			this.row = row;
			this.col = col;
			this.color = color;
			this.king = king;
		}
	}

	static class point {
		int row;
		int col;

		public point(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + row;
			result = prime * result + col;
			return result;
		}

		public boolean equals(point obj) {
			if (this.row == obj.row && this.col == obj.col) {
				return true;
			}
			return false;
		}
	}

	public static void main(String[] args) throws IOException {
		// long stime = System.nanoTime();
		int count1=  0;
		int wc = 0, bc = 0;
		while (count1 != 50) {
			wWin = false;
			bWin = false;
		readMap("input1.txt");
		FileWriter ouputWriter = new FileWriter("output.txt");
		 
			if (!checkWin(inBoard)) {
				move(inBoard, ouputWriter, 1);
			}
		
			
if (wWin) {
	System.out.println("WHITE Win");
	wc++;
}
if (bWin) {
	System.out.println("BLACK Win");
	bc++;
}
		
		
		
	if (type.equals("GAME")) {
		
		while (!checkWin(inBoard) && !wWin && ! bWin) { 
			readMap("playdata.txt");
				move(inBoard, ouputWriter, 3);

if (wWin) {
	System.out.println("WHITE Win");
	wc++;
}
if (bWin) {
	System.out.println("BLACK Win");
	bc++;
}				
		
		}
		ouputWriter.close();
	}count1++;
		
}
		System.out.println("w" + wc + "b" + bc);}
		// long etime = System.nanoTime();
		// System.out.println((etime - stime) / 1000000 + "ms");
	

	public static chess[][] move(chess[][] state, FileWriter ouputWriter, int dep) throws IOException {
		tuple t = null;
		if (colorPlay.equals("w")) {
			t = minimax(new mstuple(null, state), 3, false, alpha, beta);
		} else {
			t = minimax1(new mstuple(null, state), 2, true, alpha, beta);
		}
		if (t == null || t.ms == null) {
			if (colorPlay.equals("w")) {
				bWin = true;
			} else {
				wWin = true;
			}
			return state;
		}
		act m = t.ms.m;
		checkWin(t.ms.s);
//		printBoardS(t.ms.s);
//		for (point p : m.path) {
//			System.out.println(p.row + " " + p.col);
//		}
		ArrayList<point> path = m.path;
		boolean jump = false;
		if (m.skip != null && m.skip.size() != 0) {
			jump = true;
		}
		if (jump) {
			for (int i = path.size() - 1; i > 0; i--) {
//System.out.println("J" + " " + letter[path.get(i).col] + (8 - path.get(i).row) + " "
//+ letter[path.get(i - 1).col] + (8 - path.get(i - 1).row));
				ouputWriter.write("J" + " " + letter[path.get(i).col] + (8 - path.get(i).row) + " "
						+ letter[path.get(i - 1).col] + (8 - path.get(i - 1).row));
				ouputWriter.write(System.getProperty("line.separator"));
			}
		} else {
//System.out.println("E" + " " + letter[path.get(path.size() - 1).col] + (8 - path.get(path.size() - 1).row)
//+ " " + letter[path.get(0).col] + (8 - path.get(0).row));
			ouputWriter.write("E" + " " + letter[path.get(path.size() - 1).col] + (8 - path.get(path.size() - 1).row)
					+ " " + letter[path.get(0).col] + (8 - path.get(0).row));
			ouputWriter.write(System.getProperty("line.separator"));
		}

String str = "playdata.txt";
FileWriter writer = new FileWriter(str);
writer.write(type);
writer.write(System.getProperty("line.separator"));
if (colorTurn.equals("w")) {
	writer.write("BLACK");
} else {
	writer.write("WHITE");
}
writer.write(System.getProperty("line.separator"));
writer.write(Double.toString(time));
writer.write(System.getProperty("line.separator"));
for (int i = 0; i < length; i++) {
	for (int j = 0; j < length; j++) {
		if (t.ms.s[i][j].king) {
			writer.write(t.ms.s[i][j].color.toUpperCase());
		} else {
			writer.write(t.ms.s[i][j].color);
		}
	}
	writer.write(System.getProperty("line.separator"));
}
writer.close();

		return state;
	}

	private static void printBoardS(homework.chess[][] c) {
		System.out.println(type);
		System.out.println(colorTurn);
		System.out.println(time);
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (c[i][j].king) {
					System.out.print(c[i][j].color.toUpperCase());
				} else {
					System.out.print(c[i][j].color);
				}
			}
			System.out.println();
		}

	}

	private static chess[][] readMap(String name) {
		Scanner scanner = null;
		try {
			String str = name;
			scanner = new Scanner(new File(str));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		type = scanner.nextLine();
		String temp = scanner.nextLine();
		if (temp.equals("WHITE")) {
			colorPlay = "w";
			colorTurn = "w";
		} else {
			colorPlay = "b";
			colorTurn = "b";
		}
		time = Double.parseDouble(scanner.nextLine());
		for (int i = 0; i < length; i++) {
			String s = scanner.nextLine();
			for (int j = 0; j < length; j++) {
				char ch = s.charAt(j);
				if (ch == '.') {
					chess c = new chess(i, j, 8 - i, letter[j], ".", false);
					inBoard[i][j] = c;
				} else if (ch == 'b') {
					chess c = new chess(i, j, 8 - i, letter[j], "b", false);
					inBoard[i][j] = c;
				} else if (ch == 'w') {
					chess c = new chess(i, j, 8 - i, letter[j], "w", false);
					inBoard[i][j] = c;
				} else if (ch == 'B') {
					chess c = new chess(i, j, 8 - i, letter[j], "b", true);
					inBoard[i][j] = c;
				} else if (ch == 'W') {
					chess c = new chess(i, j, 8 - i, letter[j], "w", true);
					inBoard[i][j] = c;
				}
			}
		}
		return inBoard;
	}

	private static boolean checkWin(chess[][] c) {
		int b = 0, w = 0;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (c[i][j].color.equals("b") || c[i][j].color.equals("B")) {
					b++;
				} else if (c[i][j].color.equals("w") || c[i][j].color.equals("W")) {
					w++;
				}
			}
		}
		if (w == 0) {
			bWin = true;
			return true;
		}
		if (b == 0) {
			wWin = true;
			return true;
		}
		return false;
	}

//--------------------------------------------------------------------------------minimax---------------------------------------------------------------------------
	private static ArrayList<act> getValidMove(chess[][] board, String color) {
		ArrayList<act> move = new ArrayList<>();
		ArrayList<point> sq = new ArrayList<>();

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (board[i][j].color.equals(color)) {
					chess cur = board[i][j];
					sq.add(new point(cur.rRow, cur.rCol));
					if (legalJump(board, cur, i, j, i - 2, j + 2, i - 1, j + 1) == true) {
						ArrayList<act> jumps = getJumps(board, cur, i, j, i - 2, j + 2, i - 1, j + 1);
						move.addAll(jumps);
					}
					if (legalJump(board, cur, i, j, i + 2, j + 2, i + 1, j + 1) == true) {
						ArrayList<act> jumps = getJumps(board, cur, i, j, i + 2, j + 2, i + 1, j + 1);
						move.addAll(jumps);
					}
					if (legalJump(board, cur, i, j, i + 2, j - 2, i + 1, j - 1) == true) {
						ArrayList<act> jumps = getJumps(board, cur, i, j, i + 2, j - 2, i + 1, j - 1);
						move.addAll(jumps);
					}
					if (legalJump(board, cur, i, j, i - 2, j - 2, i - 1, j - 1) == true) {
						ArrayList<act> jumps = getJumps(board, cur, i, j, i - 2, j - 2, i - 1, j - 1);
						move.addAll(jumps);
					}
				}
			}
		}

		if (move.isEmpty()) {
			for (point p : sq) {
				int i = p.row;
				int j = p.col;
				chess c = board[i][j];
				if (legalMove(board, c, i, j, i - 1, j + 1) == true) {
					act m = new act(i, j, i - 1, j + 1);
					move.add(m);
				}
				if (legalMove(board, c, i, j, i + 1, j + 1) == true) {
					act m = new act(i, j, i + 1, j + 1);
					move.add(m);
				}
				if (legalMove(board, c, i, j, i + 1, j - 1) == true) {
					act m = new act(i, j, i + 1, j - 1);
					move.add(m);
				}
				if (legalMove(board, c, i, j, i - 1, j - 1) == true) {
					act m = new act(i, j, i - 1, j - 1);
					move.add(m);
				}
			}
		}

		return move;
	}

	private static boolean legalMove(homework.chess[][] board, homework.chess cur, int sr, int sc, int er, int ec) {
		if (!(er < length && ec < length && er >= 0 && ec >= 0) || board[er][ec].color != ".") {
			return false;
		}
		if (cur.color.equals("b")) {
			if (board[sr][sc].color.equals("b") && !cur.king && sr > er) {
				return false;
			}
			return true;
		}
		if (cur.color.equals("w")) {
			if (board[sr][sc].color.equals("w") && !cur.king && sr < er) {
				return false;
			}
			return true;
		}
		return false;
	}

	private static ArrayList<homework.act> getJumps(homework.chess[][] board, homework.chess cur, int sr, int sc,
			int cr, int cc, int jr, int jc) {
		HashSet<String> visit = new HashSet<>();
		ArrayList<act> validMove = new ArrayList<>();
		HashMap<String, String> from = new HashMap<>();
		Stack<act> stack = new Stack<>();
		String sPoint = Integer.toString(sr) + Integer.toString(sc);
		String ePoint = Integer.toString(cr) + Integer.toString(cc);
		from.put(ePoint, sPoint);
		stack.add(new act(sr, sc, cr, cc));
		visit.add(sPoint);

		while (!stack.empty()) {
			act curMove = stack.pop();
			String srsc = Integer.toString(curMove.sr) + Integer.toString(curMove.sc);
			String erec = Integer.toString(curMove.er) + Integer.toString(curMove.ec);
			visit.add(srsc);
			visit.add(erec);
			ArrayList<act> around = getAroundJump(cur, board, curMove.er, curMove.ec);
			String start = Integer.toString(curMove.er) + Integer.toString(curMove.ec);

			if (leafNode(visit, around) || around.isEmpty()) {
				ArrayList<homework.point> path = new ArrayList<>();
				String key = start;
				while (from.containsKey(key)) {
					path.add(new point(Character.getNumericValue(key.charAt(0)),
							Character.getNumericValue(key.charAt(1))));
					key = from.get(key);
				}
				path.add(new point(Character.getNumericValue(sPoint.charAt(0)),
						Character.getNumericValue(sPoint.charAt(1))));

				ArrayList<homework.point> skip = new ArrayList<>();
				String key1 = start;
				while (from.containsKey(key1)) {
					int r = Character.getNumericValue(key1.charAt(0));
					int c = Character.getNumericValue(key1.charAt(1));
					key1 = from.get(key1);
					int r1 = Character.getNumericValue(key1.charAt(0));
					int c1 = Character.getNumericValue(key1.charAt(1));
					skip.add(new point((int) (r + r1) / 2, (int) (c + c1) / 2));
				}
				act newMove = new act(sr, sc, curMove.er, curMove.ec, skip, path);
				validMove.add(newMove);
				visit.remove(start);
			}
			for (act move : around) {
				String end = Integer.toString(move.er) + Integer.toString(move.ec);
				if (!visit.contains(end)) {
					from.put(end, start);
					stack.push(move);
				}
			}
		}
		if (validMove.isEmpty()) {
			act m = new act(sr, sc, cr, cc);
			validMove.add(m);
		}
		return validMove;
	}

	private static boolean leafNode(HashSet<String> visit, ArrayList<homework.act> around) {
		for (act move : around) {
			String p = Integer.toString(move.er) + Integer.toString(move.ec);
			if (!visit.contains(p)) {
				return false;
			}
		}
		return true;
	}

	private static ArrayList<homework.act> getAroundJump(homework.chess c, homework.chess[][] board, int i, int j) {
		chess temp = board[i][j];
		board[i][j] = c;
		ArrayList<act> validMoves = new ArrayList<>();
		if (legalJump(board, c, i, j, i - 2, j + 2, i - 1, j + 1) == true) {
			act m = new act(i, j, i - 2, j + 2);
			validMoves.add(m);
		}
		if (legalJump(board, c, i, j, i - 2, j - 2, i - 1, j - 1) == true) {
			act m = new act(i, j, i - 2, j - 2);
			validMoves.add(m);
		}
		if (legalJump(board, c, i, j, i + 2, j - 2, i + 1, j - 1) == true) {
			act m = new act(i, j, i + 2, j - 2);
			validMoves.add(m);
		}
		if (legalJump(board, c, i, j, i + 2, j + 2, i + 1, j + 1) == true) {
			act m = new act(i, j, i + 2, j + 2);
			validMoves.add(m);
		}
		board[i][j] = temp;
		return validMoves;
	}

	private static boolean legalJump(homework.chess[][] board, homework.chess cur, int sr, int sc, int er, int ec,
			int jr, int jc) {
		if (!(er < length && ec < length && er >= 0 && ec >= 0)) {
			return false;
		}
		if (board[er][ec].color != "." || (board[jr][jc].color == ".")) {
			return false;
		}
		if (cur.color.equals("b")) {
			if (board[sr][sc].color.equals("b") && !cur.king && sr > er) {
				return false;
			}
			if (board[jr][jc].color.equals("b")) {
				return false;
			}
			return true;

		}

		if (cur.color.equals("w")) {
			if (board[sr][sc].color.equals("w") && !cur.king && sr < er) {
				return false;
			}
			if (board[jr][jc].color.equals("w")) {
				return false;
			}
			return true;

		}

		return false;
	}

	private static chess[][] copBoard(homework.chess[][] nextBoard, homework.chess[][] board) {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				nextBoard[i][j] = new chess(board[i][j].rRow, board[i][j].rCol, board[i][j].row, board[i][j].col,
						board[i][j].color, board[i][j].king);
			}
		}
		return nextBoard;
	}

	private static tuple minimax(mstuple ms, int depth, boolean max, double alpha, double beta) {
		if (depth == 0) {
			tuple t = new tuple(score(ms.s), ms);
			return t;
		}
		if (max) {
			double maxScore = Double. NEGATIVE_INFINITY;
			ArrayList<mstuple> mst = new ArrayList<>();
			ArrayList<act> actions = getValidMove(ms.s, "b");
			for (act m : actions) {
				chess[][] nextState = new chess[length][length];
				nextState = copBoard(nextState, ms.s);
				doMove(nextState, m);
				mstuple ms1 = new mstuple(m, nextState);
				double score = minimax(ms1, depth - 1, false, alpha, beta).d;
				maxScore = Math.max(score, maxScore);
				alpha = Math.max(alpha, maxScore);
				if (beta < alpha) {
					break;
				}
				if (score == maxScore) {
					mst.add(ms1);
				}
			}
			tuple t = new tuple(maxScore, null);
			if (mst != null && mst.size() != 0) {
				t.ms = getRandomElement(mst);
			} 
			return t;

		} else {
			double minScore = Double. POSITIVE_INFINITY;
			ArrayList<act> actions = getValidMove(ms.s, "w");
			ArrayList<mstuple> mst = new ArrayList<>();
			for (act m : actions) {
				chess[][] nextState = new chess[length][length];
				nextState = copBoard(nextState, ms.s);
				doMove(nextState, m);
				mstuple ms1 = new mstuple(m, nextState);
				double score = minimax(ms1, depth - 1, true, alpha, beta).d;
				minScore = Math.min(score, minScore);
				beta = Math.min(beta, minScore);
				if (beta < alpha) {
					break;
				}
				if (score == minScore) {
					mst.add(ms1);
				}
			}
			tuple t = new tuple(minScore, null);
			if (mst != null && mst.size() != 0) {
				t.ms = getRandomElement(mst);
			} 
			return t;

		}
	}
	
	private static tuple minimax1(mstuple ms, int depth, boolean max, double alpha, double beta) {
		if (depth == 0) {
			tuple t = new tuple(score1(ms.s), ms);
			return t;
		}
		if (max) {
			double maxScore = -999999999;
			ArrayList<mstuple> mst = new ArrayList<>();
			ArrayList<act> actions = getValidMove(ms.s, "b");
			for (act m : actions) {
				chess[][] nextState = new chess[length][length];
				nextState = copBoard(nextState, ms.s);
				doMove(nextState, m);
				mstuple ms1 = new mstuple(m, nextState);
				double score = minimax(ms1, depth - 1, false, alpha, beta).d;
				maxScore = Math.max(score, maxScore);
				alpha = Math.max(alpha, score);
				if (beta < alpha) {
					break;
				}
				if (score == maxScore) {
					mst.add(ms1);
				}
			}
			tuple t = new tuple(maxScore, null);
			if (mst != null && mst.size() != 0) {
				t.ms = getRandomElement(mst);
			} 
			return t;

		} else {
			double minScore = 999999999;
			ArrayList<act> actions = getValidMove(ms.s, "w");
			ArrayList<mstuple> mst = new ArrayList<>();
			for (act m : actions) {
				chess[][] nextState = new chess[length][length];
				nextState = copBoard(nextState, ms.s);
				doMove(nextState, m);
				mstuple ms1 = new mstuple(m, nextState);
				double score = minimax(ms1, depth - 1, true, alpha, beta).d;
				minScore = Math.min(score, minScore);
				beta = Math.min(beta, score);
				if (beta < alpha) {
					break;
				}
				if (score == minScore) {
					mst.add(ms1);
				}
			}
			tuple t = new tuple(minScore, null);
			if (mst != null && mst.size() != 0) {
				t.ms = getRandomElement(mst);
			} 
			return t;

		}
	}

	public static homework.mstuple getRandomElement(List<mstuple> list) {
		Random rand = new Random();
		return list.get(rand.nextInt(list.size()));
	}

	private static double score(chess[][] c) {
		int we = 0, be = 0, wp = 0, bp = 0, wUnsafe = 0, bUnsafe = 0, wKingUnsafe = 0, bKingUnsafe = 0, wCount = 0, bCount = 0, wRow = 0, bRow = 0, wKing = 0, bKing = 0, wKingSafe = 0, bKingSafe = 0, wSafe = 0, bSafe = 0;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (c[i][j].color.equals("w")) {
					if (j == 0 || j == length - 1) {
						we++;
					}
					if (c[i][j].king) {
						wKing++;
					} else {
						if (i == length - 1) {
							wp++;
						}
						wCount++;
					}
					wRow += length - i;
					if (i - 1 >= 0 && j - 1 >= 0) {
						if (c[i - 1][j - 1].color.equals("b") || (c[i - 1][j - 1].color.equals("b") && c[i - 1][j - 1].king)) {
							if (i + 1 < length && j + 1 < length) {
								if (c[i + 1][j + 1].color.equals(".")) {
									if (c[i][j].king) {
										wKingUnsafe+=1;
									} else {
										wUnsafe+=1;
									}
									
								}
							}
						}
					} else if (i - 1 >= 0 && j + 1 < length) {
						if (c[i - 1][j + 1].color.equals("b") || (c[i - 1][j + 1].color.equals("b") && c[i - 1][j + 1].king)) {
							if (i + 1 < length && j - 1 >= 0) {
								if (c[i + 1][j - 1].color.equals(".")) {
									if (c[i][j].king) {
										wKingUnsafe+=1;
									} else {
										wUnsafe+=1;
									}
								}
							}
						}
					} else if (i + 1 < length && j - 1 >= 0) {
						if ((c[i + 1][j - 1].color.equals("b") && c[i + 1][j - 1].king)) {
							if (i - 1 >= 0 && j + 1 < length) {
								if (c[i - 1][j + 1].color.equals(".")) {
									if (c[i][j].king) {
										wKingUnsafe+=1;
									} else {
										wUnsafe+=1;
									}
								}
							}
						}
					} else if (i + 1 < length && j + 1 < length) {
						if (c[i + 1][j + 1].color.equals("b") && c[i + 1][j + 1].king) {
							if (i - 1 >= 0 && j - 1 >= 0) {
								if (c[i- 1][j - 1].color.equals(".")) {
									if (c[i][j].king) {
										wKingUnsafe+=1;
									} else {
										wUnsafe+=1;
									}
								}
							}
						}
					} else {
						if (c[i][j].king) {
							wKingSafe+=length - i;
						} else {
							wSafe+=length - i;
						}
					}
				}
				
				if (c[i][j].color.equals("b")) {
					if (j == 0 || j == length - 1) {
						be++;
					}
					if (c[i][j].king) {
						bKing++;
					} else {
						if (i == 0) {
							bp++;
						}
						bCount++;
					}
					bRow += i;
					if (i - 1 >= 0 && j - 1 >= 0) {
						if ((c[i - 1][j - 1].color.equals("w") && c[i - 1][j - 1].king)) {
							if (i + 1 < length && j + 1 < length) {
								if (c[i + 1][j + 1].color.equals(".")) {
									if (c[i][j].king) {
										bKingUnsafe+=1;
									} else {
										bUnsafe+=1;
									}
								}
							}
						}
					} else if (i - 1 >= 0 && j + 1 < length) {
						if ((c[i - 1][j + 1].color.equals("w") && c[i - 1][j + 1].king)) {
							if (i + 1 < length && j - 1 >= 0) {
								if (c[i + 1][j - 1].color.equals(".")) {
									if (c[i][j].king) {
										bKingUnsafe+=1;
									} else {
										bUnsafe+=1;
									}
								}
							}
						}
					}else if (i + 1 < length && j - 1 >= 0) {
						if ((c[i + 1][j - 1].color.equals("b")) || ((c[i + 1][j - 1].color.equals("b") && c[i + 1][j - 1].king))) {
							if (i - 1 >= 0 && j + 1 < length) {
								if (c[i - 1][j + 1].color.equals(".")) {
									if (c[i][j].king) {
										bKingUnsafe+=1;
									} else {
										bUnsafe+=1;
									}
								}
							}
						}
					} else if (i + 1 < length && j + 1 < length) {
						if ((c[i + 1][j + 1].color.equals("b")) || (c[i + 1][j + 1].color.equals("b") && c[i + 1][j + 1].king)) {
							if (i - 1 >= 0 && j - 1 >= 0) {
								if (c[i- 1][j - 1].color.equals(".")) {
									if (c[i][j].king) {
										bKingUnsafe+=1;
									} else {
										bUnsafe+=1;
									}
								}
							}
						}
					} else {
						if (c[i][j].king) {
							bKingSafe+=i;
						} else {
							bSafe+=i;
						}
					}
				}
				
			}
		}
		
		
		double res = 300 * bCount + 15 * bRow - 300 * wCount - 15 * wRow  + be * 5  - we * 5 + bKing * 800 - wKing * 800 + bp * 5 - wp * 5 - 10 * bUnsafe + 10 * wUnsafe - 50 * bKingUnsafe + 50 * wKingUnsafe + 50 * bKingSafe - 50 * wKingSafe + 10 * bSafe - 10 * wSafe ;
		return res;
	}
	
	private static double score1(chess[][] c) {
		int b = 0, w = 0, bk = 0, wk = 0, bRow = 0, wRow = 0, wCol = 0, bCol = 0, wHome = 0, count = 0, wkRow = 0,
				bkRow = 0, be = 0, we = 0;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (c[i][j].color.equals("b") && !c[i][j].king) {
					b++;
					bRow += i;
					count++;
				}

				if (c[i][j].color.equals("b") && c[i][j].king) {
					bk++;
					bkRow += i;
					count++;

				}
				if (c[i][j].color.equals("w") && !c[i][j].king) {
					w++;
					wHome += 5;
					count++;
				}
				if (c[i][j].color.equals("w") && c[i][j].king) {
					wk++;
					wkRow += i;
					count++;

				}
			}
		}
		double res = b - w + bk * 2 - wk * 2;
		return res;
	}

	private static chess[][] doMove(chess[][] board, act m) {
		chess cur = board[m.sr][m.sc];
		board[m.er][m.ec] = cur;
		board[m.sr][m.sc] = new chess(m.sr, m.sc, 8 - m.sr, letter[m.sc], ".", false);

		if (m.er == length - 1 && cur.color.equals("b")) {
			cur.king = true;
		}
		if (m.er == 0 && cur.color.equals("w")) {
			cur.king = true;
		}
		if (!m.skip.isEmpty()) {
			for (point p : m.skip) {
				board[p.row][p.col] = new chess(p.row, p.col, 8 - p.row, letter[p.col], ".", false);
			}
		}
		return board;
	}

}