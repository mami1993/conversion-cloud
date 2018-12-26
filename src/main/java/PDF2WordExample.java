
import java.io.FileOutputStream;
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
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import com.cli.Client;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@WebServlet("/PDF2WordExample")
public class PDF2WordExample extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String s = null;
		String name = null;
		String sql = "select * from documents where id_client=? and type_doc=?";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connUrl = "jdbc:mysql://jws-app-mysql:3306/conversionapp";
			String username = "user";
			String pwd = "password";
			Connection conn = (Connection) DriverManager.getConnection(connUrl, username, pwd);
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1, Client.clients.get(Client.clients.size() - 1).getId_client());
			ps.setString(2, "pdf");
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				s = result.getString("path_doc");
				name = result.getString("name_doc");
			}
			conn.close();
			String lien = "/deployments/ROOT/" + name + ".docx";
			generateDocFromPDF(s, lien);
			RequestDispatcher rd = request.getRequestDispatcher("downloadp.jsp");
			request.setAttribute("message",
					"<H3>WARNING : the file will be deleted after 2 min!</H3><br><H4>we sent an email to your acount</H4><br> <a href=\"restt/files/pdf\">Click Here To Download Your File</a>");
			rd.include(request, response);

			Class.forName("com.mysql.jdbc.Driver");
			Connection connc = (Connection) DriverManager.getConnection(connUrl, username, pwd);
			String sqll = " INSERT INTO documents(name_doc,path_doc,id_client,type_doc,typ) VALUES (?,?,?,?,?) ";
			PreparedStatement pss = (PreparedStatement) connc.prepareStatement(sqll);
			pss.setString(1, name);
			pss.setString(2, lien);
			pss.setInt(3, Client.clients.get(Client.clients.size() - 1).getId_client());
			pss.setString(4, "doc");
			pss.setString(5, "converted");
			@SuppressWarnings("unused")
			int st = pss.executeUpdate();
			connc.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void generateDocFromPDF(String filename, String outt) throws IOException {
		XWPFDocument doc = new XWPFDocument();
		String pdf = filename;
		PdfReader reader = new PdfReader(pdf);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			TextExtractionStrategy strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
			String text = strategy.getResultantText();
			XWPFParagraph p = doc.createParagraph();
			XWPFRun run = p.createRun();
			run.setText(text);
			run.addBreak(BreakType.PAGE);
		}
		FileOutputStream out = new FileOutputStream(outt);
		doc.write(out);
		out.close();
		reader.close();
		doc.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
