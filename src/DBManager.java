import java.util.*;
import java.util.logging.*;
import java.io.IOException;
import java.sql.*;
public interface DBManager {
	void init(String fileName) throws IOException;
	void insert(ArrayList array); // generics!
	void update(ArrayList array); // generics!
	ResultSet search(ArrayList array); // generics!
}
