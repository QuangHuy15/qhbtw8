package vniot.star.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vniot.star.entity.Category;
import vniot.star.entity.Video;
import vniot.star.services.ICategoryService;
import vniot.star.services.IVideoService;
import vniot.star.services.impl.CategoryService;
import vniot.star.services.impl.VideoService;
import static vniot.star.ultils.Constant.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
	maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)

@WebServlet(urlPatterns = {"/admin/videos","/admin/video/add",
		"/admin/video/insert", "/admin/video/edit", "/admin/video/update",
		"/admin/video/delete", "/admin/video/search"})

public class VideoController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public IVideoService vidService = new VideoService();
	public ICategoryService cateService = new CategoryService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		if (url.contains("/admin/videos")) {
			List<Video> list = vidService.findAll();
			req.setAttribute("listvideo", list);
			req.getRequestDispatcher("/views/admin/video-list.jsp").forward(req, resp);
			
		} else if (url.contains("/admin/video/add")) {
			req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);
			
		} else if (url.contains("/admin/video/edit")) {
			String id = req.getParameter("id");
			Video video = vidService.findById(id);
			req.setAttribute("video", video);
			req.getRequestDispatcher("/views/admin/video-edit.jsp").forward(req, resp);
			
		} else if (url.contains("/admin/video/delete")) {
			String id = req.getParameter("id");
			try {
				vidService.delete(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		

		if (url.contains("/admin/video/insert")) {
			Video video = new Video();
			Category category = new Category();
			
			String videoid = req.getParameter("videoid");
			String title = req.getParameter("title");
			String description = req.getParameter("description");
			String categoryname	= req.getParameter("categoryname");
			int status = Integer.parseInt(req.getParameter("status"));
			int views = Integer.parseInt(req.getParameter("views"));

			video.setVideoid(videoid);
			video.setTitle(title);
			video.setDescription(description);
			video.setStatus(status);
			video.setViews(views);	
			category.setCategoryname(categoryname);

			String videos = req.getParameter("videos");
			String fname = "";
			String uploadPath = UPLOAD_DIRECTORY;
			File uploadDir = new File(uploadPath);
			
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("videos");
				if (part.getSize() > 0) {
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					int index = filename.lastIndexOf(".");
					String ext = filename.substring(index + 1);
					fname = System.currentTimeMillis() + "." + ext;
					part.write(uploadPath + "/" + fname);  // Upload file
					video.setVideos(fname);  // Lưu tên file vào cơ sở dữ liệu
				} else if (videos != null) {
					video.setVideos(videos);
				} else {
					video.setVideos("default.mp4");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			vidService.insert(video);  
			resp.sendRedirect(req.getContextPath() + "/admin/videos");

		} else if (url.contains("/admin/video/update")) {
			
			Category category = new Category();
			
			String videoid = req.getParameter("videoid");
			String title = req.getParameter("title");
			String description = req.getParameter("description");
			int status = Integer.parseInt(req.getParameter("status"));
			int views = Integer.parseInt(req.getParameter("views"));
//			int categoryid = Integer.parseInt(req.getParameter("categoryid"));
			String videos = req.getParameter("videos");
			String categoryname = req.getParameter("categoryname");

			Video video = vidService.findById(videoid);
			video.setVideoid(videoid);
			video.setTitle(title);
			video.setDescription(description);
			video.setStatus(status);
			video.setViews(views);
			category.setCategoryname(categoryname);
			
		
//			if (category != null) {
//			    video.setCategory(category);
//			} else {
//			    // Xử lý khi không tìm thấy danh mục
//			    System.out.println("Category không tồn tại với ID: " + categoryid);
//			}

			// Lưu poster cũ
			Video oldVideo = vidService.findById(videoid);
			String fileold = oldVideo.getVideos();

			// Xử lý file poster mới
			String fname = "";
			String uploadPath = UPLOAD_DIRECTORY;
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("videos");
				
				if (part.getSize() > 0) {
					
					if (video.getVideos() != null && !video.getVideos().substring(0, 5).equalsIgnoreCase("https")) {
						deleteFile(uploadPath + "\\" + fileold);  // Xóa file cũ
					}
					
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					int index = filename.lastIndexOf(".");
					String ext = filename.substring(index + 1);
					fname = System.currentTimeMillis() + "." + ext;
					part.write(uploadPath + "/" + fname);
					video.setVideos(fname);  // Lưu file mới
					
				} else if (videos != null){
					video.setVideos(videos);
				} 
				
				else {
					video.setVideos(fileold);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			vidService.update(video);  // Cập nhật thông tin video trong cơ sở dữ liệu
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		}
	}

		private void deleteFile(String filePath) throws IOException {

		Path path = Paths.get(filePath);
		
		Files.delete(path);
		
	}

}
