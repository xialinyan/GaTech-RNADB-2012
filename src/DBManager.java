import java.util.*;
import java.util.logging.*;
import java.io.IOException;
import java.sql.*;
public interface DBManager {
	void init(String fileName) throws IOException;
	void insert(ArrayList array);
	void update(ArrayList array);
	ResultSet search(ArrayList array);
}
