package test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBEntity implements Serializable {   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Map<String,List<Object>> db_save = new HashMap<String,List<Object>>();
}
