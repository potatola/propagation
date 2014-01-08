package FactorGenerate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import DataModel.BlogUnit;
import DataModel.FansNode;
import DataModel.NodeUnit;

public class Generator {

	static public boolean FactorGenerator(List<NodeUnit> nodes,
			List<BlogUnit> blogs) {
		for (NodeUnit nodeUnit : nodes) {
			double p = 0, d = 0;
			int cc = 0;
			for (FansNode fanUnit : nodeUnit.fansNodes) {
				double count = 0;
				double delay = 0;
				for (BlogUnit blogUnit : nodes.get(fanUnit.id).blogUnits) {
					int index = Collections.binarySearch(nodeUnit.blogUnits,
							blogUnit, new Comparator<BlogUnit>() {

								@Override
								public int compare(BlogUnit o1, BlogUnit o2) {
									return o1.init_id.compareTo(o2.init_id);
								}
							});
					if (index >= 0
							&& nodeUnit.blogUnits.get(index).time < blogUnit.time) {
						count += 1;
						delay += (blogUnit.time - nodeUnit.blogUnits.get(index).time);
					}
				}
				if(nodes.get(fanUnit.id).blogUnits.size() == 0)
					fanUnit.p = 0;
				else
					fanUnit.p = count / nodes.get(fanUnit.id).blogUnits.size();
				p += fanUnit.p;
				if(count == 0)
					fanUnit.delay = 0;
				else
				{
					fanUnit.delay = delay / count;
					p += fanUnit.p;
					d += fanUnit.delay;
					cc++;
				}
			}
			nodeUnit.p1 = p / nodeUnit.fansNodes.size();
			nodeUnit.p2 = p / cc;
			nodeUnit.d1 = d / nodeUnit.fansNodes.size();
			nodeUnit.d2 = d / cc;
		}
		return true;
	}

}
