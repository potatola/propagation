package DataModel;

/**
 * 一条微博的数据结构
 * @author geng yufeng
 *
 */
public class BlogUnit {
	public double time;			//微博发布时间
	public int belongTo;		//微博发布者id
	public int id;				//微博唯一标识符
	
	public double source_time;	//原微博发布时间
	public int from;			//微博转发源用户id
	public int init_id;		//原微博id
	
	/**
	 * 
	 * @param _time	微博时间
	 * @param _belongTo	微博博主id
	 * @param _id	微博id
	 * @param _source_time	原微博发布时间
	 * @param _from	原微博博主id
	 * @param _init_id	原微博id
	 */
	public BlogUnit(double _time, int _belongTo, int _id, double _source_time, 
			int _from, int _init_id){
		time = _time;
		belongTo = _belongTo;
		id = _id;
		init_id = _init_id;
		source_time = _source_time;
		from = _from;
	}
}
