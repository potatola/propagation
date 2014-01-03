package PriorityQueueModel;

import java.util.Comparator;


public class myDComparator implements Comparator<DeclineHeapElement> {

	@Override
	public int compare(DeclineHeapElement o1, DeclineHeapElement o2) {
		// TODO Auto-generated method stub
		return Double.compare(o1.time, o2.time);
	}

}
