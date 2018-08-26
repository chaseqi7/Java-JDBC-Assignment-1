<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/A1.css" /> 
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> 
	</head>	
	<body>
		<jsp:useBean id="geoDataBean" class="prog3060.jwong.GeoDataBean" />
		<%-- Begin Geo --%>
		<div class="page-container">
			<a name="Geo"></a>
			<h1>Geographic Area Classification List</h1>
			<nav class="navbar navbar fixed-top" style="margin-left: 62%;">
				<a class="navbar-brand" href="#Ind">Individual Geographic Area Details Page</a>
				<a class="navbar-brand" href="#Age">Age Group List Page</a>
			</nav>
			<div class="row">
				<div class="col-sm-3">
					<h3 class="h3">Class 0 </h3>
					<div class="container table-container">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Country</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<c:forEach items="${geoDataBean.a0Names}" var="item">
										<td>${item}</td>
									</c:forEach>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-sm-3">
					<h3 class="h3">Class 1</h3>
					<div class="container table-container">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Provinces/Territories</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${geoDataBean.a1Names}" var="item">
									<tr>
										<td>${item}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-sm-3">
					<h3 class="h3">Class 2</h3>
					<div class="container table-container">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Cities</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${geoDataBean.a2Names}" var="item">
									<tr>
										<td>${item}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-sm-3">
					<h3 class="h3">Class 3 </h3>
					<div class="container table-container">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Parts</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${geoDataBean.a3Names}" var="item">
									<tr>
										<td>${item}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<%-- End Geo --%>
		
		<%-- Begin Ind --%>
		<a name="Ind"></a>
		<div class="page-container">
			<h1>Individual Geographic Area Details Page</h1>
			<nav class="navbar navbar fixed-top" style="margin-left: 62%;">
				<a class="navbar-brand" href="#Geo">Geographic Area Classification List</a>
				<a class="navbar-brand" href="#Age">Age Group List</a>
			</nav>
			<div>
			<div class="container table-container">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Name</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${geoDataBean.a1Pop}" var="item">
								<tr>
									<td>${item}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<%-- End Ind --%>
		
		<%-- Begin Age --%>
		<a name="Age"></a>
		<div class="page-container">
			<h1>Age Group List</h1>
			<nav class="navbar navbar fixed-top" style="margin-left: 54%;">
				<a class="navbar-brand" href="#Geo">Geographic Area Classification List</a>
				<a class="navbar-brand" href="#Ind">Individual Geographic Area Details Page</a>
			</nav>
			<div>
				<h3>Age - 2016</h3>
				<div class="container table-container">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Range</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${geoDataBean.age2016}" var="item">
								<tr>
									<td>${item}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<h3>Age - 2011</h3>
				<div class="container table-container">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Range</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${geoDataBean.age2011}" var="item">
								<tr>
									<td>${item}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<%-- End Age --%>
	</body>
</html>