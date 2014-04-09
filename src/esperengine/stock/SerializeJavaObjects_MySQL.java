package esperengine.stock;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;
public class SerializeJavaObjects_MySQL {
  static final String WRITE_OBJECT_SQL = "INSERT INTO java_objects(name, object_value) VALUES (?, ?)";

  static final String READ_OBJECT_SQL = "SELECT object_value FROM java_objects WHERE id = ?";

  public static Connection getConnection() throws Exception {
    String driver = "org.gjt.mm.mysql.Driver";
    String url = "jdbc:mysql://127.0.0.1:3306/esper_t?characterEncoding=utf8";
    String username = "root";
    String password = "314159zlc";
    Class.forName(driver);
    Connection conn = DriverManager.getConnection(url, username, password);
    return conn;
  }

  public static long writeJavaObject(Connection conn, Object object) throws Exception {
    String className = object.getClass().getName();
    PreparedStatement pstmt = conn.prepareStatement(WRITE_OBJECT_SQL);

    // set input parameters
    pstmt.setString(1, className);
    pstmt.setObject(2, object);
    // pstmt.executeUpdate();

    // get the generated key for the id
    int id = pstmt.executeUpdate();
    // int id = -1;
    // if (rs.next()) {
    //   // id = rs.getInt(1);
    //   id = 1;
    // }

    // rs.close();
    pstmt.close();
    System.out.println("writeJavaObject: done serializing: " + className);
    return id;
  }

  public static Object readJavaObject(Connection conn, long id) throws Exception {
    PreparedStatement pstmt = conn.prepareStatement(READ_OBJECT_SQL);
    pstmt.setLong(1, id);
    ResultSet rs = pstmt.executeQuery();
    rs.next();
    ObjectInputStream in = new ObjectInputStream(rs.getBinaryStream(1));
    Object kk = in.readObject();
    System.out.println(kk.getClass().getName());
    Object object = rs.getObject(1);
    String className = object.getClass().getName();

    rs.close();
    pstmt.close();
    System.out.println("readJavaObject: done de-serializing: " + className);
    return kk;
  }
  public static void main() {
    Connection conn = null;
    try {
      conn = getConnection();
      System.out.println("conn=" + conn);
      conn.setAutoCommit(false);
      List<Object> list = new ArrayList<Object>();
      list.add("This is a short string.");
      list.add(new Integer(1234));
      list.add(new Date());

      long objectID = 6; //writeJavaObject(conn, list);
      conn.commit();
      System.out.println("Serialized objectID => " + objectID);
      List listFromDatabase = (List) readJavaObject(conn, objectID);
      System.out.println("[After De-Serialization] list=" + listFromDatabase);
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    }
  }
}