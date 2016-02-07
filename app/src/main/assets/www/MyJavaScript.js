function submit()
{
	var username = document.getElementById('t1').value;
	var password = document.getElementById('t2').value;


	{
//		if(confirm("login successfully"))
//		window.location.href = "welcome.html";
		Android.showToast(username , password);
	}
//	else
//		alert("invalid user");
}