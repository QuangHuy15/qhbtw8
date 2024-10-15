package vniot.star.controllers.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vniot.star.ultils.Constant;

@WebServlet(urlPatterns = {"/video"})
public class DownloadVideoController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fname"); // Lấy tên file từ request
        File file = new File(Constant.DIR + "/" + fileName); // Tạo đối tượng file

        resp.setContentType("video/mp4"); 

        if (file.exists()) {
            IOUtils.copy(new FileInputStream(file), resp.getOutputStream());
        } else {
            // Nếu không tìm thấy file
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
        }
	}
}
