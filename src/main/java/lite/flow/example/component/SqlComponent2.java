/**
 * 
 */
package lite.flow.example.component;

import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author ToivoAdams
 *
 */
public class SqlComponent2 {
	
	@Resource
	protected DataSource database;


	public String read(Map<String,Object> sqlParams) {
		
		return "not implemented";
	}

}
