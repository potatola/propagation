package DataModel;

/**
 * 一条微博的数据结构
 * @author geng yufeng
 *
 */
public class BlogUnit {
	public int id;			//微博唯一标识符
	public int belongTo;	//微博发布者id
	public int from;		//微博转发源用户id
	public double time;		//微博发送时间
	
	/**
	 * 
	 * @param _id 微博唯一标识符
	 * @param _belong 微博属主
	 * @param _from 微博转发来源(原创则为0)
	 */
	public BlogUnit(int _id, int _belong, int _from, double _time){
		id = _id;
		belongTo = _belong;
		from = _from;
		time = _time;
	}
}
