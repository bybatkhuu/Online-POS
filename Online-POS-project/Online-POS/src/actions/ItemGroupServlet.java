package actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import models.Item;

import utils.LoggedUser;

@WebServlet("/item-group")
public class ItemGroupServlet extends HttpServlet{

private static final long serialVersionUID = 1L;
    
	public ItemGroupServlet()
	{
        super();
    }

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		int status = LoggedUser.checkLogin(session);
		if (status != 1)
		{
			response.sendRedirect("login.jsp");
		}
		List<Item> itemGroupList = new ArrayList<>();
		synchronized(session)
	    {
			List<Item> itemList = (List<Item>) session.getAttribute("itemList");
		    if (itemList == null)
		    {
		    	itemList = new ArrayList<Item>();
		    }
		    int i = 0;
		    while(itemList.size() !=i){
		    	Item item = itemList.get(i);
		    	if(itemGroupList.isEmpty()){
		    		itemGroupList.add(new Item(item));
		    	}else{
	    			Item it =findItem(item ,itemGroupList);
	    			if(it != null){
	    				it.addQuantity(item.getQuantity());
	    			}else{
	    				itemGroupList.add(new Item(item));
	    			}
		    	}
		    	i++;
		    }
		    session.setAttribute("itemGroupList", itemGroupList);
	    }
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	private Item findItem(Item item,List<Item> list){
		for(Item it:list){
			if(it.getPrice() == item.getPrice() && item.getFactureCode().equals(it.getFactureCode())){
				return it;
			}
		}
		return null;
	}
}
