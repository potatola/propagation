package Simulation;
import java.util.*;

import DataModel.NodeUnit;
import DataModel.FansNode;
import PriorityQueueModel.ActiveHeapElement;
import PriorityQueueModel.DeclineHeapElement;

public class StaticStartup extends SimuPropogation {

	

	public StaticStartup(List<NodeUnit> mynodes, int i, double d, int j,double e) {
		super( mynodes,  i, d,  j,  e);
		// TODO Auto-generated constructor stub
	}

	@Override
	/*
	 *来自active heap的节点vj和时间tj。
	 * 激活它并对周围产生影响pij。
	 * 对应明星广告和网络营销两个模式

	 */
	public void ActivateNode(ActiveHeapElement e) {		
		int jid=e.id;
		double tj=e.time;
		NodeUnit jnode=Nodes.get( jid );
		jnode.activate();
		NumOfActivenodes+=1;
		CurrentTime=tj;
		Iterator<FansNode> itr=jnode.fansNodes.iterator();
		while(itr.hasNext()){  //遍历粉丝
			FansNode ifan=itr.next();		
			int iid= ifan.id;
			NodeUnit inode=Nodes.get(  iid );
			//未被激活的节点更新影响因子
			if ( inode.isActivated()==false ){
				
				double delay=ifan.delay;
				inode.p=1-(1-inode.p)*(1-ifan.p);	//粉丝i的被影响力改变
				Random r=new Random();
				double ti=tj+r.nextDouble()*2*delay;//粉丝i的被影响时间
				
				
				if ( inode.p>theta){
					ActiveHeapElement ae=new  ActiveHeapElement(iid,ti);
					ActiveHeap.add(ae);
				}
				else{
					DeclineHeapElement de=new DeclineHeapElement(ifan,iid,ti);
					DeclineHeap.add(de);
				}					
				
			}
			
			
		}
			
			
		
			
		
	}
}
