package Simulation;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import DataModel.FansNode;
import DataModel.NodeUnit;
import PriorityQueueModel.ActiveHeapElement;
import PriorityQueueModel.DeclineHeapElement;

public class DynamicStartup extends SimuPropogation {
	
	public DynamicStartup(List<NodeUnit> mynodes, int i, double d, int j,	double e) {
		super( mynodes,  i, d,  j,  e);
	}

	@Override
	/*
	 *来自active heap的节点vj和时间tj。
	 * 激活它并对周围产生影响pij+α
	 * 总能量减少b

	 */
	public void ActivateNode(ActiveHeapElement e) {		
	}
}

