package netAnalysis;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DataModel.BlogUnit;
import DataModel.FansNode;
import DataModel.NodeUnit;

/**
 * 从数据文件中读入网络数据，建立数据结构
 * @author geng yufeng
 *
 */
public class DataImport {
	public List<NodeUnit> initNetwork = new ArrayList<NodeUnit>();
	public List<BlogUnit> initBlogs = new ArrayList<BlogUnit>();
	
	public List<NodeUnit> importNetwork(String fileName){
		System.out.println(">>>Start importing network node file:");
		//TODO
		//读取爬虫爬取的网络拓扑文件,并保存为NodeUnit数组
		
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
		

		System.out.println(">>>import finished, "+initNetwork.size()+" nodes added.");
		return initNetwork;
	}
	
	public List<BlogUnit> importBlogs(String fileName){
		System.out.println(">>>Start importing blog file:");
		//TODO
		
		Random random = new Random(1000);
		for(int i=0; i<10000; i++){
			initBlogs.add(new BlogUnit(random.nextInt(500), random.nextInt(1000), random.nextInt(1000)));
		}
		
		System.out.println(">>>import finished, "+initBlogs.size()+" blogs added.");
		return initBlogs;
	}
}
