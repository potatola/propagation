package netAnalysis;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataOperation di = new DataOperation();
		di.importNetwork("D:\\data_op\\twitter_combined.txt");
		di.importBlogs("D:\\data_op\\reposts");
		
	}

}
