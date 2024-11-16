<%@page import="com.fashionshop.entities.Message"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
Message messg = (Message) session.getAttribute("message");
if (messg != null) {
%>
<div class="alert <%=messg.getCssClass()%>" role="alert" id="alert">
	<%=messg.getMessage()%>
</div>
<%
session.removeAttribute("message");
}
%>
<script type="text/javascript">
	setTimeout(function() {
		$('#alert').alert('close');
	}, 3000);
</script>