package com.example.zakahdeserved.Connection;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAL {
    static Connection connection;
    static String username = "root", password = "12!@abAB", ip = "10.0.2.2", port = "3306", database = "zakatraising";
    public static boolean isConnected = false;
    public static StringBuilder insert_query = null;

    String sss;

    private static void Connect() {
        connection = connectionClass();
        if (connection != null)
            isConnected = true;
    }

    private static Connection connectionClass() {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;

//        try {
//            Class.forName("org.mariadb.jdbc.Driver"); // Initialize it
//            connection = DriverManager.getConnection(
//                    "jdbc:mariadb://10.0.2.2:3306/zakatraising?characterEncoding=utf8mb4", "root", "12!@abAB");
//        } catch (Exception exc) {
//            exc.printStackTrace();
//            isConnected = false;
//        }
        // for Ms sql
//        try {
//            Class.forName("net.sourceforge.jtds.jdbc.Driver");
//            String ConnectionUrl = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" +
//                    "databasename=" + database + ";user=" + username + ";password=" + password + ";";
//            connection = DriverManager.getConnection(ConnectionUrl);
//        } catch (Exception ex) {
//            Log.e("Error", ex.getMessage());
//            isConnected = false;
//        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://10.0.2.2:3306/zakatraising";
            String user = "root";
            String pass = "12!@abAB";
            connection = DriverManager.getConnection(url, user, pass);

        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
            isConnected = false;
        }
        return connection;
    }

    public static boolean fillSpinner(Context context, Spinner spinner) {
        boolean success = false;
        DAL.Connect();
        if (isConnected) {
            String spinnerTable = spinner.getResources().getResourceEntryName(spinner.getId());
            if (spinnerTable.contains("1"))
                spinnerTable = spinnerTable.substring(0, spinnerTable.indexOf("1"));
            String query = "select * from " + spinnerTable;
            Statement st = null;
            connection = connectionClass();
            try {
                st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                List<String> spinnerArray = new ArrayList<>();

                while (rs.next()) {
                    spinnerArray.add(rs.getString(2));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        context, android.R.layout.simple_spinner_item, spinnerArray);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                success = true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                isConnected = false;
            } finally {
                //finally block used to close resources
                try {
                    if (st != null) {
                        st.close();
                    }
                } catch (SQLException se) {
                }// do nothing
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }//end try
        }
        return success;
    }

    public static boolean insertInTables(String[] tablesName, HashMap<String, HashMap<String, Object>> allTables) {
        boolean success = false;

        insert_query = new StringBuilder();

        for (String tableName : tablesName) {
            if (tableName.equals("") || tableName == null)
                continue;
            StringBuilder strKeys = new StringBuilder("(");
            StringBuilder strValues = new StringBuilder("(");
            HashMap<String, Object> tableData = allTables.get(tableName);

            for (Map.Entry<String, Object> entry : tableData.entrySet()) {
                strKeys.append(entry.getKey()).append(",");
                strValues.append('\'').append(entry.getValue()).append('\'').append(",");
            }
            strKeys.deleteCharAt(strKeys.length() - 1);
            strValues.deleteCharAt(strValues.length() - 1);
            strKeys.append(")");
            strValues.append(")");
            insert_query.append("insert into ").append(tableName).append(" ")
                    .append(strKeys).append(" values ").append(strValues).append(";");

        }

        Connect();
        if (isConnected) {
            Log.d("QUERYTAG", insert_query.toString());
            Statement st = null;
            try {
                st = connection.createStatement();

                for (String s : insert_query.toString().split(";"))
                    st.addBatch(s);

                st.executeBatch();
                success = true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if (st != null) {
                        st.close();
                    }
                } catch (SQLException se) {
                }// do nothing
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }//end try

        }
        return success;
    }

    public static Boolean pdrUsernameTest(Context context, String strUsername, String strPassword) {
        ResultSet rs;
        Boolean ActiveUser = false;
        Connect();
        if (isConnected) {
            String strSQLTables = "SELECT * FROM zakatraising.Employees WHERE " +
                    "employeecode = '" + strUsername +
                    "' AND password = '" + strPassword +
                    "' AND activeuser = 1 " +
                    "And  Jobtitle in ('0','1');";
            Statement st = null;
            try {
                st = connection.createStatement();
                rs = st.executeQuery(strSQLTables);
                if (rs.next() == false) {
                    ActiveUser = false;
                    // connection.close();
                } else {
                    ActiveUser = true;
                }
            } catch (SQLException throwables) {
                ActiveUser = false;
                throwables.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if (st != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                }// do nothing
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }//end try

        } else {
            Toast.makeText(context, "لايوجد اتصال بالانترنت", Toast.LENGTH_SHORT).show();
        }
        return ActiveUser;
    }

    public static String getMaxID(String tableName, String Column, String EmployeeCode) {
        ResultSet rs;
        String max = "";
        Connect();
        if (isConnected) {
            String strSQLTables = " SELECT MAX(" + Column + ") FROM " + tableName + " WHERE employeeCode = '" + EmployeeCode + "';";
            Statement st = null;
            try {
                st = connection.createStatement();
                rs = st.executeQuery(strSQLTables);
                if (!rs.next()) {                    //connection.close();
                    try {
                        connection.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                } else {
                    max = rs.getString(1);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if (st != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                }// do nothing
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }//end try
        }
        return max;
    }

    public static String getMaxID1(String tableName, String Column) {
        ResultSet rs;
        String max = "";
        Connect();
        if (isConnected) {
            String strSQLTables = " SELECT MAX(" + Column + ") FROM " + tableName + ";";
            Statement st = null;
            try {
                st = connection.createStatement();
                rs = st.executeQuery(strSQLTables);
                if (!rs.next()) {
                    //connection.close();
                } else {
                    max = rs.getString(1);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if (st != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                }// do nothing
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }//end try
        }
        return max;
    }

    public static boolean saveImage(String tablename, String column, byte[] imgBytes) {
        boolean success = false;
        Connect();
        insert_query = new StringBuilder();
        if (isConnected) {

            insert_query.append("insert into ").append(tablename).append(" ( ")
                    .append(column).append(") values ('").append(Arrays.toString(imgBytes)).append("' );");
            Statement st = null;

            try {
                st = connection.createStatement();
                ResultSet rs = st.executeQuery(insert_query.toString());
                success = true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return success;
    }

    public static boolean insertValue(String tableName, String column, String value) {
        insert_query = new StringBuilder();
        insert_query.append("insert into ").append(tableName).append(" (")
                .append(column).append(") values (").append(value).append(");");
        boolean success = false;
        Statement st = null;
        Connect();
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(insert_query.toString());
            success = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return success;
    }

    public static int getDepartment(String employeeCode) {
        Connect();
        if (isConnected) {
            String query = "select jobtitle from employees where employeeCode = '" + employeeCode + "';";
            Statement st = null;

            try {
                st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                int result = -1;
                if (rs.next())
                    result = rs.getInt(1);
                return result;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                isConnected = false;
            } finally {
                //finally block used to close resources
                try {
                    if (st != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                }// do nothing
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }//end try

        }
        return -1;
    }

    public static Boolean CheckDate(String tableName, String Date) {
        boolean success = false;
        Connect();
        if (isConnected) {
            String checkQuery = "select * from " + tableName + " where AutoDate= '" + Date + "';";
            Statement st = null;
            try {
                st = connection.createStatement();
                ResultSet rs = st.executeQuery(checkQuery);
                String result = "";
                if (rs.next())
                    result = rs.getString(1);
                success = true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                isConnected = false;
            } finally {
                //finally block used to close resources
                try {
                    if (st != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                }// do nothing
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }//end try

        }
        return success;
    }

    public static boolean executeQueries(String queries) {
        boolean success = false;
        Connect();

        Statement st = null;
        try {
            st = connection.createStatement();

            for (String s : queries.split("\n"))
                st.addBatch(s);

            st.executeBatch();
            success = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        return success;
    }

    public static ArrayList<String> getSpinnerItems(String spinnerName) {
        String query = "select * from " + spinnerName;
        ArrayList<String> itemsArray = new ArrayList<>();

        DAL.Connect();
        Statement st = null;

        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                itemsArray.add(rs.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isConnected = false;
        } finally {
            //finally block used to close resources
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        return itemsArray;
    }
}