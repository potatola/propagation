package DataModel;

import java.io.Serializable;
import java.rmi.activation.ActivateFailedException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.tools.JavaCompiler;

/**
 * 微博用户数据单元
 * @author geng yufeng
 *
 */
public class NodeUnit implements Serializable{
	private int id;
	private boolean isActivated;	//节点是否被感染
	public double t;			//节点被感染的时间
	public double p;			//节点被影响因子
	public List<FansNode> fansNodes;		//节点粉丝节点组成的数组
	public List<BlogUnit> blogUnits;	//节点发布的微博列表
	
	public double p1;
	public double p2;
	public double d1;
	public double d2;
	
	public NodeUnit(){
		isActivated = false;
		t = p = 0;
		fansNodes = new ArrayList<FansNode>();
		blogUnits = new ArrayList<BlogUnit>();
	}

	public NodeUnit(int _id){
		id = _id;
		isActivated = false;
		t = p = 0;
		fansNodes = new ArrayList<FansNode>();
		blogUnits = new ArrayList<BlogUnit>();
	}
	
	public double averageP(){
		double avgp = 0;
		for(FansNode fan: fansNodes){
			avgp += fan.p;
		}
		if(fansNum() == 0)
			return 0;
		avgp = avgp / fansNum();
		return avgp;
	}
	
	public void diactivate(){
		isActivated = false;
	}
	
	public int getId(){
		return id;
	}
	
	public void activate(){
		isActivated = true;
	}
	public boolean isActivated(){
		return isActivated;
	}
	/**
	 * 新增一个粉丝
	 * @param fan
	 * @return 返回增加粉丝后的粉丝数
	 */
	public int addFan(FansNode fan){
		if(fansNodes.contains(fan)){
			return -1;
		}
		fansNodes.add(fan);
		//System.out.println("Node "+id+" added fun:"+fan.id);
		return fansNodes.size();
	}
	public int fansNum(){
		return fansNodes.size();
	}
	
	/**
	 * 新增一条发布的微博
	 * @param blog
	 * @return 增加后的微博数目
	 */
	public int addBlog(BlogUnit blog){
		blogUnits.add(blog);
		return blogUnits.size();
	}
}
