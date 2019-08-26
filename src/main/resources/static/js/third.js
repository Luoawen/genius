var Base = {};
var loading;
Base.orientationChangeCallback = null;
var Utils = {
    //验证
    Verify: {
        //通用正则 e正则表达式 t要验证的字符串
        testRegExp: function (e, t) {
            e = new RegExp(e);
            return e.test(t);
        },
        isTelephone: function (e) {
            var t = /^(\((\d{2,5})\)|\d{2,5})?(\s*)(-?)(\s*)(\d{5,9})$/;
            return t.exec(e) ? !0 : !1
        },
        isCommonTel: function (e) {
            var t = /^(1[3,5,8,7]\d{9})|(((400[0-9]{1})-(\d{3})-(\d{3}))|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{3,7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$|^([ ]?)$/;
            return t.exec(e) ? !0 : !1
        },
        isPhone: function (e) {
            var t = /^(\+86)?1(\d{10})$/;
            return t.exec(e) ? !0 : !1
        },
        checkMobile: function (e) {
            var t = /^1[3|4|5|7|8][0-9]\d{8}$/;
            return t.exec(e) ? !0 : !1
        },
        checkMail: function (e) {
            var t = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
            return t.exec(e) ? !0 : !1
        },
        checkCn: function (e) {
            var t = /^[\u4e00-\u9fa5]+$/;
            return t.exec(e) ? !0 : !1
        },
        getStrLen: function (e) {
            return e.replace(/[^\x00-\xff]/g, "aa").length
        },
        isNumber: function (e) {
            var t = /^[0-9]*$/;
            return t.exec(e) ? !0 : !1
        },
        isMoney: function (e) {
            var t = /^[0-9]+(.[0-9]{2})?$/;
            return t.exec(e) ? !0 : !1
        },
        checkNum: function (e) {
            var t = /^[0-9]*[1-9][0-9]*$/;
            return t.exec(e) ? !0 : !1
        },
        isPassword: function (e) {
            var t = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,16}$/;
            return t.exec(e) ? !0 : !1
        },
        isUrl: function (e) {
            if (typeof e == "undefined") return !1;
            var t = /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/;
            return t.exec(e) ? !0 : !1
        }
    },
    toInt: function (val, defVal) {
        try {
            if (defVal == undefined || defVal == null)
                defVal = 0;
            var temp = parseInt(val);
            if (isNaN(temp))
                return defVal;
            else
                return temp;
        } catch (e) { }
        return defVal;
    },
    toFixed: function (val,fixed) {//保留几位位小数
        try {
            if (!fixed)
                fixed = 2;
            if (val)
                return parseFloat(val).toFixed(fixed);
            else
                return parseFloat(0).toFixed(fixed);
        } catch (e) {
            return parseFloat(0).toFixed(fixed);
        }
    },
    isWXBrowser: function () {//微信游览器
        var ua = window.navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == 'micromessenger')
            return true;
        else
            return false;
    },
    getUrlParams: function () {
        var resPars = {};
        var url = window.location.search;
        if (url.indexOf('?') < 0) return resPars;
        url = url.substr(1);
        var params = url.split('&');
        params.forEach(function (val, index, item) {
            try {
                var temp = val.split('=');
                var parVal = '';
                for (var i = 1; i < temp.length; i++) {
                    if (i != 1)
                        parVal += '=';
                    parVal += temp[i];
                }
                resPars[temp[0]] = parVal ? decodeURIComponent(parVal) : undefined;
            } catch (e) { console.log(e); }
        });
        return resPars;
    },
    getUrlParam: function (name) {
        //return Utils.getOtherUrlParam(window.location.href.replace('#', ''), name);//临时处理IOS出现#号问题
        return Utils.getOtherUrlParam(window.location.search, name);
    },
    getOtherUrlParam: function (url, name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        var r = url.match(reg);
        if (r != null) {
            return decodeURIComponent(r[2]);
        }
        return null;
    },
    _wxAPIUrl: "",
    wxAPIUrl: function () {
        if (Utils._wxAPIUrl == "")
            _wxAPIUrl = current_headPath + 'WebAPI/';
        return _wxAPIUrl;
    },
    APIPost: function (url, data, func, hideLoading, errFunc, notHideLoading, notOpenID) {
        Utils.APIAjax({
            url: url,
            type: 'post',
            data: data,
            func: func,
            hideLoading: hideLoading,
            errFunc: errFunc,
            notHideLoading: notHideLoading,
            notOpenID: notOpenID
        });
    },
    APIGet: function (url, data, func, hideLoading, errFunc, notHideLoading, notOpenID) {
        Utils.APIAjax({
            url: url,
            type: 'get',
            data: data,
            func: func,
            hideLoading: hideLoading,
            errFunc: errFunc,
            notHideLoading: notHideLoading,
            notOpenID: notOpenID
        });
    },
    APIAjax: function (pars, notTimeStamp) {
        if (notTimeStamp) {
            pars.timeStamp = parseInt(new Date().getTime() / 1000);
            Utils.APIAjaxBase(pars);
        } else {
            Helper.DB.getTimeStamp(function (timeStamp) {
                pars.timeStamp = timeStamp;
                Utils.APIAjaxBase(pars);
            });
        }
    },
    APIAjaxBase: function (pars) {
        if (!pars.hideLoading)
            loading.show();
        var params = {
            type: pars.type,
            dataType: 'json',
            //jsonp: "jsonCallback", // 指定回调函数，这里名字可以为其他任意你喜欢的，比如callback，不过必须与下一行的GET参数一致
            //timeout: 7e3,
            url: Utils.wxAPIUrl() + pars.url,
            //data: pd,
            data: pars.data,
            headers: {
                token: Helper.DB.getToken(),
                sign: pars.notOpenID ? '' : WeChat.unionID,
                timeStamp: pars.timeStamp
            },
            success: function (data) {
                try {
                    if (data.code == -1) {
                        Utils.alert(data.data && data.data.errMsg ? data.data.errMsg : data.msg);
                        if (typeof (pars.errFunc) == 'function')
                            pars.errFunc(data);
                    }
                    else if (data.code < 1) {
                        Utils.alert(data.msg);
                        if (typeof (pars.errFunc) == 'function')
                            pars.errFunc(data);
                    } else {
                        var row;
                        if (data.data && data.data.list && Array.isArray(data.data.list)) {
                            var count = data.data.list.length;
                            for (var i = 0; i < count; i++) {
                                row = data.data.list[i];
                                for (var key in row) {
                                    if (row[key] == null) {
                                        row[key] = '';
                                    }
                                }
                            }
                        }
                        pars.func(data);
                    }
                    if (!pars.notHideLoading)
                        loading.close();
                } catch (e) {
                    Utils.alert('处理数据失败！' + e.message);
                    console.log(e.message);
                    loading.close();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                //Utils.alert('获取远程数据失败！');
                loading.close();
            }
        }
        $.ajax(params);
    },
    alert: function (msg, callback) {
        msgbox(msg, ["确定"], callback);
    },
    confirm: function (msg, callback, btnArray) {
        msgbox(msg, btnArray ? btnArray : ['取消', '确认'], function (index) {
            if (typeof callback != 'function') return;
            if (index == 1)
                callback(true);
            else
                callback(false);
        });
    },
    toStr: function (val, defVal) {
        if (val == null || val == undefined || val.trim() == '')
            if (defVal)
                return defVal;
            else
                return '';
        else
            return val;
    },
    utf16To8: function (str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for (i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i);
            } else if (c > 0x07FF) {
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            } else {
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            }
        }
        return out;
    },
    isFullPath: function (url) {//是否为http的完整路径
        return (String(url).indexOf('http') == 0);
    },
    isHttpPath: function (url) {//是否为Http请求开头路径
        return !!(String(url).indexOf('http') == 0);
    },
    getShareUrlBase: function (url, defImgPath) {
        if (Utils.isFullPath(url))
            return url;
        if (!url)
            url = defImgPath;
        return Utils.getWxShareUrlHead() + url;
    },
    isOfficialVersion: function () {//是否为正式版
        if (location.origin.toLowerCase().indexOf('vcard.xmheigu.com') > -1)
            return true;
        else
            return false;
    },
    getCurrentOrigin: function () {//获取当前二级域名地址
        return location.origin;
    },
    getWxShareUrlHead: function () {
        return Utils.getCurrentOrigin();
    },
    getImgBase: function (url, defImgPath) {
        if (url) {
            if (url.indexOf('http') == 0)
                return url;
            else
                return Utils.ImgUrlHead() + url;
        }
        return Utils.ImgUrlHead() + defImgPath;
    },
    _isIPhone: null,
    isIPhone: function () {
        if (Utils._isIPhone == null)
            Utils._isIPhone = navigator.userAgent.indexOf('iPhone') > -1;
        return Utils._isIPhone;
    },
    isPhone: function () {//过度
        return Utils.isIPhone();
    },
    getSStorage: function (key) {
        if (typeof (Storage) !== "undefined") {
            var temp = sessionStorage.getItem(key);
            if (temp == "undefined")
                return '';
            return temp;
        }
        return null;
    },
    setSStorage: function (key, value) {
        if (typeof (Storage) !== "undefined") {
            sessionStorage.setItem(key, value);
            return true;
        }
        else
            return false;
    },
    getSStorageJson: function (key) {
        var tem = Utils.getSStorage(key);
        try {
            if (!!tem)
                tem = JSON.parse(tem);
        } catch (e) { console.log(e); }
        return tem;
    },
    setSStorageJson: function (key, value) {
        return Utils.setSStorage(key, JSON.stringify(value));
    },
    setClientCookie: function (e, t, n, r, i, s) {
        var o = e + "=" + encodeURIComponent(t);
        n && (o += "; expires=" + n.toGMTString());
        r && (o += "; path=" + r);
        i && (o += "; domain=" + i);
        s && (o += "; secure");
        document.cookie = o;
    },
    getClientCookie: function (e) {
        var t = "(?:; )?" + e + "=([^;]*);?",
            n = new RegExp(t);
        return n.test(document.cookie) ? decodeURIComponent(RegExp.$1) : null;
    },
    deleteClientCookie: function (e, t, n) {
        var r = e + "=; expires=" + (new Date(0)).toGMTString();
        t && (r += "; path=" + t);
        n && (r += "; domain=" + n);
        document.cookie = r;

    },
    /**
     * 计算两日期时间差
     * @param   interval 计算类型：D是按照天、H是按照小时、M是按照分钟、S是按照秒、T是按照毫秒
     * @param  begintime 起始时间
     * @param  endtime 结束时间
     * @return
     */
    countTimeLength: function (interval, begintime, endtime) {
        var objInterval = { 'D': 1000 * 60 * 60 * 24, 'H': 1000 * 60 * 60, 'M': 1000 * 60, 'S': 1000, 'T': 1 };
        interval = interval.toUpperCase();
        try {
            return ((endtime - begintime) / objInterval[interval]).toFixed(0);//保留两位小数点
        } catch (e) {
            return e.message;
        }
    },
    //创建最后执行时间对象，对象才可以用作址传递
    createLastTimeObj: function () {
        var result = { lastTime: null };
        return result;
    },
    //延时执行
    //interval:时间间隔, lastTimeObj:全局最后执行时间, tempLastTimeObj: Timer中执行的时间, callback：执行回调
    delayExecute: function (interval, lastTimeObj, tempLastTimeObj, callback) {
        if (lastTimeObj.lastTime != null && lastTimeObj.lastTime != undefined) { //最后执行时间不为空才进入执行
            if (tempLastTimeObj == null || lastTimeObj.lastTime == tempLastTimeObj.lastTime) { //interval时间内重key，不再重复执行
                var currTime = new Date();
                var timeCount = Utils.countTimeLength('T', lastTimeObj.lastTime, currTime);
                if (timeCount >= interval) {
                    try {
                        callback();
                    } finally {
                        lastTimeObj.lastTime = null; //回调执行完，清空时间
                    }
                }
                else {
                    var tempLastTimeObj = Utils.createLastTimeObj();
                    tempLastTimeObj.lastTime = lastTimeObj.lastTime; //记录本次时间，当前setTimeout标准是否执行的基准时间，跟全局时间不相等，就不需要再执行
                    setTimeout(Utils.delayExecute, interval, interval, lastTimeObj, tempLastTimeObj, callback);
                }
            }
        }
    },
    getNowTimeStamp: function () {
        return parseInt(new Date().getTime() / 1000);
    },
    isTopPage: function () {//是否为顶级页面
        return window.top.location.href == location.href;
    }
};


