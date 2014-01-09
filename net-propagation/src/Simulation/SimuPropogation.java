package Simulation;
import java.util.*;

import DataModel.FansNode;
import DataModel.NodeUnit;
import PriorityQueueModel.ActiveHeapElement;
import PriorityQueueModel.DeclineHeapElement;
import PriorityQueueModel.myAComparator;
import PriorityQueueModel.myDComparator;


public abstract class SimuPropogation {
	public double TimeLimit;  //最大时间约束，与停止条件有关
	public double EnergyLimit; //初始能量
	public int NodesLimit; //要求激活的节点数
	
	public int NumOfActivenodes;//当前已激活的节点数
	public double CurrentTime;//当前时间
	public double CurrentEnergy;//当前剩余能量
	public double theta;//节点是否被激活的阈值
	public List<NodeUnit> Nodes=new ArrayList<NodeUnit>();
	
	PriorityQueue<ActiveHeapElement> ActiveHeap;
	PriorityQueue<DeclineHeapElement> DeclineHeap;
	Comparator<ActiveHeapElement> acomparator = new myAComparator();
	Comparator<DeclineHeapElement> dcomparator = new myDComparator();
	
	public SimuPropogation(){}
	public SimuPropogation(List<NodeUnit> Nodes, int NodesLimit,double TimeLimit,double EnergyLimit, double theta  ){
		this.Nodes=Nodes;
		this.NodesLimit=NodesLimit;
		this.TimeLimit=TimeLimit;
		this.EnergyLimit=EnergyLimit;
		this.NumOfActivenodes=0;
		this.CurrentTime=0;
		this.CurrentEnergy=EnergyLimit;
		this.theta=theta;
		ActiveHeap=new PriorityQueue<ActiveHeapElement>(NodesLimit,acomparator);
		DeclineHeap=new PriorityQueue<DeclineHeapElement>(NodesLimit,dcomparator);		
	}
	/*
	 * 关于激活节点后发生的事的抽象方法。网络营销和明星广告相同。奖励营销则不同。
	 */
	public abstract void ActivateNode(ActiveHeapElement e);
	
	/*
	 * 初始化，给定D和放一些节点入堆
	 */
	public void Initialization( int StartNodes[]){
		//System.out.println(StartNodes);
		//System.out.println(ActiveHeap);
		for (int i=0; i<StartNodes.length; i++){
			ActiveHeapElement t=new ActiveHeapElement(StartNodes[i], 0.0);
			ActiveHeap.add(t);
		}
		//System.out.println(ActiveHeap);
		
	}

	/*
	 * 终止条件 返回true继续运行 返回false停止。
	 * 静态时，dc为false，节点到限制且时间到限制后，才停止。输出两种模拟结果
	 * 动态时，三个条件限制,都不行了才会停止。
	 * 不过如果栈为空，则立刻停止（很少遇到这个情况）
	 */
	public boolean TerminateCondition(){
		boolean nodesC=true;
		boolean timeC=true;
		boolean dC=true;
		boolean queueC=true;
		if (nodesC && (NumOfActivenodes >=NodesLimit) ){
			System.out.println("到了限定节点数"+NodesLimit+"。已经感染了"+NumOfActivenodes+"个节点，用时"+CurrentTime);
			nodesC= false;
		}
		if (timeC && (CurrentTime>=TimeLimit) ){
			System.out.println(CurrentTime+"已经到了限定时间"+", 感染了"+NumOfActivenodes+"个节点");	
			timeC=false;
		}
		if (dC && (CurrentEnergy<=0) ){
			System.out.println("能量已经用完了。感染了"+NumOfActivenodes+"个节点，用时"+CurrentTime);	
			dC=false;
		}
		if (queueC && ActiveHeap.isEmpty() ){
			System.out.println("没有活节点啦。感染了"+NumOfActivenodes+"个节点，用时"+CurrentTime);	
			queueC=false;
		}
		return (nodesC || timeC || dC) &&queueC;			
	}
	/*
	 * 处理Declineheap弹出元素的方法
	 */
	public void DeclineNode(DeclineHeapElement e){
		FansNode ifan=e.ifan;
		int iid=e.toid;
		NodeUnit inode=Nodes.get(iid);
		inode.p=1-(1-inode.p)/(1-ifan.p);		
	}
	/*
	 * 模拟跑起来~
	 */
	public void Run(int StartNodesID[] )
	{
		//选节点
		//初始化heap
		Initialization(StartNodesID);
		
		while (TerminateCondition()){
			ActiveHeapElement activeNode=ActiveHeap.peek();
			//System.out.println(activeNode.time);
			DeclineHeapElement declineNode=DeclineHeap.peek();
			//System.out.println(declineNode);
			if (declineNode==null || activeNode.time<declineNode.time){
				ActiveHeap.poll();
				ActivateNode(activeNode);
			}
			else{
				DeclineHeap.poll();
				DeclineNode(declineNode);
			}		 			
			
		}
		//只要不满足终止条件就弹栈并判断，分别运行activenode和declinenode
	}	

}
