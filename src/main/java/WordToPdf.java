
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.cli.Client;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@WebServlet("/WordToPdf")
public class WordToPdf extends HttpServlet {
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
			ps.setString(2, "docx");
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				s = result.getString("path_doc");
				name = result.getString("name_doc");
			}
			String lien = "/deployments/ROOT/" + name + ".pdf";

			String ext = FilenameUtils.getExtension(s);
			String output = "";

			if ("docx".equalsIgnoreCase(ext)) {
				output = readDocxFile(s);
			} else if ("doc".equalsIgnoreCase(ext)) {
				output = readDocFile(s);
			} else {
				System.out.println("INVALID FILE TYPE. ONLY .doc and .docx are permitted.");
			}
			conn.close();

			writePdfFile(output, lien);
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
			pss.setString(4, "pdf");
			pss.setString(5, "converted");
			@SuppressWarnings("unused")
			int st = pss.executeUpdate();
			connc.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String readDocFile(String fileName) {
		String output = "";
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			HWPFDocument doc = new HWPFDocument(fis);
			WordExtractor we = new WordExtractor(doc);
			String[] paragraphs = we.getParagraphText();
			for (String para : paragraphs) {
				output = output + "\n" + para.toString() + "\n";
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	public static String readDocxFile(String fileName) {
		String output = "";
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			XWPFDocument document = new XWPFDocument(fis);
			List<XWPFParagraph> paragraphs = document.getParagraphs();
			for (XWPFParagraph para : paragraphs) {
				output = output + "\n" + para.getText() + "\n";
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	public static void writePdfFile(String output, String ut) throws FileNotFoundException, DocumentException {
		File file = new File(ut);
		FileOutputStream fileout = new FileOutputStream(file);
		Document document = new Document();
		PdfWriter.getInstance(document, fileout);
		document.open();
		String[] splitter = output.split("\\n");
		for (int i = 0; i < splitter.length; i++) {
			Chunk chunk = new Chunk(splitter[i]);
			Font font = new Font();
			font.setStyle(Font.UNDERLINE);
			font.setStyle(Font.ITALIC);
			chunk.setFont(font);
			document.add(chunk);
			Paragraph paragraph = new Paragraph();
			paragraph.add("");
			document.add(paragraph);
		}
		document.close();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
