// 进入页面时间
var start = new Date();

// 监听刷新
window.addEventListener('unload', (e) => {
    // 退出时间
    var end = new Date();
    times = end.getTime() - start.getTime();
    // 停留时长，单位：秒
    var times = Math.ceil(times / 1000);
    $.ajax({
        type: 'POST',
        async: false,
        url: '/record',
        data: {
            viewsId, viewsId,
            times: times,
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

// $(window).bind('beforeunload', function (e) {});