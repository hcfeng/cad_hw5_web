package tw.dev.hcfeng.hw5.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

/**
 * Servlet implementation class SendQueue
 */
public class SendQueue extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendQueue() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	private void doRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String numbers = request.getParameter("numbers");
		PrintWriter out = response.getWriter();

		// 定義SQS
		AmazonSQS sqs = new AmazonSQSClient(new PropertiesCredentials(
				getClass().getClassLoader().getResourceAsStream(
						"AwsCredentials.properties")));

		// 寫入訊息
		SendMessageRequest smRequest = new SendMessageRequest(
				"https://queue.amazonaws.com/792286945666/cad_hw5_inbox",
				numbers);
		SendMessageResult smResult = sqs.sendMessage(smRequest);

		// 接收訊息
		ReceiveMessageRequest rmRequest = new ReceiveMessageRequest(
				"https://queue.amazonaws.com/792286945666/cad_hw5_outbox")
				.withMaxNumberOfMessages(1);

		String resultStr = "";		
		while (true) {
			ReceiveMessageResult rmResult = sqs.receiveMessage(rmRequest);
			if (rmResult.getMessages().size() > 0) {
				for (Message mesg : rmResult.getMessages()) {
					resultStr += mesg.getBody()+"<br/>";					
				}
				// 刪除訊息
				for (Message mesg : rmResult.getMessages()) {
					DeleteMessageRequest dmRequest = new DeleteMessageRequest(
							rmRequest.getQueueUrl(), mesg.getReceiptHandle());
					sqs.deleteMessage(dmRequest);
				}	
				break;
			} else {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
			}			
		}	
		response.sendRedirect("index.jsp?showmsg="+resultStr);
	}

}
