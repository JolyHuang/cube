package com.sharingif.cube.web.springmvc.servlet.view.stream;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

/**
 * 
 * @Description: [Stream视图]
 * @Author: [Joly]
 * @CreateDate: [2014年5月30日 上午10:53:48]
 * @UpdateUser: [Joly]
 * @UpdateDate: [2014年5月30日 上午10:53:48]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 * 
 */
public class StreamView extends AbstractView {
	
	@Override
	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		super.prepareResponse(request, response);
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType(getContentType());
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		Object content = model.get(StreamModelAndView.CONTENT);

		Object fileName = model.get(StreamModelAndView.FILE_NAME);
		if(fileName != null) {
			response.setHeader("Content-Disposition", "attachment; filename="+(fileName));
		}
		
		if (content instanceof String) {
			PrintWriter printwriter = response.getWriter();
			printwriter.print(content);
			printwriter.flush();
		} else if (content instanceof byte[]) {
			javax.servlet.ServletOutputStream servletoutputstream = response.getOutputStream();
			servletoutputstream.write((byte[]) content);
			servletoutputstream.flush();
		}
		
	}

}
