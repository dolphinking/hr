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
		

    // sticky footer stuff
    if ($('#mainContent').height() > $('#sidebarContainer').height()) {
        $('#nonFooter').css( {
            'min-height': '100%'
        });
        $('#nonFooter').height("auto");
    }
    
    function loadCallback(evt) {
        var form = document.getElementById("searchbox");
        var input = form.query;
        form.onsubmit = function (evt) {
            var query = input.value;
            if (query) {
                input.value = "";
                input.blur();
                var currentVersion = $('#currentVersion').text();
                var url = "https://www.google.com/search?q=" + encodeURIComponent("site:nodemanual.org/" + currentVersion + " " + query);
                window.open(url);
            }
            return false;
        };
    }

    var fileNameRE = new RegExp("^" + fileName, "i");

    $('a.menuLink').each(function (index) {
        if ($(this).attr("href").match(fileNameRE)) {
            $(this).addClass("currentItem");
            return false;
        }
    });

    // init search
    $('#search')
    // prevent from form submit
    .on('submit', function () {
        return false;
    }).find('input');

    // init prettyprint
    $('pre > code').addClass('prettyprint');
});

$(document).ready(function () {
    var d = 'a.menu, .dropdown-toggle'

    function clearMenus() {
        $(d).parent('li').each(function () {
            $(this).removeClass('open')
        });
    }

    var s, sx;

    // scrolling offset calculation via www.quirksmode.org
    if (window.pageYOffset || window.pageXOffset) {
        s = window.pageYOffset;
        sx = window.pageXOffset;
    }
    else if (document.documentElement && (document.documentElement.scrollTop || document.documentElement.scrollLeft)) {
        s = document.documentElement.scrollTop;
        sx = document.documentElement.scrollLeft;
    }
    else if (document.body) {
        s = document.body.scrollTop;
        sx = document.body.scrollLeft;
    }

    if (document.documentElement.offsetWidth < 1010) {
        if (sx <= 0) sx = 0;
        else if (sx + document.documentElement.offsetWidth > 1010) sx = 1010 - document.documentElement.offsetWidth;
    }
    else sx = 0;

    $('.members').each(function (i) {
        var position = $(this).position();
        var $classContent = $(this).closest('.classContent');
        
        $(this).scrollspy({
            min: $classContent.position().top - 35,
            max: $classContent.position().top + $classContent.height() - 35,
            onEnter: function (element, position) {
                var $pagination = $(element);
                var $paginationContent = $('.membersContent pos' + i);
                var $tabs = $('.tabs pos' + i);

                $paginationContent.css('left', -1 * sx);
                $paginationContent.css('top', 0);

                $pagination.addClass('shadow').stop().css({
                    height: 31,
                    'top': 33
                }).closest('.classContent').addClass('srolled');

                $tabs.addClass('tabsSansBorder');
            },
            onLeave: function (element, position) {
                var $pagination = $(element);
                var $paginationContent = $('.membersContent pos' + i);
                var $tabs = $('.tabs pos' + i);

                $paginationContent.stop().css({
                    top: 11
                }); 
                $pagination.css({
                    'position': 'absolute',
                    'top': 193
                });
                $pagination.stop().removeClass('shadow').css({
                    height: 42
                });

                $paginationContent.css('left', 0);
               // setTimeout(function () {
                    $paginationContent.css({
                        'top': ''
                    });
                    $pagination.css({
                        'position': '',
                        'top': ''
                    });
                    $paginationContent.css('left', 0);
                    $pagination.closest('.classContent').removeClass('srolled')
                    $tabs.removeClass('tabsSansBorder');
               // }, 300);
            }
        });
    });

    $('span.methodClicker, article.article, h3.methodClicker').each(function () {
        var a = $(this);
        var constructorPos = a.attr("id").indexOf("new ");

        var objName = a.attr("id");
        if (constructorPos >= 0) {
            objName = objName.substring(constructorPos + 4);
            objName += ".new";
        }

        a.attr("id", objName);
    });

    $('.brand').parent('.dropdown').hover(

    function () {
        $(this).addClass('open');
    }, function () {
        clearMenus();
    });

    $('.versions').hover(

    function () {
        $(this).addClass('open');
    }, function () {
        clearMenus();
    });

    function showMethodContent() {
        if (!location.hash) return;

        var $clickerEl = $('span#' + location.hash.replace(/^#/, '').replace(/\./g, '\\.'));
        if ($clickerEl.length > 0 && $clickerEl.hasClass('methodClicker')) {
            var p = $clickerEl.parent();
            p[0].force = true;
            p.trigger('click');
            p[0].force = false;
        }
    }

    if (location.hash) {
        showMethodContent();
        var data = location.hash;
        scrollTo(null, data.substr(1));
    }

    window.onhashchange = function () {
        showMethodContent();
    }
});

function scrollTo(el, data) {
    if (!data) {
        data = el.getAttribute("data-id");
        location.hash = data;
    }
    var el = $("span#" + data.replace(/\./g, "\\."))[0];
    if (!el) return;

    var article = $(el).closest('.article')[0];

    var top = article.offsetTop - 100;

    if (document.body.scrollTop > top || document.body.scrollTop != top && document.body.scrollTop + (window.innerHeight || document.documentElement.offsetHeight) < top + article.offsetHeight) {
        $('body').animate({
            scrollTop: top
        }, {
            duration: 200,
            easing: "swing"
        });
    }
}