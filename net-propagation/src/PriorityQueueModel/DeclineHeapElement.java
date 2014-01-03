package PriorityQueueModel;

import DataModel.FansNode;
import DataModel.NodeUnit;

public class DeclineHeapElement {
	public FansNode ifan ;//记录有向边
	public int toid;
	public double time;
	public DeclineHeapElement(FansNode _ifan, int i, double t){
		ifan=_ifan;
		toid=i;
		time=t;		
	}
}
