package netAnalysis;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import DataModel.NodeUnit;
import FactorGenerate.Generator;
import Simulation.StaticStartup;

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
					"E:\\data_op\\nodes1"));
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
			

			StaticStartup mypropogation=new StaticStartup(di.initNetwork,3, 100, 1000, 0.2);
			 int s[]= new int[]{1,2,3,4,5,6,7,8,9,10};
			 mypropogation.Run(s);
		}

	}

}
