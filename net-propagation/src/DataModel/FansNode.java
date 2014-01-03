package DataModel;

/**
 * 粉丝节点数组的数据单元
 * @author geng yufeng
 *
 */
public class FansNode {
	public int id;			//这里id对应node数组的下标标号
	public double p;		//节点 v_i 对 v_jv_j 的影响因子 影响因子 ，即 p(j,i )
	public double delay;	//节点 v_i 对 v_jv_j 的延迟 时
	
	/**
	 * 
	 * @param _id 粉丝节点id
	 */
	public FansNode(int _id){
		id = _id;
		p = delay = 0;
	}
    /*
     * 新加的
     */
    public FansNode(int _id,double _p,double _delay){
        id = _id;
        p = _p;
        delay=_delay;
	}
}
