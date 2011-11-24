package tw.dev.hcfeng.hw5.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * Servlet implementation class ListLogs
 */
public class ListLogs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListLogs() {
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
		response.setContentType("text/html;charset=utf-8");		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));	
		PrintWriter out = response.getWriter();
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
				getClass().getClassLoader().getResourceAsStream(
						"AwsCredentials.properties")));
		
		ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
        .withBucketName("cad-hw5")
        .withPrefix("Process_"));
		out.println("<html><head><title>cad_hw5</title></head><body>");		
		out.println("<table style=\"background: blue;\" cellspacing=\"1\" cellpadding=\"1\">");	
		out.println("<tr><th style=\"background:gray; width: 50px; color:white;\">下載</th><th style=\"background:gray; width: 300px; color:white;\">Log</th><th style=\"background:gray; width:250px;  color:white;\">Date</th><th style=\"background:gray;width: 100px;  color:white;\">Size</th></tr>") ;				
		 for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
			 out.println("<tr><td style=\"background:white; text-align: center;\"><a href=\"ViewLog?key="+objectSummary.getKey()+"\" target=\"_blank\"><img border=\"0\" src=\"images/drive.png\" /></a></td><td style=\"background:white; text-align: center;\">"+objectSummary.getKey()+"</td><td style=\"background:white; text-align: center;\">"+sdf.format(objectSummary.getLastModified())+"</td><td style=\"background:white; text-align: center;\">"+objectSummary.getSize()+" Bytes</td></tr>") ;                      
         }
		out.println("</table>");
		out.println("</body></html>");		
	}

}
