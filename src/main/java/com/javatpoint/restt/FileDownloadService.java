package com.javatpoint.restt;

import java.io.File;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FilenameUtils;

import com.cli.Client;
import com.cli.EmailSend;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@Path("/files")
public class FileDownloadService {
	@GET
	@Path("/pdf")
	@Produces("application/pdf")
	public Response getFile() {
		String s = null;
		String name = null;
		String type_doc = null;
		String lien = null;
		String tup = null;
		String doc = null;
		String pdf = null;
		String sql = "select * from documents where id_client=? and typ=?";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connUrl = "jdbc:mysql://jws-app-mysql:3306/conversionapp";
			String username = "user";
			String pwd = "password";
			Connection conn = (Connection) DriverManager.getConnection(connUrl, username, pwd);
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1, Client.clients.get(Client.clients.size() - 1).getId_client());
			ps.setString(2, "converted");
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				int a = result.getInt(1);
				name = result.getString(2);
				s = result.getString(3);
			}
			conn.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		doc = FilenameUtils.removeExtension(s) + ".docx";
		pdf = FilenameUtils.removeExtension(s) + ".pdf";

		Thr th1 = new Thr(doc, pdf);
		Thread th = new Thread(th1);
		th.start();

		EmailSend th2 = new EmailSend();
		Thread thh = new Thread(th2);
		thh.start();

		File file = new File(s);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=\"" + s);
		return response.build();

	}

	public class Thr implements Runnable {
		String a, b;

		public Thr(String a, String b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public void run() {
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					try {
						Files.deleteIfExists(Paths.get(a));
						Files.deleteIfExists(Paths.get(b));

					} catch (NoSuchFileException e) {
						System.out.println("No such file/directory exists");
					} catch (DirectoryNotEmptyException e) {
						System.out.println("Directory is not empty.");
					} catch (IOException e) {
						System.out.println("Invalid permissions.");
					}
				}
			}, 200000);
		}

	}

}