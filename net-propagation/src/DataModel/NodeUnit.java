package DataModel;

import java.rmi.activation.ActivateFailedException;
import java.util.ArrayList;
import java.util.List;

/**
 * 微博用户数据单元
 * @author geng yufeng
 *
 */
public class NodeUnit {
	private boolean isActivated;	//节点是否被感染
	public double t;			//节点被感染的时间
	public double p;			//节点被影响因子
	public List<FansNode> fansNodes;		//节点粉丝节点组成的数组
	
	public NodeUnit(){
		isActivated = false;
		t = p = 0;
		fansNodes = new ArrayList<FansNode>();
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
		fansNodes.add(fan);
		return fansNodes.size();
	}
	public int fansNum(){
		return fansNodes.size();
	}
}
