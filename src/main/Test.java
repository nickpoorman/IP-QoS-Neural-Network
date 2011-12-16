package main;

import java.net.InetAddress;
import java.net.UnknownHostException;

import neuralnetwork.Neuron;

public class Test {

	public static void main(String[] args) {

		// Neuron n = new Neuron();
		// Neuron n2 = new Neuron();
		//
		// System.out.println("should be false: " + n.equals(n2));
		// System.out.println("should be false: " + n.equals(n));
		//
		// String initial = "192.168.1.10";
		//
		// String[] sp = initial.split("\\.");
		// String[] bin = new String[sp.length];
		// System.out.println("len: " + sp.length);
		// for (int i = 0; i < sp.length; i++) {
		// int a = Integer.parseInt(sp[i]);
		// String outA = Integer.toBinaryString(a);
		// while (outA.length() < 8) {
		// outA = "0" + outA;
		// }
		// bin[i] = outA;
		// System.out.println(outA);
		// }
		//
		// System.out.println("=====");
		//
		// int a = Integer.parseInt(sp[0]);
		// int b = Integer.parseInt(sp[1]);
		// int c = Integer.parseInt(sp[2]);
		// int d = Integer.parseInt(sp[3]);
		// int tst = (a << 24) | (b << 16) | (c << 8) | d;
		// String outA = Integer.toBinaryString(tst);
		// System.out.println(outA);

		// InetAddress address = null;
		// try {
		// address = InetAddress.getByName(initial);
		// } catch (UnknownHostException e) {
		// // Your String wasn't a valid IP Address or host name
		// }
		// byte[] binaryIP = address.getAddress();
		//
		// for (byte b : binaryIP) {
		// boolean[] bits = byteToBits(b);
		//
		// }

		String initial2 = "192.168.1.10";

		String[] sp2 = initial2.split("\\.");
		String[] bin2 = new String[sp2.length];
		// System.out.println("len: " + sp.length);
		for (int i2 = 0; i2 < sp2.length; i2++) {
			int a2 = Integer.parseInt(sp2[i2]);
			String outA2 = Integer.toBinaryString(a2);
			while (outA2.length() < 8) {
				outA2 = "0" + outA2;
			}
			bin2[i2] = outA2;
			// System.out.println(outA);
		}

		String actual2 = "";
		actual2 = bin2[0] + bin2[1] + bin2[2] + bin2[3];
		actual2 = actual2.replaceAll(".(?=.)", "$0 ");
		System.out.println("Input: " + actual2);

	}
	//
	// public static boolean[] byteToBits(byte b) {
	// boolean[] bits = new boolean[8];
	// for (int i = 0; i < bits.length; i++) {
	// bits[i] = ((b & (1 << i)) != 0);
	// }
	// return bits;
	// }

}
