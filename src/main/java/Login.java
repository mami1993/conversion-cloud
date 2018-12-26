import com.cli.*;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String dbEmail = null;
			String dbPass = null;
			int id = 0;
			String sql = "select * from client where email_client=? and password=?";
			Class.forName("com.mysql.jdbc.Driver");
			String connUrl = "jdbc:mysql://jws-app-mysql:3306/conversionapp";
			String username = "user";
			String pwd = "password";
			Connection conn = (Connection) DriverManager.getConnection(connUrl, username, pwd);
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				dbEmail = result.getString(3);
				dbPass = result.getString("password");
				id = result.getInt("id_client");
			}
			if (email.equals(dbEmail) && password.equals(dbPass)) {
				Information inf = new Information(id, dbEmail);
				Client.clients.add(inf);
				RequestDispatcher rd = request.getRequestDispatcher("mainapp.jsp");
				rd.include(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.include(request, response);
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
