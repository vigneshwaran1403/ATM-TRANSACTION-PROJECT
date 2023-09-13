package ATMPROJECT;
import java.sql.*;
import java.util.*;
public class atmProcess {

	public static void main(String[] args) {
	try
	{
		Scanner s=new Scanner(System.in);
		Connection con =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","vignesh1","1824");
		Statement st=con.createStatement();
		String queryid="select userid from atmproject1";
		ResultSet rs=st.executeQuery(queryid);
		String idgrant="";
		System.out.println("................WELCOME................");
		System.out.println("DO YOU HAVE ACCOUNT ..YES.. TO CONTINUE ..NO..TO CREATE ACCOUNT ");
		String answer=s.next();
		if("yes".equals(answer))
		{
		System.out.println("ENTER THE USERID");
		String id=s.next();
		while(rs.next())
		{
			if(rs.getString(1).equals(id))
			{
			  idgrant="grant";
			  break;
			}
		}
		if("grant".equals(idgrant))
		{
			System.out.println("ENTER THE PIN NUMBER");
			String pin=s.next();
			String querypin="select pin from atmproject1 where userid="+id;
			ResultSet pincheck=st.executeQuery(querypin);
			pincheck.next();
			String pinchecked=pincheck.getString(1);
			if(pin.equals(pinchecked))
			{
				String username="select name from atmproject1 where userid="+id;
		    	ResultSet un1=st.executeQuery(username);
		    	un1.next();
		    	String name=un1.getString(1);
		    	System.out.println("................welcome "+ name +"...................");
				boolean bool=true;
				while(bool)
				{
		    System.out.println("1 to Withdrawal");
		    System.out.println("2 to Bank balance");
		    System.out.println("3 to Deposit the Amount");
		    System.out.println("4 to Transaction History");
		    System.out.println("5 to Exit");
		    System.out.println("6 to Change The Password");
		    System.out.println("Enter The Number");
		    int num=s.nextInt();
		    
		    switch(num)
		    {
		    case 1:
		     {
		    	 String queryamount="select totalamount from atmproject1 where pin="+pin ;
		    	 ResultSet amount=st.executeQuery(queryamount);
		    	 amount.next();
		    	 long TotalAmount=amount.getLong(1);
		    	 while(TotalAmount>=0)
		    	 {
		    		 System.out.println("You want to withdrawal the amount ");
				        System.out.println("YES to withdrawal or NO to exit"); 
				        s.nextLine();
				        String str=s.nextLine();
				        if("yes".equals(str))
				        {
				        	System.out.println("Enter the withdrawal amount");
				        	long cash=s.nextLong();
				        	if(cash<=TotalAmount)
				        	{
				        		if(cash>0)
				        		{
				        			
				        		
				        	System.out.println(cash + "  withdrawal successfully  ");
				        	TotalAmount-=cash;
//				        	 System.out.println(" balance amount is " + TotalAmount);
				        	 String updateBalance="update atmproject1 set totalamount="+TotalAmount+"where pin="+pin;
				        	 PreparedStatement pst=con.prepareStatement(updateBalance);
				        	 pst.executeUpdate();
				        	 String his="insert into atmtransactionhistory (id,withdrawal,date_time)values (?,?,?)";
			                 String one=id;
			                 PreparedStatement pst1=con.prepareStatement(his);
			                  long two=cash;
			                 String time="select systimestamp from dual";
			                ResultSet amount2=st.executeQuery(time);
			                 amount2.next();
			                 String four= amount2.getString(1);
			                 pst1.setString(1,one);
			                 pst1.setString(2, "+"+two);
			                 pst1.setString(3,four);
			                 pst1.executeUpdate();
				             }
				        	
				        		else
				        		{
				        			System.out.println("ENTER THE AMOUNT GREATER THAN ZERO");
				        		}
				        	}
				        	else
				        	{
				        		System.out.println("INSUFFICIENT BALANCE");
				        		
				        	}
				        	
				        	
		    }
				        else {
				        	System.out.println("Transaction cancel");
				        }
				       break; 
			}
		    	 break;
		}
		    case 2:{
		    	String queryamount="select totalamount from atmproject1 where pin="+pin ;
		    	 ResultSet amount=st.executeQuery(queryamount);
		    	 amount.next();
		    	 long TotalAmount=amount.getLong(1);
		    	 System.out.println("your current balance is:"+TotalAmount ); 
		    	 break;
		    }
		    case 3:
		    {
		    	System.out.println("Enter the Deposit amount");
		 		long deposite=s.nextLong();
		 		if(deposite>0)
		 		{
		 		String queryamount="select totalamount from atmproject1 where pin="+pin ;
		    	 ResultSet amount=st.executeQuery(queryamount);
		    	 amount.next();
		    	 long TotalAmount=amount.getLong(1);
		    	 long depositeamount=TotalAmount+deposite;
		 		String depositequery="update atmproject1 set totalamount="+depositeamount+"where userid="+id;
		 		PreparedStatement pst=con.prepareStatement(depositequery);
		 		  pst.executeUpdate();
		 		System.out.println("successfully Deposit the Amount");
                 String his="insert into atmtransactionhistory (id,deposite,date_time)values (?,?,?)";
                 String one=id;
                 PreparedStatement pst1=con.prepareStatement(his);
                  long two=deposite;
                 String time="select systimestamp from dual";
                ResultSet amount2=st.executeQuery(time);
                 amount2.next();
                 String four= amount2.getString(1);
                 pst1.setString(1,one);
                 pst1.setString(2, "+"+two);
                 pst1.setString(3,four);
                 pst1.executeUpdate();
		 		}
		 		else
		 		{
		 			System.out.println("!......ENTER THE AMOUNT GREATER THAN ZERO.....!");
		 		}
		    }
		    break;
		    case 4:
		    {
		    	String history="select * from atmtransactionhistory where id="+id;
		    	ResultSet result=st.executeQuery(history);
		    	int count=1;
		    	while(result.next())
		    	{
		    		String userid=result.getString(1);
		    		String credit=result.getString(2);
		    		String with=result.getString(3);
		    		String date=result.getString(4);
		    		if(id.equals(userid))
		    		{
		    			System.out.println(count+". "+" USERID : "+userid+" , "+" CREDIT STATUS : "+credit+" , "+ " WITHDRAWAL STATUS : "+with+" , "+" DATE AND TIME : "+date);
		    			count++;
		    		}
		    		else
		    		{
		    			System.out.println("...............NO TRANSACTION FOUND................");
		    		}
		    	}
		    }
		    break;
		    case 5:
		    {
		    	bool=false;
		    	String username1="select name from atmproject1 where userid="+id;
		    	ResultSet un=st.executeQuery(username1);
		    	un.next();
		    	String name1=un.getString(1);
		    	System.out.println("thank you " +name1);
		    }
		    break;
		    case 6:
		    {
		    	String mobile ="select mobile from atmproject1 where userid="+id;
		    	ResultSet mobilenocheck=st.executeQuery(mobile);
		    	mobilenocheck.next();
		    	String nocheck=mobilenocheck.getString(1);
		    	System.out.println("ENTER THE MOBILE NUMBER");
		    	String number=s.next();
		    	if(number.equals(nocheck))
		    	{
		    		System.out.println("ENTER THE OLD PIN NUMBER");
		    		String oldpin=s.next();
		    		if(oldpin.equals(pin))
		    		{
		    			System.out.println("ENTER THE NEW PIN NUMBER");
		    			String newpin=s.next();
		    			
		    			String pinupdate="update atmproject1 set pin="+newpin+"where userid="+id;
		    			PreparedStatement pst=con.prepareStatement(pinupdate);
		             	pst.executeUpdate();
		             	System.out.println("NEW PIN NUMBER IS UPDATED");
		    		}
		    	}
		    	else {
		    		System.out.println("INVALID MOBILE NUMBER");
		    	}
		    }
		   }
		 }
    }
			else
			{
				System.out.println("INVALID PIN NUMBER");
			
			}
		}
		else
		{
			System.out.println("YOUR USERID IS INVALID CHECK THE USERID");
		}
		}
		else
		{
        String add="insert into atmproject1 (userid,name,mobile,pin) values (?,?,?,?) ";
      	PreparedStatement st1=con.prepareStatement(add);
      	System.out.println("ENTER THE NAME");
      	String name=s.next();
      	System.out.println("ENTER THE MOBILE");
      	String mobileno=s.next();
      	System.out.println("ENTER THE PIN");
      	String pinno=s.next();
      	String count="select max(userid) from atmproject1";
      	ResultSet c1=st.executeQuery(count);
      	c1.next();
      	int maxvalue=c1.getInt(1)+1;
      	st1.setInt(1, maxvalue);
      	st1.setString(2, name);
      	st1.setString(3, mobileno);
      	st1.setString(4, pinno);
      	st1.executeUpdate();
      	
      	
      	System.out.println("..................YOUR ACCOUNT IS CREATED...........");
      	System.out.println();
      	System.out.println("..................THANK YOU FOR CREATING ACOUNT "+name+" ...........");
	}
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
	

}
}