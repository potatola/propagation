package Simulation;

import java.util.ArrayList;
import java.util.List;

import DataModel.FansNode;
import DataModel.NodeUnit;
import PriorityQueueModel.ActiveHeapElement;
import PriorityQueueModel.DeclineHeapElement;

public class test {
	 public static void main(String args[]){  
		 NodeUnit n1=new NodeUnit();
		 NodeUnit n2=new NodeUnit();
		 NodeUnit n3=new NodeUnit();
		 NodeUnit n4=new NodeUnit();
		 FansNode f12=new FansNode(1, 0.3, 0.1);
		 FansNode f13=new FansNode(2,0.4,0.1);
		 n1.addFan(f12);
		 n1.addFan(f13);
		 FansNode f24=new FansNode(3,0.8,0.1);
		 n2.addFan(f24);
		 FansNode f14=new FansNode(3, 0.2, 0.1);
		 n1.addFan(f14);
		 
		 ArrayList<NodeUnit> mynodes=new ArrayList<NodeUnit>();
		 mynodes.add(n1);
		 mynodes.add(n2);
		 mynodes.add(n3);
		 mynodes.add(n4);
		 
		 StaticStartup mypropogation=new StaticStartup(mynodes,3, 1.0, 10, 0.3);
		 int s[]= new int[]{0};
		 int s2[]= new int[]{1};
		 mypropogation.Run(s);	
		 
	 }

}
