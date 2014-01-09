package netAnalysis;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

import DataModel.NodeUnit;
import FactorGenerate.Generator;
import Simulation.StaticStartup;

public class main {

	public static int Day = 1440;
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
		System.out.println(">>>读文件结束");
		di.dataMining();
		
		StaticStartup mypropogation=new StaticStartup(di.initNetwork,1000, 10*Day, 100000, 0.01);
		 int s[]= new int[1000];
		 Random random = new Random(1000);
		 for(int i=0; i<1000; i++){
			 s[i] = random.nextInt(80000);
		 }
		 //mypropogation.Run(s);

	}

}
