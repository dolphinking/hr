$(function () {
    'use strict';

    var baseTitle = document.title,
        // base (general) part of title
        pathName = window.location.pathname,
        fileName = pathName.substring(window.location.pathname.lastIndexOf("/") + 1);

		console.log(pathName);
		console.log(pathName.indexOf("cv_pool"));
    if (pathName.indexOf("applicant_login") >= 0) $('li#applicant_login').addClass("active");
    else if (pathName.indexOf("employee_login") >= 0) $('li#employee_login').addClass("active");
    else if (pathName.indexOf("home") >= 0) $('li#home').addClass("active");
		else if (pathName.indexOf("jobs") >= 0) $('li#jobs').addClass("active");
		else if (pathName.indexOf("jobApplied") >= 0) $('li#jobApplied').addClass("active");
		else if (pathName.indexOf("change_password") >= 0) $('li#change_password').addClass("active");
		else if (pathName.indexOf("cv_pool") >= 0) $('li#cv_pool').addClass("active");
		else if (pathName.indexOf("job_post") >= 0) $('li#job_post').addClass("active");
		else if (pathName.indexOf("employee_settings") >= 0) $('li#employee_settings').addClass("active");
});