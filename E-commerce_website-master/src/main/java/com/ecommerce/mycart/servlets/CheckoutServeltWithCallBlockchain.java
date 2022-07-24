package com.ecommerce.mycart.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecommerce.blockchain.cli.MainBlockchain;

/**
 * Servlet implementation class CheckoutServeltWithCallBlockchain
 */
public class CheckoutServeltWithCallBlockchain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutServeltWithCallBlockchain() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
		
		
		PrintWriter out=response.getWriter();
		
		   out.println("<script type=\"text/javascript\">");
		   response.setContentType("text/html");  
		   out.println("alert('User Checkout Sucessfully and the amount is go send for blockchain module for processing !.....');");  
		   out.println("</script>");
		   
		   //actually this is come from javascript function
		String totalPrice=request.getParameter("totalPrice2");
		
		System.out.println("totalPrice is "+totalPrice);
		
		// i have pass statically pass and blockchain with block , wallet all apply
		String totalPrice2="5";
		try{
		//MainBlockchain mbc=new MainBlockchain();
		MainBlockchain.mainMethodOfBlockchain(totalPrice2);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

}
