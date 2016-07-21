/**
 * 
 */
package lite.flow.example.component;

import java.util.Map;

import javax.sql.DataSource;

/**
 * @author ToivoAdams
 *
 */
public class SqlComponent {
	
	protected final DataSource database;

	public SqlComponent(DataSource database) {
		super();
		this.database = database;
	}

	public String read(Map<String,Object> sqlParams) {
		
		return "not implemented";
	}

}
