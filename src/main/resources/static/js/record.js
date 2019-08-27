var start;
var end;
var times = 0;
// 进入页面时间
start = new Date();
$(window).bind('beforeunload', function (e) {
    // 退出时间
    end = new Date();
    times = end.getTime() - start.getTime();
    // 取的是秒并且化整
    times = Math.ceil(times / 1000); 
    $.ajax({
        type: 'POST',
        async: false,
        url: '/record',
        data: {
            times: times,
            referrer: document.referrer,
            title: document.title,
            url: window.location.href
        },
        success: function (data) {
            console.log(data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("网络超时，请重试");
        }
    });
});