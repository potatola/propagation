package netAnalysis;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Delayed;

import DataModel.BlogUnit;
import DataModel.FansNode;
import DataModel.NodeUnit;

/**
 * 从数据文件中读入网络数据，建立数据结构
 * @author geng yufeng
 *
 */
public class DataOperation {
	public static int MAX_NODES_NUM = 100000;
	
	public List<NodeUnit> initNetwork;	//网络结构
	public List<BlogUnit> initBlogs;	//微博数据集
	
	private HashMap<Integer, Integer> idIdMap;
	
	public DataOperation(){
		initNetwork = new ArrayList<NodeUnit>();
		initBlogs = new ArrayList<BlogUnit>();
		
		idIdMap = new HashMap<Integer, Integer>();
	}
	
	public List<NodeUnit> importNetwork(String fileName){
		System.out.println(">>>Start importing network node file:");
		//TODO
		//读取爬虫爬取的网络拓扑文件,并保存为NodeUnit数组
		
		if(fileName == ""){
			Random random = new Random(1000);
			//假设1000个用户节点
			for(int i=0; i<1000; i++){
				NodeUnit node = new NodeUnit();
				//每个用户随即增加不多于100个粉丝
				for(int j=0; j<random.nextInt(100); j++){
					node.addFan(new FansNode(random.nextInt(1000)));
				}
				initNetwork.add(node);
			}
		}
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = "";
			int count = 0;
			while((line=reader.readLine()) != null){
				String[] strints = line.split(" ");
				int id1 = Integer.parseInt(strints[0]);
				int id2 = Integer.parseInt(strints[1]);
				if(!idIdMap.containsKey(id1)){
					idIdMap.put(id1, count++);
					initNetwork.add(new NodeUnit(initNetwork.size()));
				}
				if(!idIdMap.containsKey(id2)){
					idIdMap.put(id2, count++);
					initNetwork.add(new NodeUnit(initNetwork.size()));
				}
				initNetwork.get(idIdMap.get(id1)).addFan(new FansNode(idIdMap.get(id2)));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		System.out.println(">>>import finished, "+initNetwork.size()+" nodes added.");
		return initNetwork;
	}
	
	public List<BlogUnit> importBlogs(String directName){
		System.out.println(">>>Start importing blog file:");
		//TODO
		
		if(directName == ""){
			Random random = new Random(1000);
			for(int i=0; i<10000; i++){
				initBlogs.add(new BlogUnit(random.nextDouble(), random.nextInt(500),  
						"", random.nextDouble(),random.nextInt(1000), ""));
			}
		}
		
		File direct = new File(directName);
		String[] files = direct.list();
		for(String fileName : files){
			try {
				int id = Integer.parseInt(fileName.replace(".statuses", ""));
				BufferedReader reader = new BufferedReader(new FileReader(directName+"\\"+fileName));
				String line = "";
				while((line=reader.readLine()) != null){
					String[] items = line.split("\t");
					DateFormat f = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzzz yyyy", Locale.ENGLISH);
					Date date1 = f.parse(items[0]);
					int id1 = Integer.parseInt(items[1]);
					Date date2 = f.parse(items[3]);
					int id2 = Integer.parseInt(items[4]);

					initBlogs.add(new BlogUnit((double)date1.getTime(), idIdMap.get(id1), items[2], (double)date2.getTime(), idIdMap.get(id2), items[5]));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(">>>import finished, "+initBlogs.size()+" blogs added.");
		return initBlogs;
	}
}
