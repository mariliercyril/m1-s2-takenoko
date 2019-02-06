<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC *-//W3C//DTD HTML 4.01 TRANSITIONAL//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="txt/html; charset=ISO-8859-1">
		<title>Welcome to ComeRead</title>
		<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
	</head>
	<body>
		<p>Welcome to ComeRead</p>
		<a href="/users">All users</a>
		<a href="/articles">All articles</a>
		<p>Create new User:</p>
		User Id:<br>
		<input type="text" name="uid" id="uid"><br>
		User Name:<br>
		<input type="text" name="username" id="username"><br>
		User Email:<br>
		<input type="text" name="email" id="email"><br>
		User Password:<br>
		<input type="text" name="password" id="password"><br>
		User Quote:<br>
		<input type="text" name="quote" id="quote">
		<button type="submit" value="submit" id="submit">OK</button>

		<script type="text/javascript">
			/*<%@ page language="java" contentType="text/html;charset=ISO-8859-1" pageEncoding="ISO8859-1"%>*/
			jQuery(document).ready(function($) {
				$("#submit").click(function(){
					var userData = {};
					userData["uid"] = $("#uid").val();
					userData["username"] = $("#username").val();
					userData["email"] = $("#email").val();
					userData["password"] = $("#password").val();
					userData["quote"] = $("#quote").val();
			
					$.ajax({
						type : "POST",
						contentType : "application/json",
						url : "users",
						data : JSON.stringify(userData),
						dataType : 'json',
						success : function(data) {
							$('#processedData').html(JSON.stringify(data));
							$('#displayDiv').show();
						}
					});
				});
			});
		</script>
	</body>
</html>
