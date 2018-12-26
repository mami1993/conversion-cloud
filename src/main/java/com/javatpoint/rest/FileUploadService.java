package com.javatpoint.rest;

import com.cli.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@Path("/files")
public class FileUploadService {
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String fileLocation = "/deployments/ROOT/" + fileDetail.getFileName();

		// saving file
		try {
			FileOutputStream out = new FileOutputStream(new File(fileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(fileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Connection conn = null;
		try {
			String pathname = fileLocation;
			String name = FilenameUtils.removeExtension(fileDetail.getFileName());
			String type = FilenameUtils.getExtension(fileDetail.getFileName());
			Class.forName("com.mysql.jdbc.Driver");
			String connUrl = "jdbc:mysql://jws-app-mysql:3306/conversionapp";
			String username = "user";
			String pwd = "password";
			conn = (Connection) DriverManager.getConnection(connUrl, username, pwd);
			String sql = " INSERT INTO documents(name_doc,path_doc,id_client,type_doc,typ) VALUES (?,?,?,?,?) ";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, pathname);
			ps.setInt(3, Client.clients.get(Client.clients.size() - 1).getId_client());
			ps.setString(4, type);
			ps.setString(5, "toconvert");
			@SuppressWarnings("unused")
			int st = ps.executeUpdate();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}