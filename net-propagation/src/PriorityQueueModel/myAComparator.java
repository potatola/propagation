package PriorityQueueModel;

import java.util.Comparator;


public class myAComparator implements Comparator<ActiveHeapElement> {

	@Override
	public int compare(ActiveHeapElement arg0, ActiveHeapElement arg1) {
		// TODO Auto-generated method stub
		return Double.compare(arg0.time, arg1.time);
	}

}
