<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-15"
	pageEncoding="ISO-8859-15"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="authbar">
	<span style="font-size: 2em; color: #337ab7;"> <spring:message
			code="generic.dear" /> <strong style="color: black;">${loggedinuser}</strong>,
		<spring:message code="generic.welcomeMessage" />
	</span> <span class="floatRight"><a href="<c:url value="/logout" />"><spring:message
				code="generic.logout" /></a></span>
</div>
<div>
	<ul class="ems-navbar">
		<!-- Mitarbeiter -->
		<li><a href="<c:url value='/list' />"><spring:message
						code="authheader.menu.employees" /></a></li>
		
		<!-- Verfügbarkeiten -->
		<li><a
			href="<c:url value='/UserAttendance/userAttendanceslist' />"><spring:message
						code="authheader.menu.emp.attendance" /></a></li>
		
		<!-- Projekte -->
		<sec:authorize access="hasAnyRole('ADMIN', 'Projektleitung')">
			<li><a href="<c:url value='/Project/projectslist' />"><spring:message
						code="authheader.menu.projects" /></a></li>
		</sec:authorize>

		<!-- Arbeitspakete -->
		<sec:authorize access="!hasAnyRole('ADMIN', 'Projektleitung')">
			<li><a
				href="<c:url value='/WorkPackage/edit-normal-user-workPackage-0-0' />"><spring:message
						code="authheader.menu.employee.workPackage.allocations" /></a></li>
		</sec:authorize>

		<!-- Zeiterfassung -->
		<li><a id="timeAndAttendanceLink" href="<c:url value='/TimeRecordingReport/timeRecording' />"><spring:message
						code="authheader.menu.workPackage.timeRecording" /></a></li>

		<!-- Faktura -->		
		<sec:authorize access="hasAnyRole('ADMIN', 'Projektleitung')">
			<li><a href="<c:url value="/Payment/paymentslist" />"><spring:message
						code="authheader.menu.workPackage.payment" /></a></li>
		</sec:authorize>
	</ul>
</div>
<script>

	(function() {
		var currDate = new Date();
		var month = currDate.getMonth()
		var year = currDate.getFullYear();
		var url = $("#timeAndAttendanceLink").attr('href');
		url = url + "-" + year + "-" + month + "-1";
		$("#timeAndAttendanceLink").attr('href', function(i, val) {
			return url;
		});
	})();
</script>
