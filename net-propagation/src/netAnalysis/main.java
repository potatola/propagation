package netAnalysis;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import DataModel.NodeUnit;
import FactorGenerate.Generator;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataOperation di = new DataOperation();
		boolean flag = false;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(
					"E:\\data_op\\nodes"));
			while (true) {
				di.initNetwork.add((NodeUnit) ois.readObject());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			flag = true;
		} catch (EOFException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (flag) {
			di.importNetwork("E:\\data_op\\twitter_combined.txt");
			di.importBlogs("E:\\data_op\\reposts");
			di.sort();
			Generator.FactorGenerator(di.initNetwork, di.initBlogs);
			di.save();
		}

	}

}
