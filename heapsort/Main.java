package pl.marcinbaranowski;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static int Size() throws IOException {
		BufferedReader br = null;
		try {br = new BufferedReader(new FileReader("C:\\Users\\Marcin\\IdeaProjects\\heapSort\\src\\input"));}
		catch (FileNotFoundException el) {el.printStackTrace();}

		int size = 0;
		String line;
		try {
			while ((line = br.readLine()) != null)
				size++;
		}

		catch (IOException e) {System.out.println("File read error");}
		return size;
	}

	public static void printArrayToFile(int[] array) throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter("output.txt", false));
		for (int i = 0; i < array.length; i++)
			pw.print(array[i]+"\n");
        pw.close();
	}

	public static void main(String args[]) throws IOException {
	    int[] array = new int[Size()];
	    int n = array.length;
		BufferedReader br = null;
		br = new BufferedReader(new FileReader("C:\\Users\\Marcin\\IdeaProjects\\heapSort\\src\\input"));
		String line;
		for (int i = 0; (line = br.readLine()) != null; i++) {
			int value = Integer.parseInt(line);
			array[i] = value;
		}
		Heapsort.sort(array);
		// HeapsortIterative.sort(array, n);
		printArrayToFile(array);
		br.close();
	}
}

