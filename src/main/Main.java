package main;

import java.util.ArrayList;
import java.util.Scanner;

import framework.Inputtable;
import framework.SimFramework;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter the 32bit IP Address: ");
		String initial = sc.nextLine();
		System.out.println();

		Rule110Model model = new Rule110Model();
		SimFramework<Cell, Cell> sf = new SimFramework<Cell, Cell>(model);
		sf.takeInputFunction(new BinaryInput());
		for (int i = 0; i < times; i++) {
			sf.tick(initial);
		}
	}
}

class BinaryInput implements Inputtable<Cell> {

	@Override
	public ArrayList<Cell> input(String line) {
		// TODO: Take each of the inputs in the string and create a collection
		// out of them
		ArrayList<Cell> list = new ArrayList<Cell>();
		for (String s : line.split("\\s+")) {
			s = s.trim();
			if (s.equals("1")) {
				list.add(Cell.ON);
			} else if (s.equals("0")) {
				list.add(Cell.OFF);
			} else {
				System.err.println("[" + s + "] is not a valid input");
			}
		}

		return list;
	}
}