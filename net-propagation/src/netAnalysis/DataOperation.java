package netAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 * 
 * @author geng yufeng
 * 
 */
public class DataOperation {
	public static int MAX_NODES_NUM = 100000;

	public List<NodeUnit> initNetwork; // 网络结构
	public List<BlogUnit> initBlogs; // 微博数据集

	private HashMap<String, Integer> idIdMap;

	public DataOperation() {
		initNetwork = new ArrayList<NodeUnit>();
		initBlogs = new ArrayList<BlogUnit>();

		idIdMap = new HashMap<String, Integer>();
	}
	
	/**
	 * 数据特征分析
	 */
	public void dataMining(){
		//输出度分布、转发状态数
		try {
			File file = new File("E:\\data_op\\node_info.txt");
			if(!file.exists()){
				file.createNewFile();
			}
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write("节点编号\t度\t微博数\n");
			int edgeCount = 0;
			int max_degree = 0, max_i=0;
			for(NodeUnit node : initNetwork){
				edgeCount += node.fansNum();
				if(node.fansNum() > max_degree){
					max_degree = node.fansNum();
					max_i = node.getId();
				}
				output.write(node.getId()+"\t"+node.fansNum()+"\t"+node.blogUnits.size()+"\n");
			}
			System.out.println("Total nodes:"+initNetwork.size()+",total edges:"
					+edgeCount+",average degree:"+(double)edgeCount/initNetwork.size()
					+",max degree:"+max_i+":"+max_degree);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<NodeUnit> importNetwork(String fileName) {
		System.out.println(">>>Start importing network node file:");
		// TODO
		// 读取爬虫爬取的网络拓扑文件,并保存为NodeUnit数组

		if (fileName == "") {
			Random random = new Random(1000);
			// 假设1000个用户节点
			for (int i = 0; i < 1000; i++) {
				NodeUnit node = new NodeUnit();
				// 每个用户随即增加不多于100个粉丝
				for (int j = 0; j < random.nextInt(100); j++) {
					node.addFan(new FansNode(random.nextInt(1000)));
				}
				initNetwork.add(node);
			}
		}

		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(new File(
					"E:\\data_op\\ed_node.txt")));
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = "";
			int count = 0;
			while ((line = reader.readLine()) != null) {
				String[] strints = line.split(" ");
				if (!idIdMap.containsKey(strints[0])) {
					idIdMap.put(strints[0], count++);
					output.write("" + strints[0] + " " + (count - 1) + "\n");
					initNetwork.add(new NodeUnit(initNetwork.size()));
				}
				if (!idIdMap.containsKey(strints[1])) {
					idIdMap.put(strints[1], count++);
					output.write("" + strints[1] + " " + (count - 1) + "\n");
					initNetwork.add(new NodeUnit(initNetwork.size()));
				}
				initNetwork.get(idIdMap.get(strints[0])).addFan(
						new FansNode(idIdMap.get(strints[1])));
			}
			reader.close();
			output.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(">>>import finished, " + initNetwork.size()
				+ " nodes added.");
		return initNetwork;
	}

	public List<BlogUnit> importBlogs(String directName) {
		System.out.println(">>>Start importing blog file:");
		// TODO

		if (directName == "") {
			Random random = new Random(1000);
			for (int i = 0; i < 10000; i++) {
				initBlogs.add(new BlogUnit(random.nextDouble(), random
						.nextInt(500), "", random.nextDouble(), random
						.nextInt(500), ""));
			}
		}

		File direct = new File(directName);
		String[] files = direct.list();
		int totalBlogCount = 0;
		int totalFileCount = 0;
		for (String fileName : files) {
			totalFileCount++;
			String line = "";
			String[] items = null;
			try {
				int id = Integer.parseInt(fileName.replace(".statuses", ""));
				BufferedReader reader = new BufferedReader(new FileReader(
						directName + "\\" + fileName));
				while ((line = reader.readLine()) != null) {
					totalBlogCount++;
					items = line.split("\t");
					DateFormat f = new SimpleDateFormat(
							"EEE MMM dd HH:mm:ss zzzzz yyyy", Locale.ENGLISH);
					Date date1 = f.parse(items[0]);
					if (!idIdMap.containsKey(items[1])) {
						System.out.println("not found: " + items[1]);
						continue;
					}
					int id1 = idIdMap.get(items[1]);
					Date date2 = f.parse(items[3]);
					int id2;
					if (idIdMap.containsKey(items[4])) {
						id2 = idIdMap.get(items[4]);
					} else {
						id2 = -1;// 无法匹配的用户，肯定是没有用的
					}

					// int id2 = idIdMap.get(Integer.parseInt(items[4]));
					BlogUnit blog = new BlogUnit((double) date1.getTime()/60000, id1,
							items[2], (double) date2.getTime()/60000, id2, items[5]);

					initBlogs.add(blog);
					initNetwork.get(id1).addBlog(blog);
				}
				reader.close();
				// } catch (NullPointerException e) {
				// // 源节点不在节点列表中
				// } catch (NumberFormatException e) {
				// // TODO: handle exception
				// // 数字文本过长（这些数字未出现在源节点中）
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println(">>>import finished, " + initBlogs.size()
				+ " blogs added. Total blogs " + totalBlogCount);
		System.out.println(">>>files read: " + totalFileCount);
		return initBlogs;
	}

	public void sort() {
		for (NodeUnit node : initNetwork) {
			if (node.blogUnits == null) {
				System.out.println(node.getId());
			}
			Collections.sort(node.blogUnits, new Comparator<BlogUnit>() {
				@Override
				public int compare(BlogUnit o1, BlogUnit o2) {
					// TODO Auto-generated method stub
					return o1.init_id.compareTo(o2.init_id);
				}
			});
		}
	}

	public void save() {
		ObjectOutputStream oos = null;
		BufferedWriter ooos = null;
		BufferedWriter oooos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(
					"E:\\data_op\\nodes"));
			ooos = new BufferedWriter(new FileWriter(new File(
					"E:\\data_op\\nodes.txt")));
			oooos = new BufferedWriter(new FileWriter(new File(
					"E:\\data_op\\pd.txt")));
			for (NodeUnit node : initNetwork) {
				oos.writeObject(node);
				ooos.write(node.getId() + ": ");
				oooos.write(node.getId() + ": p1 = " + node.p1 + " p2 = "
						+ node.p2 + " d1 = " + node.d1 + " d2 = " + node.d2
						+ "\n");
				for (FansNode fans : node.fansNodes) {
					ooos.write(fans.p + " ");
				}
				ooos.write("\n");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
