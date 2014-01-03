package PriorityQueueModel;

import DataModel.NodeUnit;

public class ActiveHeapElement  {
	public int id;//node的index
	public double time;
	public ActiveHeapElement(int inode, double t){
		id=inode;
		time=t;		

	}
}
