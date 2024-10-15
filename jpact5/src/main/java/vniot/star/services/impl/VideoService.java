package vniot.star.services.impl;

import java.util.List;

import vniot.star.dao.IVideoDao;
import vniot.star.dao.impl.VideoDao;
import vniot.star.entity.Video;
import vniot.star.services.IVideoService;

public class VideoService implements IVideoService{

	IVideoDao vidDao = new VideoDao();
	
	@Override
	public int count() {
		return vidDao.count();
	}

	@Override
	public List<Video> findAll(int page, int pageSize) {
		return vidDao.findAll(page, pageSize);
	}

	@Override
	public List<Video> findByTitle(String title) {
		return vidDao.findByTitle(title);
	}

	@Override
	public List<Video> findAll() {
		return vidDao.findAll();
	}

	@Override
	public Video findById(String videoid) {
		return vidDao.findById(videoid);
	}

	@Override
	public void delete(String videoid) throws Exception {
		vidDao.delete(videoid);
	}

	@Override
	public void update(Video video) {
		vidDao.update(video);
	}

	@Override
	public void insert(Video video) {
		vidDao.insert(video);
	}

}
