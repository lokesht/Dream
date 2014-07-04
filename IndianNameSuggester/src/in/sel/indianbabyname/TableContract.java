/*Most of The column are self Explainable by Skipping that only few 
 * columns description I am maintaining*/

package in.sel.indianbabyname;


public class TableContract {
	private TableContract() {
	}

	/* Data Type And Separator */
	private static final String TYPE_INTEGER = " INTEGER ";
	private static final String TYPE_BOOLEAN = " BOOLEAN ";
	private static final String TYPE_TEXT = " TEXT ";
	private static final String SEP_COMMA = " , ";

	private static final String CLOSE_BRACE = " ) ";
	private static final String OPEN_BRACE = " ( ";
	private static final String SEMICOLON = " ; ";

	private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
	private static final String AUTO_INCREMENT = " AUTOINCREMENT ";
	private static final String CREATE_TABLE = " CREATE TABLE ";
	private static final String PRIMARY_KEY = " PRIMARY KEY ";
	private static final String NOT_NULL = " NOT NULL ";

	/* Constraints */
	private static final String ON_CONFLICT_REPLACE = " ON CONFLICT REPLACE ";
	private static final String ON_CONFLICT_IGNORE = " ON CONFLICT IGNORE ";
	private static final String UNIQUE = " UNIQUE ";
	
	/* Common Column used in Tables */
	public interface AppColumn {
		String CAUTO_ID = "AutoId";
		String CIS_ACTIVE = "isActive";
	}

	/* Application Related Table */
	public interface TimeStamp {
		String TABLE_NAME = "TimeStamp";
		String CTABLE_NAME = "TableName";
		String CTIME_STAMP = "TimeStamp";

		String SQL_CREATE = CREATE_TABLE + TABLE_NAME 
				+ OPEN_BRACE
		           + AppColumn.CAUTO_ID + TYPE_INTEGER+PRIMARY_KEY+AUTO_INCREMENT+SEP_COMMA
		           + CTABLE_NAME + TYPE_TEXT + SEP_COMMA 
				   + CTIME_STAMP + TYPE_TEXT + SEP_COMMA 
				   + UNIQUE 
				     + OPEN_BRACE 
				       + CTABLE_NAME 
				     + CLOSE_BRACE + ON_CONFLICT_IGNORE 
				+ CLOSE_BRACE;

		String SQL_DROP =  DROP_TABLE+ TABLE_NAME;
	}

	/*String List Of State*/
	public interface Name
	{
		String TABLE_NAME = "Name";
		String NAME_ID = "Id";
		String NAME_EN = "NameEn";
		String NAME_MA = "NameMa";
		String NAME_FRE = "NameFre";
		String DESCRIPTION = "Description";/* */
		
		String SQL_CREATE = CREATE_TABLE+TABLE_NAME
				    +OPEN_BRACE
				    +NAME_ID+TYPE_INTEGER+PRIMARY_KEY+AUTO_INCREMENT+SEP_COMMA
				    +NAME_EN+TYPE_TEXT+SEP_COMMA
				    +NAME_MA+TYPE_TEXT+SEP_COMMA
				    +NAME_FRE+TYPE_INTEGER+SEP_COMMA
				    +DESCRIPTION+TYPE_TEXT+SEP_COMMA
				    + UNIQUE 
				     + OPEN_BRACE 
				       + NAME_EN+SEP_COMMA
				       + NAME_MA+SEP_COMMA
				     + CLOSE_BRACE + ON_CONFLICT_IGNORE
				    +CLOSE_BRACE;
		String SQL_DROP = DROP_TABLE+TABLE_NAME;
	}
}
