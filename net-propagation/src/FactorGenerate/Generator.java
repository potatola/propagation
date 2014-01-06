package FactorGenerate;

import java.util.List;

import DataModel.BlogUnit;
import DataModel.FansNode;
import DataModel.NodeUnit;

public class Generator {
	
	static boolean factorGenerator(List<NodeUnit> nodes, List<BlogUnit> blogs)
	{
		for (NodeUnit nodeUnit : nodes) {
			for (FansNode fanUnit : nodeUnit.fansNodes) {
				nodes.get(fanUnit.id);
			}
		}
		return true;
	}

}
