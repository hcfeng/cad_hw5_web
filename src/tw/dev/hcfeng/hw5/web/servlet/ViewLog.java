package tw.dev.hcfeng.hw5.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;

/**
 * Servlet implementation class ViewLog
 */
public class ViewLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewLog() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	private void doRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain;charset=utf-8");		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));	
		String key = request.getParameter("key");
		PrintWriter out = response.getWriter();
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
				getClass().getClassLoader().getResourceAsStream(
						"AwsCredentials.properties")));			
		S3Object s3Object = s3.getObject("cad-hw5", key);
		InputStream is = s3Object.getObjectContent();
		try {			
			char[] buffer = new char[1024];
			Reader reader = new BufferedReader(new InputStreamReader(is , "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {				
				out.write(buffer, 0, n);				
			}
		} finally{
			is.close();
		}
		
	}

}
