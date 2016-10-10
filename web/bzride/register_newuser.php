<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>BZ Ride</title>
<link href="bzride_style.css" type="text/css" rel="stylesheet" />
</head>
<body>

 <?php include("header.php"); ?> 
  
  <div class="div_center">
<!--------------------------------------------------------------------------------------------------------------->
    <table border="2" >
<caption> <h3>REGISTRATION</h3> </caption>
<form action="register_newuser_act.php" method="post" enctype="multipart/form-data">

 <tr>
<td> First Name </td>
<td> <input type="text" name="txtfirstname" id="txtfirstname" placeholder="First name" />
<br />
</td>
 </tr>
 
 <tr>
 <td> Middle Name </td>
<td> <input type="text" name="txtmiddlename" id="txtmiddlename"  placeholder="Middle name"/>
<br />
</td>
 </tr>
 
 <tr>
<td> Last Name </td>
<td> <input type="text" name="txtlastname" id="txtlastname" placeholder="Last name" />
<br />
</td>
 </tr>
 
  <tr>
<td> Email ID </td>
<td> <input type="text" name="txtemail"id="txtemail" placeholder="email id"/>
<br />
</td>
 </tr>

<tr>
<td> Password </td>
<td> <input type="password" name="txtpass"id="txtpass" placeholder="Password"/>
<br />
</td>
 </tr>

 <tr>
<td> Confirm Password </td>
<td> <input type="password" name="txtconpass"id="txtconpass" placeholder="Confirm password" />
<br />
</td>
 </tr>
 <!---------------------------------------------------------------------------------------->
  <tr>
<td> Address 1 </td>
<td> <textarea rows="4" cols="50" name="txtaddr1" id="txtaddr1" placeholder="Address1" >
</textarea>
<br />
</td>
 </tr>
 
   <tr>
<td> Address 2 </td>
<td> <textarea rows="4" cols="50" name="txtaddr2" id="txtaddr2" placeholder="Address2" ></textarea>
<br />
</td>
 </tr>

   <tr>
<td> City </td>
<td> <input type="text" name="txtcity"id="txtcity"  placeholder="City" />
<br />
</td>
 </tr>
 
  <tr>
<td> State </td>
<td> <input type="text" name="txtstate"id="txtstate"  placeholder="State" />
<br />
</td>
 </tr>
 
   <tr>
<td> Zip </td>
<td> <input type="text" name="txtzip"id="txtzip"  placeholder="Zip" />
<br />
</td>
 </tr>
  <tr>
<td> Mobile No: </td>
<td> <input type="text" name="txtphone"id="txtphone" placeholder="mobile phone" />
<br />
</td>
 </tr>
 
 <!---------------------------------------------------------------------------------------------->
  <tr><td></td><td><b> CARD DETAILS</td></tr></b>
  <tr>
<td> Card Type </td>
<td> <input type="radio" name="cardtype" value="C" id="r1" checked />Credit Card
     <input type="radio" name="cardtype" value="D" id="r2" />Debit Card
<br />
</td>
 </tr>
 
   <tr>
<td> Sub Type </td>
<td> <input type="radio" name="subtype" value="M" id="r3" checked />Master Card
     <input type="radio" name="subtype" value="V" id="r4" />Visa Card
	 <input type="radio" name="subtype" value="A" id="r5" />Amex Card
<br />
</td>
 </tr>

  <tr>
<td> Card No: </td>
<td> <input type="text" name="txtcardno"id="txtcardno" placeholder="card number"  />
<br />
</td>
 </tr>

  <tr>
<td> Expiry month and year </td>
<td> <input type="text" name="txtexpirydate"id="txtexpirydate" placeholder="mm/yy"  />
<br />
</td>
 </tr>

<tr>
<td>Billing Address 1 </td>
<td> <textarea rows="4" cols="50" name="txtbillingaddr1" id="txtbillingaddr1" placeholder="Billing Address1" ></textarea>
<br />
</td>
 </tr>
 
<tr>
<td> Billing Address 1 </td>
<td> <textarea rows="4" cols="50" name="txtbillingaddr2" id="txtbillingaddr2" placeholder="Billing Address2" ></textarea>
<br />
</td>
 </tr> 
 
  <tr>
<td>CVV </td>
<td> <input type="password" name="txtcvv"id="txtcvv"  placeholder="CVV" /> 
<br />
</td>
 </tr> 

  <tr> 
<!--<td> Licence Accepted </td>
<td> <select name="licence"id="licence" >
<option value="0" selected="selected">Select</option>
<option value="True">True</option>
<option value="False">False</option>
</optgroup>
</select>-->

<br />
</td>
 </tr>

 <tr>
 <th></th>
 <td><input type="SUBMIT" value="submit" onclick="return disp()"/>
 </td>
 </tr>
 
 </form>
 </table>

<!--------------------------------------------------------------------------------------------------------------->    
 <?php include("registermenu.php"); ?> 
    
  </div>

  <!--<div class="div_right">
    <div class="latest_blog">
      <h3> Latest News</h3>
      <div class="div_blog_top">
        <div class="blog_image"></div>
        <div class="blog_heading"> Hi all</div>
      </div>
      <div class="blog_description">
        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry.... </p>
      </div>
    </div>
    <div class="latest_blog">
      <h3>Lattest Blog</h3>
      <div class="blog_image"></div>
      <div class="blog_heading"> new blog</div>
      <div class="blog_description">
        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. </p>
      </div>
    </div>
  </div>-->
</div>
 <?php include("footer.php"); ?> 
</body>
</html>
