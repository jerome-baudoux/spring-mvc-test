package dao;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public abstract class AbstractDaoTest {

	static boolean loaded = false;

    @Autowired
    private IDatabaseConnection databaseConnection;
    
    protected abstract String getDataset();
    
    /**
     * Creates the table the first time
     * @throws Exception
     */
    private void loadDdl() throws Exception {
    	if (!loaded) {
        	URL url = Resources.getResource("sql/ddl-hsqldb.sql");
        	String sql = Resources.toString(url, Charsets.UTF_8);

        	Connection c = databaseConnection.getConnection();
        	c.createStatement().executeUpdate(sql);
        	
    		loaded = true;
    	}
    }
    
    /**
     * Prepares the database before each test
     * @throws Exception
     */
    @Before
    public void setup() throws Exception {
    	loadDdl();
    	
    	try (InputStream is = Resources.getResource(getDataset()).openStream()) {
        	IDataSet data = new FlatXmlDataSetBuilder().build(is);
        	DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, data);
    	}
    }
}
