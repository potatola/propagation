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

	static final int DAV = 0;
	static final int DAV_OPT = 3;
	static final int XIAOMI = 1;
	static final int CHOUJIANG = 2;

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
		
		//di.dataMining();
		int[] es = {500};
		for(int i:es){
			System.out.println(">>>simulating with "+i+" energy");
			for(NodeUnit node:di.initNetwork){
				node.diactivate();
			}
			StaticStartup mypropogation=new StaticStartup(di.initNetwork,1000, 10*Day, 100000, 0.0005);
			 int s[]= di.generateInit(XIAOMI, i);
			 mypropogation.Run(s);
		}
		 
	}

}
