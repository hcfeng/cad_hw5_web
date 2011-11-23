package tw.dev.hcfeng.hw5.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
		response.setContentType("text/html;charset=utf-8");		
		PrintWriter out = response.getWriter();
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
				getClass().getClassLoader().getResourceAsStream(
						"AwsCredentials.properties")));
		
		ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
        .withBucketName("cad-hw5")
        .withPrefix("Process_"));
		out.println("<html><title>cad_hw5</title><body>");		
		out.println("<table style=\"background: blue;\" cellspacing=\"1\" cellpadding=\"1\">");	
		out.println("<tr><th>下載</th><th style=\"background:gray; width: 300px; color:white;\">Log</th><th style=\"background:gray; width:250px;  color:white;\">Date</th><th style=\"background:gray;width: 100px;  color:white;\">Size</th></tr>") ;				
		 for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
			 out.println("<tr><td><a href=\"\" target=\"_blank\"></a></td><td style=\"background:white; text-align: center;\">"+objectSummary.getKey()+"</td><td style=\"background:white; text-align: center;\">"+objectSummary.getLastModified()+"</td><td style=\"background:white; text-align: center;\">"+objectSummary.getSize()+" Bytes</td></tr>") ;                      
         }
		out.println("</table>");
		out.println("</body></html>");		
	}

}
